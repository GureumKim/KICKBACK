import React from 'react'
import { FooterBox } from '../../styles/Nav/Footer'
import logo from "../../assets/logo4.png"

const Footer = () => {
  return (
    <FooterBox>
      <div className='container'>
        <img src={logo} alt='로고 다른거' className='item'/>
        <div className='item'>
          <div className='content'>KICKBACK : 학살의 현장에서 난, 피어오른다. 붉은 여명에 피어나는 꽃처럼 ... </div>
          <div className='content'>© Jinger Double Down Max Team. Project Name Is KICKBACK. All Rights Reserved.</div>
        </div>
      </div>
    </FooterBox>
  )
}

export default Footer