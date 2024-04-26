import React from 'react'
import { FooterBox } from '../../styles/Nav/Footer'
import logo from "../../assets/logo3.png"

const Footer = () => {
  return (
    <FooterBox>
      <div className='container'>
        <img src={logo} alt='로고 다른거' className='item'/>
        <div className='item'>
          <div className='content'>게임 소개</div> |
          <div className='content'>공지사항</div> |
          <div className='content'>다운로드</div> |
          <div className='content'>커뮤니티</div>
        </div>
      </div>
    </FooterBox>
  )
}

export default Footer