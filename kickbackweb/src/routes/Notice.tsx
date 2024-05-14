import React, { useEffect, useState } from "react";
import axios from 'axios';
import { NoticeBox, NoticeItem, CategorySelector } from '../styles/Notice/Notice';
import { PageNation, MapBox } from '../styles/Rank/Speed';
import useAuthStore from '../stores/AuthStore';
import useNoticeStore from '../stores/NoticStore';
import { useShallow } from 'zustand/react/shallow';
import { useNavigate, useLocation } from 'react-router';
import NoticeIcon from '../assets/noticeIcon.png';
import UpdateIcon from '../assets/updateIcon.png';
import NewIcon from '../assets/newIcon.png';

interface NoticeData {
  id: number;
  title: string;
  content: string;
  category: 'NOTICE' | 'UPDATE';
  nickname: string;
  createdAt: string;
  updatedAt: string;
}

const Notice = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const { PATH } = useAuthStore(useShallow(state => ({ PATH: state.PATH })));
  const { setMinId, setMaxId } = useNoticeStore(
    useShallow((state) => ({
      setMinId: state.setMinId,
      setMaxId: state.setMaxId
    }))
  );

  const [notices, setNotices] = useState<NoticeData[]>([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [category, setCategory] = useState<'NOTICE' | 'UPDATE' | 'ALL'>('ALL');

  const ITEMS_PER_PAGE = 10;
  const PAGINATION_NUMBERS = 5;

  const totalPageCount = Math.ceil(notices.length / ITEMS_PER_PAGE);

  const handlePageChange = (page: number) => {
    setCurrentPage(page);
  };

  const renderPaginationNumbers = () => {
    const paginationNumbers = [];
    const start = Math.floor((currentPage - 1) / PAGINATION_NUMBERS) * PAGINATION_NUMBERS + 1;

    for (let i = start; i < start + PAGINATION_NUMBERS && i <= totalPageCount; i++) {
      paginationNumbers.push(
        <button
          key={i}
          onClick={() => handlePageChange(i)}
          className={currentPage === i ? "current" : ""}
        >
          {i}
        </button>
      );
    }

    return paginationNumbers;
  };

  const startIdx = (currentPage - 1) * ITEMS_PER_PAGE;
  const endIdx = startIdx + ITEMS_PER_PAGE;
  const currentItems = notices.slice(startIdx, endIdx);

  useEffect(() => {
    const searchParams = new URLSearchParams(location.search);
    const categoryParam = searchParams.get('category');
    if (categoryParam === 'NOTICE' || categoryParam === 'UPDATE') {
      setCategory(categoryParam);
    }

    axios.get(`${PATH}/api/v1/notice/all`)
      .then(response => {
        setNotices(response.data);
        setMinId(response.data[0].id);
        setMaxId(response.data[response.data.length - 1].id);
      })
      .catch(error => {
        console.error('Error fetching notices:', error);
      });
  }, [location.search, PATH, setMinId, setMaxId]);

  const handleNoticeClick = (noticeId: number) => {
    navigate(`/notice/${noticeId}`);
  };

  const isToday = (dateString: string) => {
    const today = new Date();
    const date = new Date(dateString);
    return date.setHours(0, 0, 0, 0) === today.setHours(0, 0, 0, 0);
  };

  return (
    <NoticeBox>
      <CategorySelector>
        <div className={category === 'ALL' ? "item choice-all" : "item"} onClick={() => setCategory('ALL')}>ALL</div>
        <div className={category === "NOTICE" ? "item choice1" : "item"} onClick={() => setCategory("NOTICE")}>NOTICE</div>
        <div className={category === "UPDATE" ? "item choice2" : "item"} onClick={() => setCategory("UPDATE")}>UPDATE</div>
      </CategorySelector>
      {currentItems.filter(notice => category === 'ALL' || notice.category === category)
        .map((notice) => (
          <NoticeItem key={notice.id} onClick={() => handleNoticeClick(notice.id)}>
            <div className="notice-content">
              <img src={notice.category === "NOTICE" ? NoticeIcon : UpdateIcon} alt="Category" />
              <h3>
                {notice.title}
                {isToday(notice.createdAt) && <img src={NewIcon} alt="New" style={{ width: '16px', height: '16px' }} />}
              </h3>
            </div>
            <div className="notice-date">
              <div>{new Date(notice.createdAt).toLocaleDateString()}</div>
              <div>Posted by {notice.nickname}</div>
            </div>
          </NoticeItem>
        ))}
      <PageNation>
        <div className="nav_buttons">
          <button onClick={() => handlePageChange(1)}>&lt;&lt;</button>
          <button onClick={() => handlePageChange(currentPage - 1)} disabled={currentPage === 1}>&lt;</button>
          {renderPaginationNumbers()}
          <button onClick={() => handlePageChange(currentPage + 1)} disabled={currentPage === totalPageCount}>&gt;</button>
          <button onClick={() => handlePageChange(totalPageCount)} disabled={totalPageCount < 2}>&gt;&gt;</button>
        </div>
      </PageNation>
    </NoticeBox>
  );
};

export default Notice;


