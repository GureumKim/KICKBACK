import styled from "styled-components";

const ModeBox = styled.div`
  width: 80%;
  margin: 0 auto;

  display: flex;
  flex-direction: row;
  border: 1px solid lightgray;
  border-radius: 10px;
  box-shadow: 2px 2px 2px rgba(0, 0, 0, 0.3);
  margin-bottom: 30px;

  .item {
    flex: 1;
  }

  .item:nth-child(1) {
    flex: 65%;
    border-right: 1px solid lightgray;
  }

  .item:nth-child(2) {
    flex: 35%;
    img {
      width: 100%;
      height: 100%;
      border-top-right-radius: 10px;
      border-bottom-right-radius: 10px;
    }
  }

  .item2 {
    flex: 1;
  }

  .item2:nth-child(1) {
    flex: 35%;
    img {
      width: 100%;
      height: 100%;
      border-bottom-left-radius: 10px;
      border-top-left-radius: 10px;
    }
  }

  .item2:nth-child(2) {
    flex: 65%;
    border-left: 1px solid lightgray;
    
  }
`

const TitleBox = styled.div`
  width: 80%;
  margin: 0 auto;
  font-size: 35px;
  margin-bottom: 15px;
  font-weight: bold;
  font-style: italic;
`

export {ModeBox,TitleBox};