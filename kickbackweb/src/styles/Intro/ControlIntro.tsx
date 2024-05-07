import styled from "styled-components";

const ContentBox = styled.div`
  width: 80%;
  margin: 0 auto;
  height: auto;
  margin-bottom: 30px;
  display: flex;
  flex-direction: row;
  border: 1px solid lightgray;
  padding: 15px;
  border-radius: 10px;
  box-shadow: 2px 2px 2px rgba(0, 0, 0, 0.3);

  .item {
    flex: 1;
  }

  .item:nth-child(1) {
    flex: 40%;
  }

  .item:nth-child(2) {
    flex: 60%;
    display: flex;
    justify-content: center;
    align-items: center;

    img {
      width: 30%;
    }
  }

  .item2 {
    flex: 1;
  }

  .item2:nth-child(1) {
    flex: 40%;
  }

  .item2:nth-child(2) {
    flex: 30%;
    display: flex;
    justify-content: center;
    align-items: center;

    img {
      width: 50%;
    }
  }

  .item2:nth-child(3) {
    flex: 30%;
    display: flex;
    justify-content: center;
    align-items: center;

    img {
      width: 50%;
    }
  }
`

const TextBox = styled.div`
  width: 80%;
  margin: 30px auto;
  font-size: 30px;
`

export {ContentBox, TextBox};