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
    flex: 80%;
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    font-size: 25px;
    color: #c6c6c6;
    

    .content {
      margin: 0 40px;
      cursor: pointer;
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif !important;
      &:hover {
        text-decoration: underline;
      }
    }
  }
`

export {FooterBox};