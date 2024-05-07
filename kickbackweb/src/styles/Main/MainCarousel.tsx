import styled from "styled-components";

const LoginBox = styled.div`
  width: 100%;
  background-color: #252428;
  height: 200px;
  padding: 30px 0;

  .item {
    width: 90%;
    margin: 0 auto;
    height: 100%;
    display: flex;
    flex-direction: row;
    justify-content: space-between;

    .content {
      flex:1;
      height: 100%;
    }

    .content:nth-child(1) {
      flex: 47%;
      display: flex;
      flex-direction: row;
      margin-right: 15%;
      .in {
        flex: 1;
      }

      .in:nth-child(1) {
        flex: 35%;
        border-radius: 1000px;
        background-color: white;
        margin-right: 30px;
        display: flex;
        align-items: center;
        justify-content: center;
        img {
          width: 60%;
          height: 80%;
        }
      }

      .in:nth-child(2) {
        flex: 65%;
        height: 90%;
        display: flex;
        flex-direction: column;
        justify-content: center;

        .main {
          font-size: 35px;
          margin-bottom: 10px;
          color: #818181;
        }

        .text {
          font-size: 25px;
          color: #d5d5d5;
        }
      }
    }

    .content:nth-child(2) {
      flex: 40%;
      display: flex;
      flex-direction: column;

      .text{
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        margin-bottom: 20px;
        align-items: center;

        .kick {
          flex: 80%;
          font-size: 35px;
          color: #818181;
        }

        .back {
          flex: 20%;
          font-size: 20px;
          color: #7f7f7f;
          cursor: pointer;
          
          &:hover {
            color: white;
          }
        }
      }

      .con {
        display: flex;
        flex-direction: row;
        justify-content: center;

        .con1:nth-child(1) {
          flex: 80%;
          display: flex;
          flex-direction: column;
          margin-right: 2%;
        }

        .con1:nth-child(2) {
          flex: 18%;

          .btn {
            width: 100%;
            height: 95%;
            padding: 10px 0;
            text-align: center;
            background-color: #13579f;
            color: white;
            font-size: 27px;
            border-radius: 10px;
            border: 0;
            cursor: pointer;
          }
        }
      }
    }
  }
`

const InputTag = styled.div`
  width: 100%;
  margin-bottom: 10px;
  color: #bbbbbb;

  display: flex; 
  flex-direction: row;
  align-items: center;
  position: relative;

  .content2 {
    flex: 1;
    padding-left:10px;
  }

  .content2:nth-child(1) {
    flex: 20%;
  }

  .content2:nth-child(2) {
    flex: 80%;
    height: 50px;
    background-color: white;
    border: 0;
    border-radius: 10px;
  }

  .abs {
    position: absolute;
    color: #757474;
    right: 5%;
    font-size: 30px;
    cursor: pointer;
  }
`

const TopBox = styled.div`
  width: 100%;
  height: auto;
  background-color: rgba(0,0,0,0.4);
`

const UserBox = styled.div`
  flex: 40%;
  display: flex;
  flex-direction: row;

  .item2 {
    flex:1 ;
  }

  .item2:nth-child(1) {
    flex: 35%;
    margin-right: 5%;

    img {
      width: 100%;
      height: 100%;
      border-radius: 5px;
    }
  }

  .item2:nth-child(2) {
    flex: 60%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    .text1 {
      flex: 47%;
      margin-bottom: 10px;
    }

    .text1:nth-child(1) {
      font-size: 30px;
      color: #fcfcfc;
      display: flex;
      flex-direction: row;
      justify-content: space-between;
      align-items: center;

      .logout {
        font-size: 25px;
        border: 0;
        border-radius: 5px;
        height: 50px;
        background-color: #267fdd;
        color: white;
        display: flex;
        align-items: center;
        justify-content: center;
        cursor: pointer;
        padding: 0 10px;
        transition: all 250ms;
        &:hover {
          background-color: #a9a9a9;
          color: white;
        }
      }
    }

    .text1:nth-child(2) {
      border-radius: 10px;
      font-size: 30px;
      display: flex;
      border: 1px solid #615e68;
      display: flex;
      align-items: center;
      justify-content: center;
      color: white;
      background: #505050;
      cursor: pointer;
      z-index: 1;
      position: relative;
      -webkit-box-shadow: 4px 8px 19px -3px rgba(0,0,0,0.27);
      box-shadow: 4px 8px 19px -3px rgba(0,0,0,0.27);
      transition: all 250ms;
      overflow: hidden;

      &::before {
        position: absolute;
        top: 0;
        left: 0;
        height: 100%;
        width: 0;
        border-radius: 15px;
        z-index: -1;
        background: #e8e8e8;
        -webkit-box-shadow: 4px 8px 19px -3px rgba(0,0,0,0.27);
        box-shadow: 4px 8px 19px -3px rgba(0,0,0,0.27);
        transition: all 250ms;
      }

      &:hover {
        background: #e8e8e8;
        color: #121212;
      }

      &:hover::before {
        width: 100%;
      }
    }
  }
`

export { LoginBox,TopBox, InputTag, UserBox}