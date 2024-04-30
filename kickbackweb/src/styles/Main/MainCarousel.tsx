import styled from "styled-components";

const CarouselContainer = styled.div`
  width: 100%;
  height: 600px;
  overflow: hidden;
  margin-top: 80px;
`;

const SlideContainer = styled.div`
  display: flex;
  width: 100%;
  height: 600px;
`;

const Slide = styled.div`
  flex: 0 0 auto;
  width: 100%;
  position: relative;
`;

const Image = styled.img`
  width: 100%;
  height: 600px;
`;

const IndicatorContainer = styled.div`
  width: 100%;
  position: absolute;
  z-index: 2;
  bottom: 0;
  display: flex;
  justify-content: center;
  img {
  }
`

const LeftArrow = styled.button`
  font-size: 40px;
  position: absolute;
  top: 45%;
  left: 3%;
  z-index: 10;
  color: ${({ disabled }) => disabled ? 'lightgray' : 'white'};
  cursor: ${({ disabled }) => disabled ? '' : 'pointer'};
  background-color: transparent;
  border: 0;
`;

const RightArrow = styled.button`
  font-size: 40px;
  position: absolute;
  top: 45%;
  right: 3%;
  z-index: 10;
  color: ${({ disabled }) => disabled ? 'lightgray' : 'white'};
  cursor: ${({ disabled }) => disabled ? '' : 'pointer'};
  background-color: transparent;
  border: 0;
`;

export {CarouselContainer,SlideContainer,Slide,Image, IndicatorContainer, LeftArrow, RightArrow}