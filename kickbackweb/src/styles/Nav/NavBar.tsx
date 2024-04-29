import styled from "styled-components";

const Hme = styled.div`
    width: 100%;
    height: auto;
    background-color: whitesmoke;
    position: sticky;
    top: 0;
    z-index: 11;
`

const MenuBox = styled.div`
    width: 90%;
    margin: 0 auto;
    height: auto;
    display: flex;
    justify-content: space-between;
    align-items: center;
    flex-direction: row;

    .item {
        flex: 1;
        text-align: center;
        font-size: 20px;
        height: 70px;
        margin: 10px 0;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .item:nth-child(1) {
        flex: 10%;
        font-size: 35px;
        text-align: start;
    }

    .item:nth-child(2) {
        flex: 80%;
    }

    .item:nth-child(3) {
        flex: 10%;
        display: flex;
        align-items: center;
        justify-content: center;
        flex-direction: row;
        font-size: 25px;
    }
`

const LogoImg = styled.img`
    width: 28%;
    height: max-content;
    cursor: pointer;
`

export { MenuBox, Hme, LogoImg };