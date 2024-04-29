import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { MenuBox, Hme, LogoImg } from '../../styles/Nav/NavBar'
import appLogo from "../../assets/logo3.png"
import { GiHamburgerMenu } from "react-icons/gi";
import { PiPowerBold } from "react-icons/pi";
import List from './List';
import useAuthStore from '../../stores/AuthStore';
import { useShallow } from 'zustand/react/shallow';

const NaviBar = () => {
  const navigate = useNavigate();
  const [isList,setIsList] = useState(false);

  const { PATH, isLogin } =
    useAuthStore(
      useShallow((state) => ({
        PATH: state.PATH,
        isLogin: state.isLogin,
      }))
    );

  return (
    <>
      <Hme>
        <MenuBox>
          <div className='item'>
            <GiHamburgerMenu style={{ cursor: "pointer", color: isList ? "#102495":"black" }} onClick={() => setIsList(!isList)}/>
          </div>
          <div className='item'>
            <LogoImg src={appLogo} alt="앱 로고" onClick={() => navigate("/")} />
          </div>
          <div className='item'>
            <div style={{ marginRight: "3px", cursor: "pointer" }}><PiPowerBold /></div>
            {isLogin ? <div></div> : <div style={{ cursor: "pointer" }} onClick={() => navigate("/login")}>로그인</div>}
          </div>
        </MenuBox>
      </Hme>
      {isList ? <List /> : null}
    </>
  )
}

export default NaviBar