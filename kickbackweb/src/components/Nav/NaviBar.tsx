import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { MenuBox, Hme, LogoImg } from '../../styles/Nav/NavBar'
import appLogo from "../../assets/logo.png"
import { GiHamburgerMenu } from "react-icons/gi";
import { PiPowerBold } from "react-icons/pi";

const NaviBar = () => {
  const navigate = useNavigate();

  return (
    <>
      <Hme>
        <MenuBox>
          <div className='item'>
            <GiHamburgerMenu style={{ cursor: "pointer" }}/>
          </div>
          <div className='item'>
            <LogoImg src={appLogo} alt="앱 로고" onClick={() => navigate("/")} />
          </div>
          <div className='item'>
            <div style={{ marginRight: "3px", cursor: "pointer" }}><PiPowerBold /></div>
            <div style={{ cursor: "pointer" }} onClick={() => navigate("/login")}>로그인</div>
          </div>
        </MenuBox>
      </Hme>
    </>
  )
}

export default NaviBar