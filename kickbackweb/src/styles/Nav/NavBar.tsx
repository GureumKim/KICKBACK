import styled from "styled-components";

const Hme = styled.div`
    width: 100%;
    height: auto;
    background-color: #393939;
    position: fixed;
    color: white;
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
    background-color: #393939;

    .item {
        flex: 1;
        text-align: center;
        font-size: 20px;
        height: 70px;
        padding: 20px 0;
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
            font-size: 25px;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif !important;
            font-weight: 700;
            
            .text {
                margin-bottom: 20px;
                height: auto;
                font-size: 20px;
                color: #cdcdcd;
                cursor: pointer;
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif !important;
                font-weight: 100;

                &:hover {
                color: white;
                text-decoration: underline;
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
`

const LogoImg = styled.img`
    width: 28%;
    height: max-content;
    cursor: pointer;
`

export { MenuBox, Hme, LogoImg };