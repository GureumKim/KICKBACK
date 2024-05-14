import styled from "styled-components";

const FooterBox = styled.div`
  background-color: #252428;
  width: 100%;
  height: auto;
  padding: 10px 0;
  
  .container {
    width: 90%;
    margin: 0 auto;
    display: flex;
    flex-direction: row;
    font-family: 'LINESeedKR-Bd' !important;

  }

  .item {
    flex:1;
    font-family: 'LINESeedKR-Bd' !important;

  }

  .item:nth-child(1) {
    flex: 15%;
    width: 5%;
  }

  .item:nth-child(2) {
    flex: 85%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: start;
    font-size: 12px;
    color: #c6c6c6;
    margin-left: 30px;
    .content {
      margin: 5px 0;
      display: flex;
      align-items: center;
      justify-content: center;
      font-family: 'LINESeedKR-Bd' !important;

    }
  }
`

export {FooterBox};