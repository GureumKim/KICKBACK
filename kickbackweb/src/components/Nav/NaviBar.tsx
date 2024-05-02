import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { MenuBox, Hme, LogoImg } from '../../styles/Nav/NavBar'
import appLogo from "../../assets/logo3.png"
import { PiPowerBold } from "react-icons/pi";
import useAuthStore from '../../stores/AuthStore';
import { useShallow } from 'zustand/react/shallow';
import useUserStore from '../../stores/UserStore';
import { Fade } from "react-awesome-reveal";
import { GoPerson } from "react-icons/go";
import { IoIosLogOut } from "react-icons/io";

const NaviBar = () => {
  const navigate = useNavigate();
  const [isList, setIsList] = useState(false);

  const { PATH, isLogin, logout } =
    useAuthStore(
      useShallow((state) => ({
        PATH: state.PATH,
        isLogin: state.isLogin,
        logout: state.logout
      }))
    );

  const { nickname, profileImage } =
    useUserStore(
      useShallow((state) => ({
        nickname: state.nickname,
        profileImage: state.profileImage === null ? "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png" : state.profileImage
      }))
    );

  return (
    <>
      <Hme>
        <MenuBox>
          <div className='item' onMouseEnter={() => setIsList(true)} onMouseLeave={() => setIsList(false)}>
            <LogoImg src={appLogo} alt="앱 로고" onClick={() => navigate("/")} />
            <div className='content'>게임소식</div>
            <div className='content'>랭킹</div>
            <div className='content'>가이드</div>
            <div className='content'>커뮤니티</div>
          </div>
          {isLogin ? <>
            {isList ? <div className='item'>
              {nickname} 님
            </div> : <div className='item' style={{ justifyContent: "space-between" }}>
              <div style={{ cursor: 'pointer' }}><span>마이페이지</span> <GoPerson /></div>
              <div style={{ cursor: 'pointer' }} onClick={logout}><span>로그아웃</span> <IoIosLogOut /></div>
            </div>}
          </> :
            <div className='item'>
              <div style={{ marginRight: "3px", cursor: "pointer", marginTop: "6px" }}><PiPowerBold /></div>
              <div style={{ cursor: "pointer" }} onClick={() => navigate("/login")}>로그인</div>
            </div>}
        </MenuBox>
        {isList ?
          <Fade>
            <MenuBox onMouseEnter={() => setIsList(true)} onMouseLeave={() => setIsList(false)}>
              <div className='item'>
                <div className='content'></div>
                <div className='content'>
                  <div className='text'>공지사항</div>
                  <div className='text'>업데이트</div>
                </div>
                <div className='content'>
                  <div className='text'>아이템 전</div>
                  <div className='text' onClick={() => navigate("/rank/speed")}>스피드 전</div>
                  <div className='text'>축구 모드</div>
                </div>
                <div className='content'>
                  <div className='text'>게임 소개</div>
                  <div className='text'>모드 소개</div>
                  <div className='text'>조작법</div>
                </div>
                <div className='content'>
                  <div className='text'>자유 게시판</div>
                  <div className='text'>공유 게시판</div>
                  <div className='text'>Q & A</div>
                </div>
              </div>
              {isLogin ? <div className='item'>
                <img src={profileImage} alt="프로필 이미지" style={{ width: "50%", height: "50%" }} />
              </div> : <div className='item'></div>}
            </MenuBox>
          </Fade> : null}
      </Hme>
    </>
  )
}

export default NaviBar