import styled from "styled-components";

const FooterBox = styled.div`
  background-color: #393939;
  width: 100%;
  height: auto;
  padding: 40px 0;
  
  .container {
    width: 90%;
    margin: 0 auto;
    display: flex;
    flex-direction: row;
  }

  .item {
    flex:1;
  }

  .item:nth-child(1) {
    flex: 20%;
    width: 20%;
  }

  .item:nth-child(2) {
    flex: 75%;
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
    }
  }
`

export {FooterBox};