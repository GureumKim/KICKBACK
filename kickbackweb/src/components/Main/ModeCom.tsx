import React from 'react'
import { ModeText, ModeBox, Mode } from "../../styles/Main/Main"
import ghostMode from "../../assets/ghostMode.png"
import soccerMode from "../../assets/soccerMode.png"
import speedMode from "../../assets/speedMode.png"
import itemMode from "../../assets/itemMode.png"

const ModeCom = () => {
  return (
    <>
      <ModeText>모드 소개</ModeText>
      <ModeBox>
        <Mode>
          <img src={speedMode} alt="스피드 모드"></img>
          <span className='abs-text'>스피드 모드</span>
        </Mode>
        <Mode>
          <img src={itemMode} alt="아이템 모드" ></img>
          <span className='abs-text'>아이템 모드</span>
          <div className='abs-back'>Comming Soon!</div>
        </Mode>
        <Mode>
          <img src={soccerMode} alt="축구 모드"></img>
          <span className='abs-text'>축구 모드</span>
          <div className='abs-back'>Comming Soon!</div>
        </Mode>
        <Mode>
          <img src={ghostMode} alt="고스트 모드"></img>
          <span className='abs-text'>고스트 모드</span>
          <div className='abs-back'>Comming Soon!</div>
        </Mode>
      </ModeBox>
    </>

  )
}

export default ModeCom