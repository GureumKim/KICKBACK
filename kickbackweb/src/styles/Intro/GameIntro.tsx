import styled from "styled-components";

const IntroBox =  styled.div`
  width: 100%;
  margin: 0 auto;
  height: auto;
  margin-top: 50px;

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

export {IntroBox,ImgBox};