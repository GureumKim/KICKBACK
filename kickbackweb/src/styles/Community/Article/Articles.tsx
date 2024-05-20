import styled from "styled-components";

export const Wrapper = styled.div`
  width: 95%;
  margin: 0 auto;
`;

export const Container = styled.div`
  width: 90%;
  min-height: 45vh;
  margin: 0 auto;
  display: flex;
  flex-wrap: wrap;
`;

export const Article = styled.div`
  width: 20%;
  height: 250px;
  align-items: center;
  margin: 30px 2%;
  padding: 5px 5px;
  border-radius: 10px;
  background-color: rgba(135, 206, 235, 0.5);
  color: rgba(0, 0, 0, 0.8);
  box-shadow: 4px 4px 4px rgba(0, 0, 0, 0.3);
  transition: all 0.2s;

  &.mine {
    background-color: rgba(255, 255, 0, 0.3);
    display: flex;
    flex-direction: column;
    justify-content: space-between;
  }

  &.create {
    display: flex;
    text-align: center;
    justify-content: center;
    font-size: 50px;
    color: white;
    cursor: pointer;

    &:hover {
      transform: scale(1.05);
      transition: all 0.2s;
    }
  }

  div {
    margin: auto;
  }

  .title {
    font-size: 17px;
    width: 80%;
    height: 70px;
    margin-top: 20px;
    text-align: center;
    font-family: 'LINESeedKR-Bd' !important;
  }

  .content {
    width: 80%;
    margin-top: 30px;
    text-align: center;
    font-family: 'LINESeedKR-Bd' !important; 
  }
`;

export const BottomBox = styled.div`
  width: 100%;
  display: flex;
  justify-content: end;
`;

export const Delete = styled.button`
  background-color: rgba(0, 0, 0, 0);
  border: none;
  color: gray;
  font-size: 33px;
  transition: all 0.2s;

  &:hover {
    transform: scale(1.3);
    transition: all 0.2s;
    color: black;
  }
`;
