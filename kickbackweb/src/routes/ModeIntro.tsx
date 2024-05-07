import React from 'react'
import { IntroBox, ImgBox} from '../styles/Intro/GameIntro'
import img1 from "../assets/MexicoTrack.png"
import { ModeBox,TitleBox } from '../styles/Intro/ModeIntro'
import ghostMode from "../assets/ghostMode.png"
import soccerMode from "../assets/soccerMode.png"
import speedMode from "../assets/speedMode.png"
import itemMode from "../assets/itemMode.png"

const ModeIntro = () => {
  return (
    <IntroBox>
      <ImgBox >
        <img src={img1} alt="이미지" />
        <div className='text'>KICKBACK 모드 소개</div>
      </ImgBox>
      <TitleBox>스피드 모드</TitleBox>
      <ModeBox>
        <div className='item2'>
          <img src={speedMode} alt="스피드" />
        </div>
        <div className='item2'>
          <video src="/video/speed.mp4" autoPlay muted playsInline style={{ width: "95%", height: "95%", borderRadius: "10px" }} />
        </div>
      </ModeBox>
      <TitleBox>아이템 모드</TitleBox>
      <ModeBox>
        <div className='item'></div>
        <div className='item'>
          <img src={itemMode} alt="아이템" />
        </div>
        <div className='abs'>Comming Soon!</div>
      </ModeBox>
      <TitleBox>축구 모드</TitleBox>
      <ModeBox>
        <div className='item2'>
          <img src={soccerMode} alt="축구" />
        </div>
        <div className='item2'></div>
        <div className='abs'>Comming Soon!</div>
      </ModeBox>
      <TitleBox>고스트 모드</TitleBox>
      <ModeBox>
        <div className='item'></div>
        <div className='item'>
          <img src={ghostMode} alt="고스트" />
        </div>
        <div className='abs'>Comming Soon!</div>
      </ModeBox>
    </IntroBox>
  )
}

export default ModeIntro