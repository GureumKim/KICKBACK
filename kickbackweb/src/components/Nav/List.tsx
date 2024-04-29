import React from 'react'
import { ListBox,InnerBox } from '../../styles/Nav/List'
const List = () => {
  return (
    <ListBox>
      <InnerBox>
        <div className='item'>
          <div style={{ fontSize: "35px", fontWeight: "700"}}>게임소식</div>
          <div className='content'>공지사항</div>
          <div className='content'>업데이트</div>
        </div>
        <div className='item'>
          <div style={{ fontSize: "35px", fontWeight: "700"}}>랭킹</div>
          <div className='content'>아이템 전</div>
          <div className='content'>스피드 전</div>
          <div className='content'>축구 모드</div>
        </div>
        <div className='item'>
          <div style={{ fontSize: "35px", fontWeight: "700"}}>가이드</div>
          <div className='content'>게임 소개</div>
          <div className='content'>모드 소개</div>
          <div className='content'>조작법</div>
        </div>
        <div className='item'>
          <div style={{ fontSize: "35px", fontWeight: "700"}}>커뮤니티</div>
          <div className='content'>자유 게시판</div>
          <div className='content'>공유 게시판</div>
          <div className='content'>Q & A</div>
        </div>
      </InnerBox>
    </ListBox>
  )
}

export default List