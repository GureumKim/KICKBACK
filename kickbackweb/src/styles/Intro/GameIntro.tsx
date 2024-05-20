import styled from "styled-components";

const IntroBox =  styled.div`
  width: 100%;
  margin: 0 auto;
  height: auto;

  display: flex;
  flex-direction: column;
`

const ImgBox = styled.div`
  position: relative;
  width: 100%;
  height: 200px;
  margin-bottom: 30px;

  img {
    display: block;
    width: 100%;
    height: 200px;
    object-fit: cover;
  }

  .text {
    width: 90%;
    color: white;
    font-size: 40px;
    position: absolute;
    margin-left: 60px;
    bottom: 10px;
  }
`

const TextBox = styled.div`
  width: 90%;
  font-size: 40px;
  font-style: italic;
  margin: 30px auto;

  border-bottom: 2px solid black;
  padding-bottom: 20px;
  margin-bottom: 300px;
  font-family: 'LINESeedKR-Bd' !important;

  span {
    font-size: 30px;
    font-style: oblique;
    font-family: 'LINESeedKR-Bd' !important;

  }
`

export {IntroBox,ImgBox, TextBox};