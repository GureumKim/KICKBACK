import React from 'react'
import { NoticeBox } from '../../styles/Main/Notice'
import { GoPlusCircle } from "react-icons/go";

const Notice = () => {
  return (
    <div style={{ backgroundColor: "#f4f4f4"}}>
      <NoticeBox>
        <div className='item'>
          <div className='gonji'>공지사항 <GoPlusCircle style={{ fontSize: "22px", cursor: "pointer" }} /></div>
          <div className='text'><span style={{ backgroundColor: "#3a5ec0" }}>공지</span>최신 클라이언트 버전에서 발견된 버그들을 수정했습니다. 게임 플레이의 안정성이 향상되었습니다.</div>
        </div>
        <div className='item'>
          <div className='gonji'>오늘의 랭킹 <GoPlusCircle style={{ fontSize: "22px", cursor: "pointer" }} /></div>
          <div className='box'>
            <div className='content' style={{ fontSize: "25px", color: "#4e4e4e" }}>
              <div className='content-item'>등수</div>
              <div className='content-item'>닉네임</div>
              <div className='content-item'>기록</div>
            </div>
            <div className='content'>
              <div className='content-item'>🥇</div>
              <div className='content-item'>영일</div>
              <div className='content-item'>02:31:15</div>
            </div>
            <div className='content'>
              <div className='content-item'>🥈</div>
              <div className='content-item'>영이</div>
              <div className='content-item'>02:32:64</div>
            </div>
            <div className='content'>
              <div className='content-item'>🥉</div>
              <div className='content-item'>영삼</div>
              <div className='content-item'>02:32:98</div>
            </div>
            <div className='content'>
              <div className='content-item'>4</div>
              <div className='content-item'>영사</div>
              <div className='content-item'>02:33:03</div>
            </div>
            <div className='content'>
              <div className='content-item'>5</div>
              <div className='content-item'>영오</div>
              <div className='content-item'>02:33:08</div>
            </div>
            <div className='content'>
              <div className='content-item'>6</div>
              <div className='content-item'>영육</div>
              <div className='content-item'>02:33:11</div>
            </div>
          </div>
        </div>
      </NoticeBox>
    </div>
  )
}

export default Notice