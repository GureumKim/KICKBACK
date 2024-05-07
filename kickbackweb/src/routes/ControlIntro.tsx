import React from 'react'
import { IntroBox, ImgBox} from '../styles/Intro/GameIntro'
import uTrack from "../assets/uphillTrack.png"
import { ContentBox, TextBox } from '../styles/Intro/ControlIntro'
import img2 from "../assets/2.gif"
import img3 from "../assets/3.gif"
import img4 from "../assets/8.gif"
import img6 from "../assets/6.gif"
import con1 from "../assets/con1.png"
import con2 from "../assets/con2.png"
import con3 from "../assets/con3.png"
import con4 from "../assets/con4.png"
import con5 from "../assets/con5.png"

const ControlIntro = () => {
  return (
    <IntroBox>
      <ImgBox >
        <img src={uTrack} alt="이미지" />
        <div className='text'>KICKBACK 조작법</div>
      </ImgBox>
      <TextBox>1. 기본 조작법 ( 방향키 or WASD )</TextBox>
      <ContentBox>
        <div className='item2'>
          <img src={img2} alt="조작법"/>
        </div>
        <div className='item2'>
          <img src={con1} alt="조작법"/>
        </div>
        <div className='item2'>
          <img src={con2} alt="조작법"/>
        </div>
      </ContentBox>
      <TextBox>2. 점프 ( 스페이스바 )</TextBox>
      <ContentBox>
        <div className='item'>
          <img src={img3} alt="조작법"/>
        </div>
        <div className='item'>
          <img src={con5} alt="조작법"/>
        </div>
      </ContentBox>
      <TextBox>3. 드리프트 ( Left Shift )</TextBox>
      <ContentBox>
        <div className='item'>
          <img src={img4} alt="조작법"/>
        </div>
        <div className='item'>
          <img src={con3} alt="조작법"/>
        </div>
      </ContentBox>
      <TextBox>4. 부스터(아이템) 사용( Left Ctrl )</TextBox>
      <ContentBox>
        <div className='item'>
          <img src={img6} alt="조작법"/>
        </div>
        <div className='item'>
          <img src={con4} alt="조작법"/>
        </div>
      </ContentBox>
    </IntroBox>
  )
}

export default ControlIntro