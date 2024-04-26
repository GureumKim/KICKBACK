import styled from "styled-components";

const CarouselContainer = styled.div`
  position: relative;
  width: 100%;
  height: 600px;
  overflow: hidden;
`;

const SlideContainer = styled.div`
  display: flex;
  width: 100%;
  height: 600px;
`;

const Slide = styled.div`
  flex: 0 0 auto;
  width: 100%;
  
`;

const Image = styled.img`
  width: 100%;
  height: 600px;
`;

const IndicatorContainer = styled.div`
    position: absolute;
    z-index: 2;
    bottom: 5%;
    left: 47%;
    width: auto;
    height: auto;
    display: flex;
    flex-direction: row;
`

const Indicator = styled.div`
    width: 10px;
    height: 10px;
    border-radius: 50%;
    margin: 0 10px;
    cursor: pointer;
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

export {CarouselContainer,SlideContainer,Slide,Image, IndicatorContainer, Indicator, LeftArrow, RightArrow}