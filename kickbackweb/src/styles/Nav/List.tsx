import styled from "styled-components";

const ListBox = styled.div`
  width: 100%;
  height: auto;
  padding: 50px 0;
  background-color: #e5e5e5;
  position: sticky;
  top: 90px;
  z-index: 11;
`

const InnerBox = styled.div`
  width: 95%;
  margin: 0 auto;
  display: flex;
  flex-direction: row;

  .item {
    flex: 1;
    flex: 25%;
    display: flex;
    align-items: center;
    flex-direction: column;
    /* border-right: 2px solid #545454; */
  }

  .content {
    margin-top: 40px;
    font-size: 25px;
    color: #545454;
    cursor: pointer;

    &:hover {
      color: #4457c5;
      text-decoration: underline;
    }
  }
`

export {ListBox, InnerBox};