import styled from "styled-components";

const NoticeBox = styled.div`
  width: 90%;
  height: auto;
  margin: 0 auto;
  display: flex;
  flex-direction: row;
  margin-bottom: 50px;
  padding-bottom: 30px;

  .item {
    flex: 1;
  }

  .item:nth-child(1) {
    flex: 60%;
    display: flex;
    flex-direction: column;
    
    .gonji {
      font-size: 40px;
      color: #000000;
      margin-bottom: 10px;
    }

    .text {
      display: flex;
      flex-direction: row;
      align-items: center;
      margin: 5px 0;

      span {
        width: 60px;
        color: white;
        padding: 5px;
        border-radius: 5px;
        margin-right: 10px;
        text-align: center;
      }
    }
  }

  .item:nth-child(2) {
    flex: 40%;

    .gonji {
      font-size: 40px;
      color: #000000;
      margin-bottom: 10px;
    }

    .box {
      height: auto;
      border-radius: 5px;
      display: flex;
      flex-direction: column;
      box-shadow: 2px 2px 2px rgba(0, 0, 0, 0.3);

      .content {
        display: flex;
        flex-direction: row;
        width: 100%;
        height: auto;
        font-size: 20px;
        text-align: center;
        background-color: white;
        border-radius: 5px;
        border-bottom: 1px solid lightgray;
        padding: 10px 0;

        .content-item {
          flex: 1;
        }
        
        .content-item:nth-child(1) {
          flex: 10%
        }

        .content-item:nth-child(2) {
          flex: 40%
        }

        .content-item:nth-child(3) {
          flex: 50%
        }

      }
    }
  }
`

const TitleBox = styled.div`
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

export {NoticeBox,TitleBox};