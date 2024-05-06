import styled from "styled-components";
import img from "../../assets/carouselnav.png"

export const CarouselContainer = styled.div`
  width: 100%;
  margin: 0 auto;
  height: auto;
  overflow: hidden;
  position: relative;
  object-fit: contain;
`;

// export const SlideContainer = styled.div`
//   display: flex;
//   width: 100%;
//   height: 600px;
//   position: relative;
// `;

// export const Slide = styled.div`
//   flex: 0 0 auto;
//   width: 100%;
//   position: relative;
// `;

export const SlideContainer = styled.div`
  display: flex;
  width: 100%;
  height: 600px;
  position: relative;
`;

export const Slide = styled.div`
  position: absolute;
  width: 100%;
  height: 100%;
  transition: opacity 1.5s ease-in-out;
`;

export const Image = styled.img`
  width: 100%;
  height: 600px;
  position: relative;
`;

export const LeftArrow = styled.button`
  font-size: 40px;
  color: #ffffff;
  cursor: ${({ disabled }) => disabled ? '' : 'pointer'};
  background-color: transparent;
  border: 0;
`;

export const RightArrow = styled.button`
  font-size: 40px;
  color: #ffffff;
  cursor: ${({ disabled }) => disabled ? '' : 'pointer'};
  background-color: transparent;
  border: 0;
`;

export const CadBox = styled.div`
  width: 100%;
  height: auto;
  position: relative;
  z-index: 10;
`

export const InBox = styled.div`
  width: 100%;
  height: 74px;
  margin: 0 auto;
  display: flex;
  flex-direction: row;
  position: absolute;
  top:-74px;
  
  background-image: url(${img});
  background-size: cover;
  background-repeat: no-repeat;
  .item {
    flex: 1;
  }

  .item:nth-child(1) {
    flex: 10%;
    text-align: end;
    margin-top: 10px;
  }

  .item:nth-child(2) {
    flex: 30%;
  }

  .item:nth-child(3) {
    flex: 20;
    position: relative;
    
    img {
      position: absolute;
      top: -30px;
      left: 10%;
      width: 200px;
      height: 200px;
      cursor: pointer;
      border: 5px solid #151515;
      border-radius: 1000px;
    }
  }

  .item:nth-child(4) {
    flex: 30%;
  }

  .item:nth-child(5) {
    flex: 10%;
    text-align: start;
    margin-top: 10px;
  }
`