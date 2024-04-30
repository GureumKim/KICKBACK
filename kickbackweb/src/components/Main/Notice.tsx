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
          <div className='text'><span style={{ backgroundColor: "#3a5ec0" }}>공지</span>서버의 안정성을 개선했습니다. 접속 문제 및 랙 현상이 감소될 것으로 기대됩니다.</div>
          <div className='text'><span style={{ backgroundColor: "#3a5ec0" }}>공지</span>새로운 아이템 '화염병'이 추가되었습니다. 화염병은 적에게 화상 효과를 입히는 강력한 무기입니다.</div>
          <div className='text'><span style={{ backgroundColor: "#3a5ec0" }}>공지</span>게임 이용약관이 변경되었습니다. 변경 사항을 확인하고 동의해주세요.</div>
          <div className='text'><span style={{ backgroundColor: "#3a5ec0" }}>공지</span>매주 수요일에는 정기 업데이트가 진행됩니다. 게임의 새로운 내용을 기대해주세요!</div>
          <div className='text'><span style={{ backgroundColor: "#3a5ec0" }}>공지</span>최신 클라이언트 업데이트에서 성능이 향상되었습니다. 게임 실행 속도가 빨라집니다.</div>
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