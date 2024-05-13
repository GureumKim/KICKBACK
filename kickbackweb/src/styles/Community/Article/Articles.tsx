import styled from "styled-components";

export const Wrapper = styled.div`
  width: 95%;
  margin: auto;
`;

export const Container = styled.div`
  width: 100%;
  min-height: 45vh;
  display: flex;
  flex-wrap: wrap;
`;

export const Article = styled.div`
  width: 20%;
  height: 250px;
  align-items: center;
  margin: 30px 2.1%;
  padding: 5px 5px;
  border-radius: 10px;
  background-color: rgba(135, 206, 235, 0.5);
  color: rgba(0, 0, 0, 0.8);
  box-shadow: 4px 4px 4px rgba(0, 0, 0, 0.3);
  transition: all 0.2s;

  &.create {
    display: flex;
    text-align: center;
    justify-content: center;
    font-size: 50px;
    color: white;

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
  }

  .content {
    width: 80%;
    margin-top: 30px;
    text-align: center;
  }
`;
