import styled from "styled-components";

export const Wrapper = styled.div`
  width: 90%;
  margin: auto;
`;

export const Container = styled.div`
  width: 100%;
`;

export const Article = styled.div`
  width: 70%;
  height: 70px;
  display: flex;
  align-items: center;
  margin: auto;
  padding: 0 30px;
  border-bottom: 1px solid lightgray;
  color: gray;

  &.index {
    height: 50px;
    color: black;
  }

  .id {
    width: 10%;
    text-align: start;
  }

  .title {
    width: 80%;
    text-align: start;
    a {
      color: gray;
      text-decoration: none;
    }
  }

  .nickname {
    width: 10%;
    text-align: start;
  }
`;
