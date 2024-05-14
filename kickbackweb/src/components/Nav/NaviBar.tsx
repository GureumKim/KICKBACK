import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { MenuBox, Hme, LogoImg } from "../../styles/Nav/NavBar";
import appLogo from "../../assets/logo3.png";
import useAuthStore from "../../stores/AuthStore";
import { useShallow } from "zustand/react/shallow";
import useUserStore from "../../stores/UserStore";

const NaviBar = () => {
  const navigate = useNavigate();
  const [isList, setIsList] = useState(false);

  const { PATH, isLogin, logout } = useAuthStore(
    useShallow((state) => ({
      PATH: state.PATH,
      isLogin: state.isLogin,
      logout: state.logout,
    }))
  );

  const { nickname, profileImage } = useUserStore(
    useShallow((state) => ({
      nickname: state.nickname,
      profileImage:
        state.profileImage === null
          ? "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"
          : state.profileImage,
    }))
  );

  return (
    <>
      <LogoImg>
        <img src={appLogo} alt="로고" onClick={() => navigate("/")} />
      </LogoImg>
      {isList ? (
        <div
          style={{
            height: "60px",
            zIndex: "1000",
            backgroundColor: "white",
          }}
        ></div>
      ) : (
        <span></span>
      )}
      <Hme className={isList === true ? "active" : ""}>
        <MenuBox>
          <div
            className="item"
            onMouseEnter={() => setIsList(true)}
            onMouseLeave={() => setIsList(false)}
          >
            <div className="content">게임소식</div>
            <div className="content">랭킹</div>
            <div className="content">가이드</div>
            <div className="content">커뮤니티</div>
          </div>
        </MenuBox>
        {isList ? (
          <>
            <MenuBox
              onMouseEnter={() => setIsList(true)}
              onMouseLeave={() => setIsList(false)}
              className="active"
            >
              <div className="item">
                <div className="content">
                  <div className="text" onClick={() => navigate("/notice?category=NOTICE")}>공지사항</div>
                  <div className="text" onClick={() => navigate("/notice?category=UPDATE")}>업데이트</div>
                </div>
                <div className="content">
                  <div className="text" onClick={() => navigate("/rank/speed")}>
                    스피드 전
                  </div>
                  <div className="text">아이템 전<span>comming soon!</span></div>
                  <div className="text">축구 모드<span>comming soon!</span></div>
                </div>
                <div className="content">
                  <div className="text" onClick={() => navigate("/intro/game")}>
                    게임 소개
                  </div>
                  <div className="text" onClick={() => navigate("/intro/mode")}>
                    모드 소개
                  </div>
                  <div
                    className="text"
                    onClick={() => navigate("/intro/control")}
                  >
                    조작법
                  </div>
                </div>
                <div className="content">
                  <div className="text" onClick={() => navigate("/community/Article")}>
                    자유 게시판
                  </div>
                  <div className="text" onClick={() => navigate("/community/QnA")}>Q & A</div>
                </div>
              </div>
            </MenuBox>
          </>
        ) : null} 
      </Hme>
    </>
  );
};

export default NaviBar;
