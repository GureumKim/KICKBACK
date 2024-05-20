import styled, {keyframes} from "styled-components";

const fadeIn = keyframes `
    from {
        opacity: 0;
    } to {
        opacity: 1;
    }
`

const Hme = styled.div`
  width: 100%;
  height: 60px;
  background-color: #252428;
  color: white;
  z-index: 11;
  &.active {
    position: absolute;
    top: 80px;
  }
`;

const MenuBox = styled.div`
  width: 100%;
  margin: 0 auto;
  height: auto;
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-direction: row;
  background-color: rgba(37, 36, 40, 0.9);

  &.active {
    animation: ${fadeIn} 0.2s ease-out;
  }

  .item {
    flex: 1;
    text-align: center;
    padding: 12px 0;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .item:nth-child(1) {
    flex: 80%;
    display: flex;
    flex-direction: row;
    height: auto;

    .content {
      flex: 20%;
      height: auto;
      text-align: center;
      font-size: 22px;
      font-family: 'LINESeedKR-Bd' !important;

      .text {
        margin-bottom: 20px;
        height: auto;
        font-size: 14px;
        color: #cdcdcd;
        cursor: pointer;
        font-family: 'LINESeedKR-Bd' !important;
        font-weight: 100;
        position: relative;

        &:hover {
          color: white;
          text-decoration: underline;
        }

        span {
          position: absolute;
          top: 18px;
          left: 0;
          right: 0;
          font-size: 10px;
          color: #cdcdcd;
          font-style: italic;
        }
      }
    }

    img {
      width: 20%;
    }
  }

  .item:nth-child(2) {
    flex: 20%;
    height: auto;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: row;
    font-size: 20px;
    color: #bbbbbb;
  }
`;

const LogoImg = styled.div`
  width: 100%;
  height: 80px;
  display: flex;
  justify-content: center;
  align-items: center;

  img {
    width: 10%;
    cursor: pointer;
  }
`;

export { MenuBox, Hme, LogoImg };
