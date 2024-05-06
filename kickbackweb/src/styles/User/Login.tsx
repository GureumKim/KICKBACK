import styled from "styled-components";

const LogoBox = styled.div`
  width: 100%;
  height: auto;
  text-align: center;
  position: relative;
  margin-bottom: 50px;
  margin-top: 50px;
  img {
    width: 40%;
  }
`

const LoginBackBox = styled.div`
  width: 40%;
  height: auto;
  margin: 0 auto;
  background-color: #deddd0;
  border: 0;
  border-radius: 5px;
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  .item {
    width: 90%;
    margin: 0 auto;
    padding: 20px;
    margin-bottom: 15px;
    color: #000000;
    
    display: flex; 
    flex-direction: row;
    align-items: center;
    position: relative;

    .content {
      flex: 1;
      position: relative;
    }

    .content:nth-child(1) {
      flex: 15%;
    }

    .content:nth-child(2) {
      flex: 85%;
      width: 100%;
      height: 50px;
      background-color: #ffffff;
      color: black;
      border: 0;
      border-radius: 3px;
      font-size: 20px;
    }

    .btn {
      position: relative;
      width: 100%;
      margin: 0 auto;
      padding: 20px 0;
      text-align: center;
      background-color: #0278f6;
      color: white;
      font-size: 27px;
      border-radius: 5px;
      cursor: pointer;
    }

    .abs {
      position: absolute;
      color: #757474;
      right: 5%;
      font-size: 30px;
      cursor: pointer;
    }
  }
`

export {LogoBox, LoginBackBox};