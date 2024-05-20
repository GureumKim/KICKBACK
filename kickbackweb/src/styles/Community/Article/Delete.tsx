import styled from "styled-components";

export const Background = styled.div`
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.2);
`;

export const Wrapper = styled.div`
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 300px;
  height: 150px;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  border-radius: 10px;
  box-shadow: 4px 4px 4px rgba(0, 0, 0, 0.3);
  background-color: rgba(255, 255, 255, 0.9);

  .topBox {
    width: 100%;
    text-align: center;
    font-size: 18px;
    font-family: 'LINESeedKR-Bd' !important;
  }

  .bottomBox {
    width: 100%;
    display: flex;
    flex-direction: row;
    justify-content: space-around;
  }
`;

export const Btn = styled.button`
  width: 70px;
  height: 30px;
  border: none;
  border-radius: 10px;
  transition: all 0.2s;

  &:hover {
    transition: all 0.2s;
    transform: scale(1.1);
  }

  &.delete {
    background-color: rgba(255, 0, 0, 0.75);
  }

  &.cancle {
    background-color: gray;
  }
`;
