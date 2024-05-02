import React, { useEffect, useState } from 'react'
import {RankBox, SearchBox,SearchInput,SearchBtn,MyBox,UserRankBox, RankTable} from '../styles/Rank/Speed'
import useUserStore from '../stores/UserStore';
import { useShallow } from 'zustand/react/shallow';
import useAuthStore from '../stores/AuthStore';
import axios from 'axios';
import { useNavigate } from 'react-router';

const RankSpeed = () => {
  const [search, setSearch] = useState("");
  const [userList, setUserList] = useState([]);
  const [isSearch, setIsSearch] = useState(true);
  const [saveUserList, setSaveUserList] = useState([]);
  const navigate = useNavigate();

  const { PATH, isLogin } =
  useAuthStore(
    useShallow((state) => ({
      PATH: state.PATH,
      isLogin: state.isLogin,
    }))
  );

  const { nickname, profileImage } =
    useUserStore(
      useShallow((state) => ({
        nickname: state.nickname,
        profileImage: state.profileImage === null ? "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png" : state.profileImage
      }))
    );

  const changeSearch = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSearch(event.target.value);
  };

  const searchUser = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    if (search === "") {
      setIsSearch(!isSearch);
    }

    const searchUser = saveUserList.filter((item) => item["name"] === search);
    setUserList(searchUser);
    setCurrentPage(1);
  };

  const ITEMS_PER_PAGE = 8;
  const PAGINATION_NUMBERS = 5;

  const [currentPage, setCurrentPage] = useState(1);

  const totalPageCount = Math.ceil(userList.length / ITEMS_PER_PAGE);

  const handlePageChange = (page: number) => {
    setCurrentPage(page);
  };

  const renderPaginationNumbers = () => {
    const paginationNumbers = [];
    const start =
      Math.floor((currentPage - 1) / PAGINATION_NUMBERS) * PAGINATION_NUMBERS +
      1;

    for (
      let i = start;
      i < start + PAGINATION_NUMBERS && i <= totalPageCount;
      i++
    ) {
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
  const currentItems = userList.slice(startIdx, endIdx);

  useEffect(() => {
  }, []);

  return (
    <RankBox>
      <SearchBox>
        <SearchInput
          placeholder="닉네임을 검색해보세요!"
          value={search}
          onChange={changeSearch}
          maxLength={6}
        ></SearchInput>
        <SearchBtn>검색</SearchBtn>
      </SearchBox>
      <MyBox>
        <div className='item'>
          <div className='content'>
            <img src={profileImage} alt="프로필" />
          </div>
          <div className='content'></div>
        </div>
        <div className='item'></div>
      </MyBox>
      <UserRankBox>
        <RankTable>
          <div className="container2">
            <div className="item2">등수</div>
            <div className="item2">닉네임</div>
            <div className="item2">기록</div>
          </div>
          {/* {currentItems.map((user, idx) => (
            <div key={idx} className="container">
              <div className="item">
                {user["ranking"] === 1
                  ? "🥇"
                  : user["ranking"] === 2
                  ? "🥈"
                  : user["ranking"] === 3
                  ? "🥉"
                  : user["ranking"]}
              </div>
              <div className="item">
                <img
                  src={
                    user["profileImg"] === null
                      ? defaultProfile
                      : user["profileImg"]
                  }
                  alt="프로필이미지"
                  style={{
                    width: "45px",
                    height: "45px",
                    borderRadius: "100px",
                  }}
                />
              </div>
              <div
                className="item"
              >
                {user["name"]}
              </div>
              <div className="item">{user["rating"]}</div>
            </div>
          ))} */}
          {userList.length === 0 ? (
            <div className="no_result">존재하지 않는 유저입니다.</div>
          ) : null}
        </RankTable>
        </UserRankBox>
    </RankBox>
  )
}

export default RankSpeed