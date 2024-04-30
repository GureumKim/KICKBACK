import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { MenuBox, Hme, LogoImg } from '../../styles/Nav/NavBar'
import appLogo from "../../assets/logo3.png"
import { PiPowerBold } from "react-icons/pi";
import useAuthStore from '../../stores/AuthStore';
import { useShallow } from 'zustand/react/shallow';
import useUserStore from '../../stores/UserStore';
import { Fade } from "react-awesome-reveal";

const NaviBar = () => {
  const navigate = useNavigate();
  const [isList, setIsList] = useState(false);

  const { PATH, isLogin } =
    useAuthStore(
      useShallow((state) => ({
        PATH: state.PATH,
        isLogin: state.isLogin,
      }))
    );

  const { nickname } =
    useUserStore(
      useShallow((state) => ({
        nickname: state.nickname,
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
          <div className='item'>
            {isLogin ? <div>
              {nickname} 님 환영합니다!
            </div> :
              <>
                <div style={{ marginRight: "3px", cursor: "pointer", marginTop: "6px" }}><PiPowerBold /></div>
                <div style={{ cursor: "pointer" }} onClick={() => navigate("/login")}>로그인</div>
              </>}
          </div>
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
                <div className='text'>스피드 전</div>
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
            <div className='item'></div>
          </MenuBox>
        </Fade> : null}
      </Hme>
    </>
  )
}

export default NaviBar