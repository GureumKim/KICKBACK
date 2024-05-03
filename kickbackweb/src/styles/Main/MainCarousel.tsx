import styled from "styled-components";
import img from "../../assets/carouselnav.png"

const CarouselContainer = styled.div`
  width: 100%;
  margin: 0 auto;
  height: 500px;
  overflow: hidden;
  position: relative;
`;

const SlideContainer = styled.div`
  display: flex;
  width: 100%;
  height: 600px;
`;

const Slide = styled.div`
  flex: 0 0 auto;
  width: 100%;
`;

const Image = styled.img`
  width: 100%;
  height: 600px;
`;

const LeftArrow = styled.button`
  font-size: 40px;
  color: #ffffff;
  cursor: ${({ disabled }) => disabled ? '' : 'pointer'};
  background-color: transparent;
  border: 0;
`;

const RightArrow = styled.button`
  font-size: 40px;
  color: #ffffff;
  cursor: ${({ disabled }) => disabled ? '' : 'pointer'};
  background-color: transparent;
  border: 0;
`;

const CadBox = styled.div`
  width: 100%;
  height: auto;
  position: relative;
`

const InBox = styled.div`
  width: 100%;
  height: 74px;
  margin: 0 auto;
  display: flex;
  flex-direction: row;
  position: absolute;
  top:-74px;
  
  background-image: url(${img});
  background-size: cover;
  background-repeat: no-repeat;
  .item {
    flex: 1;
  }

  .item:nth-child(1) {
    flex: 10%;
    text-align: end;
    margin-top: 10px;
  }

  .item:nth-child(2) {
    flex: 30%;
  }

  .item:nth-child(3) {
    flex: 20;
    position: relative;
    
    img {
      position: absolute;
      top: -30px;
      left: 10%;
      width: 200px;
      height: 200px;
      cursor: pointer;
      border: 5px solid #151515;
      border-radius: 1000px;
    }
  }

  .item:nth-child(4) {
    flex: 30%;
  }

  .item:nth-child(5) {
    flex: 10%;
    text-align: start;
    margin-top: 10px;
  }
`

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
        justify-content: end;

        .main {
          font-size: 35px;
          margin-bottom: 10px;
          color: #d1d1d1;
        }

        .text {
          font-size: 25px;
          color: #818181;
        }
      }
    }

    .content:nth-child(2) {
      flex: 40%;
      display: flex;
      flex-direction: column;

      .text{
        font-size: 40px;
        color: #d1d1d1;
        margin-bottom: 20px;
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

export {CarouselContainer,SlideContainer,Slide,Image, LeftArrow, RightArrow, CadBox, InBox, LoginBox,TopBox, InputTag, UserBox}