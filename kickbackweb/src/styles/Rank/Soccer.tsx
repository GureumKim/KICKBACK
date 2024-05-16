import styled from "styled-components";

const UserRankBox = styled.div`
    width: 100%;
    margin: 30px auto;
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
      flex: 5%;
      display: flex;
      justify-content: end;
    }

    .item:nth-child(3) {
      flex: 15%;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size:20px;
    }

    .item:nth-child(4) {
      flex: 30%;
      font-size: 18px;
    }

    .item:nth-child(5) {
      flex: 35%;
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
    flex: 20%;
  }

  .item2:nth-child(3) {
    flex: 30%;
  }

  .item2:nth-child(4) {
    flex: 35%;
  }
`
export {UserRankBox, RankTable}