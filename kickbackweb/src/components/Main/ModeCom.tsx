import React, { useState } from 'react'
import { ModeText, ModeBox, Mode, Modal } from "../../styles/Main/Main"
import ghostMode from "../../assets/ghostMode.png"
import soccerMode from "../../assets/soccerMode.png"
import speedMode from "../../assets/speedMode.png"
import itemMode from "../../assets/itemMode.png"
import { IoCloseSharp } from "react-icons/io5";

const ModeCom = () => {
  const [open,setOpen] = useState(false);

  return (
    <>
      {open ? <Modal>
        <div className='video-box'>
          <div className='text'><IoCloseSharp style={{ cursor: "pointer" }} onClick={() => setOpen(false)}/></div>
          <div>
            <video src="/video/speed.mp4" controls playsInline style={{ width: "100%", height: "100%" }} />
          </div>
        </div>
      </Modal> : null}
      <ModeText>모드 소개 <span>Mode Introduction</span></ModeText>
      <ModeBox>
        <Mode onClick={() => setOpen(true)}>
          <img src={speedMode} alt="스피드 모드"></img>
          <span className='abs-text'>스피드 모드</span>
        </Mode>
        <Mode>
          <img src={soccerMode} alt="축구 모드"></img>
          <span className='abs-text'>축구 모드</span>
        </Mode>
        <Mode>
          <img src={itemMode} alt="아이템 모드" ></img>
          <span className='abs-text'>아이템 모드</span>
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