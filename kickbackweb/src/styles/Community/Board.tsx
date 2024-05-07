import styled from 'styled-components';

export const Wrapper = styled.div`
  margin: auto;
`

export const IntroBox = styled.div`
  width: 100%;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
`

export const ImgBox = styled.div`
  position: relative;
  width: 100%;
  height: 200px;

  img {
    display: block;
    width: 100%;
    height: 200px;
    object-fit: cover;
  }

  .text {
    width: 90%;
    color: black;
    font-size: 40px;
    position: absolute;
    margin-left: 60px;
    bottom: 10px;
  }
`

export const CreateBtn = styled.button`
`

export const Title = styled.div`
  width: 100%;
  text-align: center;
`