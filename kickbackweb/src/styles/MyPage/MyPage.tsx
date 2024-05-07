import styled from "styled-components";

const UserBox = styled.div`
  width: 80%;
  margin: 0 auto;
  height: auto;

  display: flex;
  flex-direction: column;
  margin-bottom: 12px;
  justify-content: center;
  align-items: center;

  .item {
    flex: 1;
  }

  .item:nth-child(1) {
    flex: 70%;
    margin-right: 1%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;

    img {
      width: 200px;
      height: 200px;
      border-radius: 1000px;
      border: 2px solid lightgray;
      margin-bottom: 10px;
    }

    .updata-btn {
      width: auto;
      height: auto;
      padding: 5px;
      font-size: 17px;
      border: 1px solid lightgray;
      border-radius: 5px;
      background-color: #eeeeee;
      box-shadow: 1px 1px 1px rgba(0, 0, 0, 0.3);
      cursor: pointer;
    }
  }

  .item:nth-child(2) {
    flex: 29%;
    display: flex;
  }
`

const RecordBox = styled.div`
  width: 80%;
  margin: 0 auto;
  height: auto;

  border: 1px solid lightgray;
  border-radius: 10px;
  box-shadow: 2px 2px 2px rgba(0, 0, 0, 0.3);
  padding: 10px 0;
  margin-bottom: 50px;

  .text {
    width: 95%;
    margin: 0 auto;
    font-size: 35px;

    span {
      font-size: 25px;
      font-style: italic;
      color: lightgray;
    }
  }
`

const ContentBox = styled.div`
  width: 95%;
  margin: 20px auto;
  height: auto;
  display: flex;
  flex-direction: row;

  .content {
    flex: 1;
    display: flex;
    flex-direction: column;
    border: 1px solid lightgray;
    border-radius: 5px;

    .record {
      flex: 1;
    }
    
    .record:nth-child(1) {
      flex: 80%;
      position: relative;

      img {
        width: 100%;
        height: 200px;
        border-top-left-radius: 5px;
        border-top-right-radius: 5px;
      }

      .abs {
        position: absolute;
        top: 0;
        right: 0;
        left: 0;
        bottom: 0;
        width: 100%;
        height: 98%;
        border-radius: 5px;
        background-color: rgba(53, 53, 53, 0.4);
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 40px;
        color: white;
        font-style: italic;
      }
    }
    .record:nth-child(2) {
      flex: 20%;
      padding: 15px 0;
      text-align: center;

      .p {
        display: flex;
        justify-content: center;
        align-items: center;
        margin: 21px 0;
      }

      .p > img {
        width: 7%;
        height: 7%;
        border-radius: 1000px;
        margin-right: 5px;
      }
    }
  }

  .content:nth-child(1) {
    flex: 30%;
    margin-right: 5%;
  }

  .content:nth-child(2) {
    flex: 30%;
    margin-right: 5%;
  }

  .content:nth-child(3) {
    flex: 30%;
  }
`

const TextBox = styled.div`
  width: 80;
`

export {UserBox, RecordBox, ContentBox};