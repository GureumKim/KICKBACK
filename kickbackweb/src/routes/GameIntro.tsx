import React from 'react'
import { IntroBox, ImgBox} from '../styles/Intro/GameIntro'
import img1 from "../assets/CebuTrack.png"

const GameIntro = () => {
  return (
    <IntroBox>
      <ImgBox >
        <img src={img1} alt="이미지" />
        <div className='text'>KICKBACK 게임 소개</div>
      </ImgBox>
    </IntroBox>
  )
}

export default GameIntro