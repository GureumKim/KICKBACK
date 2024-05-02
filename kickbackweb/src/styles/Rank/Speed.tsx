import styled from "styled-components";

const RankBox = styled.div`
  width: 80%;
  height: auto;
  margin: 0 auto;
  margin-top: 140px;
  display: flex;
  flex-direction: column;
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
    }
  }

  .item:nth-child(2) {
    flex: 29%;
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
      border-bottom: 1px solid lightgray;
    }

    .item:nth-child(1) {
      flex: 15%;
    }

    .item:nth-child(2) {
      flex: 15%;
    }

    .item:nth-child(3) {
      flex: 35%;
    }

    .item:nth-child(4) {
      flex: 35%;
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
    font-size: 20px;
  }

  .item2:nth-child(1) {
    flex: 15%;
  }

  .item2:nth-child(2) {
    flex: 50%;
  }

  .item2:nth-child(3) {
    flex: 35%;
  }
`

export {RankBox, SearchBox,SearchInput,SearchBtn,MyBox, UserRankBox, RankTable}