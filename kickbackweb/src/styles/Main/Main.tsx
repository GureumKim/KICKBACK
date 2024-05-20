import styled from "styled-components";

const ModeText = styled.div`
  width: 90%;
  margin: 0 auto;
  margin-bottom: 10px;
  font-size: 35px;
  padding-top: 30px;

  span {
    font-size: 25px;
    font-style: italic;
    color: #a5a5a5;
  }
`

const ModeBox = styled.div`
  width: 90%;
  margin: 0 auto;
  display: flex;
  flex-direction: row;
  height: auto;
  margin-bottom: 50px;  
`

const Mode = styled.div`
  flex: 1;
  flex: 25%;
  border: 0;
  border-radius: 10px;
  display: flex;
  flex-direction: row;
  position: relative;

  img {
    width: 98%;
    height: 100%;
    object-fit: cover;
    margin-bottom: 10px;
    border-radius: 10px;
    cursor: pointer;
    border: 0;
    box-shadow: 2px 2px 2px rgba(0, 0, 0, 0.3);
  }

  .abs-text {
    position: absolute;
    bottom: 2%;
    right: 5%;
    font-weight: 700;
    color: white;
    font-size: 50px;
    font-style: italic;
  }

  .abs-back {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-color: rgba(53, 53, 53, 0.5);
    width: 98%;
    border-radius: 10px;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 40px;
    color: white;
  }
`

const Modal = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0,0,0,0.5);
  z-index: 10000;
  display: flex;
  align-items: center;
  justify-content: center;

  .video-box {
    width: 60%;
    margin: 0 auto;
    height: auto;
    display: flex;
    flex-direction: column;

    .text {
      text-align: end;
      font-size: 35px;
      color: white;
    }
  }
`

export {ModeText, ModeBox, Mode, Modal};