import React from 'react'
import { IntroBox, ImgBox, TextBox} from '../styles/Intro/GameIntro'
import img1 from "../assets/CebuTrack.png"
import img2 from "../assets/1.gif"
import img3 from "../assets/4.gif"
import img4 from "../assets/7.gif"

const GameIntro = () => {
  return (
    <IntroBox>
      <ImgBox >
        <img src={img1} alt="이미지" />
        <div className='text'>KICKBACK 게임 소개</div>
      </ImgBox>
      <TextBox>KICKBACK : <span>친한 친구들끼리 모여서 소소하게 노는 것</span></TextBox>
    </IntroBox>
  )
}

export default GameIntro