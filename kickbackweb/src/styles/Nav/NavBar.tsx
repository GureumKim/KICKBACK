import styled from "styled-components";

const Hme = styled.div`
    width: 100%;
    height: auto;
    background-color: #252428;
    color: white;
    position: relative;
    position: sticky;
    top: -60px;
    z-index: 11;

    .top {
        width: 100%;
        text-align: center;
        height: 60px;
        background-color: whitesmoke;
    }
`

const MenuBox = styled.div`
    width: 75%;
    margin: 0 auto;
    height: auto;
    display: flex;
    justify-content: space-between;
    align-items: center;
    flex-direction: row;
    background-color: #252428;
    
    
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
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif !important;
            
            .text {
                margin-bottom: 20px;
                height: auto;
                font-size: 14px;
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
    width: 12%;
    height: max-content;
    cursor: pointer;
    padding: 10px 0;
`

export { MenuBox, Hme, LogoImg };