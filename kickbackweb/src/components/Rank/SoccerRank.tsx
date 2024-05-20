import React, { useEffect, useState } from 'react'
import { RankBox, SearchBox, SearchInput, SearchBtn, PageNation } from '../../styles/Rank/Speed'
import { useShallow } from 'zustand/react/shallow';
import useAuthStore from '../../stores/AuthStore';
import axios from 'axios';
import { IntroBox, ImgBox } from '../../styles/Intro/GameIntro'
import img1 from "../../assets/soccer1.png"
import { useNavigate } from 'react-router';
import { UserRankBox,RankTable } from '../../styles/Rank/Soccer';

const SoccerRank = () => {
  const [search, setSearch] = useState("");
  const [userList, setUserList] = useState([]);
  const [saveUserList, setSaveUserList] = useState([]);
  const [isSearch, setIsSearch] = useState(false);
  const navigate = useNavigate();

  const { PATH } =
    useAuthStore(
      useShallow((state) => ({
        PATH: state.PATH,
      }))
    );

  const changeSearch = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSearch(event.target.value);
  };

  const searchUser = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    if (search === "") {
      setUserList(saveUserList)
    } else {
      const searchUser = saveUserList.filter((item) => item["nickname"] === search);
      setUserList(searchUser);
    }

    setCurrentPage(1);
  };

  const ITEMS_PER_PAGE = 10;
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
    axios.get(`${PATH}/api/v1/ranking/soccer`)
    .then((res) => {
      setUserList(res.data)
      setSaveUserList(res.data)
    })
    .catch((error) => console.log(error))
  },[])

  return (
    <>
      <IntroBox>
        <ImgBox >
          <img src={img1} alt="이미지" />
          <div className='text'>축구모드 랭킹</div>
        </ImgBox>
      </IntroBox>
      <RankBox>
        <SearchBox onSubmit={searchUser}>
          <SearchInput
            placeholder="닉네임을 검색해보세요!"
            value={search}
            onChange={changeSearch}
            maxLength={6}
          ></SearchInput>
          <SearchBtn>검색</SearchBtn>
        </SearchBox>
        <UserRankBox>
          <RankTable>
            <div className="container2">
              <div className="item2">등수</div>
              <div className="item2">닉네임</div>
              <div className="item2">레이팅</div>
              <div className="item2">전적</div>
            </div>
            {currentItems.map((user, idx) => (
              <div key={idx} className="container">
                <div className="item">
                  {user["rank"] === 1
                    ? "🥇"
                    : user["rank"] === 2
                      ? "🥈"
                      : user["rank"] === 3
                        ? "🥉"
                        : user["rank"]}
                </div>
                <div className="item">
                  <img
                    src={
                      user["profileImage"] === null
                        ? "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"
                        : user["profileImage"]
                    }
                    alt="프로필이미지"
                    style={{
                      width: "35px",
                      height: "35px",
                      borderRadius: "100px",
                      marginRight: "20px"
                    }}
                  />
                </div>
                <div className="item">{user["nickname"]}</div>
                <div className="item">{user["scores"]}</div>
                <div className='item'>
                  {user["wins"] + user["loses"]}전 {user["wins"]}승 {user["loses"]}패 
                  ({user["wins"] + user["loses"] !== 0 ?
                   Math.round((user["wins"] / (user["wins"] + user["loses"])) * 100) + "%" : "0%"})
                </div>
              </div>
            ))}
            {userList.length === 0 ? (
              <div className="no_result">존재하지 않는 유저입니다.</div>
            ) : null}
          </RankTable>
          {totalPageCount > 0 && (
            <PageNation>
              <div className="nav_buttons">
                <button onClick={() => handlePageChange(1)}>&lt;&lt;</button>
                <button
                  onClick={() => handlePageChange(currentPage - 1)}
                  disabled={currentPage === 1}
                >
                  &lt;
                </button>
                {renderPaginationNumbers()}
                <button
                  onClick={() => handlePageChange(currentPage + 1)}
                  disabled={currentPage === totalPageCount}
                >
                  &gt;
                </button>
                <button onClick={() => handlePageChange(totalPageCount)}>
                  &gt;&gt;
                </button>
              </div>
            </PageNation>
          )}
        </UserRankBox>
      </RankBox>
    </>

  )
}

export default SoccerRank