import styled from "styled-components";

const RankBox = styled.div`
  width: 80%;
  height: auto;
  margin: 0 auto;
  margin-top: 50px;
  display: flex;
  flex-direction: column;
`

const MapBox = styled.div`
  width: 90%;
  margin: 20px auto;
  height: auto;
  display: flex;
  flex-direction: row;

  .item {
    flex: 1;
    border: 1px solid lightgray;
    border-radius: 10px;
    box-shadow: 2px 2px 2px rgba(0, 0, 0, 0.3);
    text-align: center;
    padding: 5px 0;
    font-size: 25px;
    cursor: pointer;
  }

  .item:nth-child(1) {
    flex: 30%;
    margin-right: 5%;

    &:hover {
      background-color: #e6b822;
      color: white;
      scale: calc(1.1);
      transition: all 0.5s;
    }
  }
  .item:nth-child(2) {
    flex: 30%;
    margin-right: 5%;

    &:hover {
      background-color: #40ca38;
      color: white;
      scale: calc(1.1);
      transition: all 0.5s;
    }
  }
  .item:nth-child(3) {
    flex: 30%;

    &:hover {
      background-color: #6f6a6a;
      color: white;
      scale: calc(1.1);
      transition: all 0.5s;
    }
  }

  .choice1 {
    background-color: #e6b822;
    color: white;
    scale: calc(1.1);
  }

  .choice2 {
    background-color: #40ca38;
    color: white;
    scale: calc(1.1);
  }

  .choice3 {
    background-color: #6f6a6a;
    color: white;
    scale: calc(1.1);
  }
`

const SearchBox = styled.form`
  width: 80%;
  height: auto;
  margin: 15px auto;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
`

const SearchInput = styled.input`
  width: 89%;
  height: 30px;
  padding: 10px;
  border-radius: 10px;
  border: 1px solid lightgray;
  background-color: rgba(255, 255, 255, 0.8);
  box-shadow: 2px 2px 2px rgba(0, 0, 0, 0.3);
  font-family: 'LINESeedKR-Bd' !important;

  &:focus {
    outline-color: lightgray;
  }
`
const SearchBtn = styled.button`
  width: 7%;
  height: 50px;
  border-radius: 10px;
  background-color: lightgray;
  border: 0;
  cursor: pointer;
  transition: 0.2s;
  box-shadow: 2px 2px 2px rgba(0, 0, 0, 0.3);
  font-family: 'LINESeedKR-Bd' !important;

  &:hover {
    transition: 0.2s;
    background-color: #373737;
    color: white;
  }
`

const MyBox = styled.div`
  width: 100%;
  height: auto;
  display: flex;
  flex-direction: row;
  margin: 30px 0;

  .item {
    flex: 1;
    border: 1px solid lightgray;
    border-radius: 5px;
    box-shadow: 2px 2px 2px rgba(0, 0, 0, 0.3);
    height: 300px;

    .memem {
      width: 100%;
      display: flex;
      justify-content: center;
      align-items: center;
      font-size: 40px;
      color: gray;
    }
  }

  .item:nth-child(1) {
    flex:70%;
    display: flex;
    flex-direction: row;
    margin-right: 1%;

    .content {
      flex:1;
      padding: 10px;
    }

    .content:nth-child(1){
      flex: 35%;
      margin-right: 2%;

      img {
        width: 100%;
        height: 100%;
      }
    }
    
    .content:nth-child(2){
      flex: 63%;
      display: flex;
      flex-direction: column;
      font-size: 23px;
      justify-content: center;
      div {
        margin-bottom: 20px;
      }
    }
  }

  .item:nth-child(2) {
    flex: 29%;
    border: 0;
    img {
      border-radius: 5px;
      width: 100%;
      height: 100%;
    }
  }
`

const UserRankBox = styled.div`
    width: 100%;
    margin: 0 auto;
    height: auto; 
    border-radius: 10px;
    display: flex;
    flex-direction: column;
    margin-bottom: 50px;
    box-shadow: 2px 2px 2px rgba(0, 0, 0, 0.3);

    .container {
      display: flex;
      flex-direction: row;
      height: auto;
      align-items: center;
      padding: 20px;
      
    }

    .item {
      flex: 1;
      text-align: center;
      font-size: 20px;
      font-family: 'LINESeedKR-Bd' !important;
    }

    .item:nth-child(1) {
      flex: 15%;
    }

    .item:nth-child(2) {
      flex: 10%;
      display: flex;
      justify-content: end;
    }

    .item:nth-child(3) {
      flex: 25%;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size:20px;
    }

    .item:nth-child(4) {
      flex: 50%;
      font-size: 18px;
    }
`

const RankTable = styled.div`
  width: 100%;
  height: auto;
  display: flex;
  flex-direction: column;
  background-color: #ffffff;
  border-radius: 5px;

  .no_result {
    width: 100%;
    font-size: 30px;
    text-align: center;
    padding: 30px;
    color: #535353;
    
  }

  .container2 {
    padding: 10px;
    display: flex;
    flex-direction: row;
    background-color: #393939;
    color: white;
    border-bottom: 1px solid lightgray;
    text-align: center;
    padding: 20px;
    border-radius: 5px;
  }

  .item2 {
    flex: 1;
    text-align: center;
    font-size: 17px;
  }

  .item2:nth-child(1) {
    flex: 15%;
  }

  .item2:nth-child(2) {
    flex: 35%;
  }

  .item2:nth-child(3) {
    flex: 50%;
  }
`

const PageNation = styled.div`
  margin: 20px auto;
  display: flex;
  justify-content: center;
  align-items: center;

  .nav_buttons button {
    padding: 8px 12px;
    margin: 0 5px;
    border: 1px solid #ccc;
    border-radius: 5px;
    background-color: #fff;
    cursor: pointer;
    font-size: 16px;
    color: #464646;
  }

  .nav_buttons button:hover {
    background-color: #eee;
  }

  .nav_buttons button.current {
    font-weight: bold;
    background-color: #ccc;
    color: #fff;
  }
`

export {RankBox, SearchBox,SearchInput,SearchBtn,MyBox, UserRankBox, RankTable, PageNation, MapBox}