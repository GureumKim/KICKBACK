import styled from "styled-components";

export const Background = styled.div`
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.2);
`

export const Wrapper = styled.div`
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 500px;
  height: 500px;
  padding: 20px;
  background-color: rgba(255, 255, 255, 0.8);
  border: 3px solid lightgray;
  border-radius: 10px;
  box-shadow: 4px 4px 4px rgba(0, 0, 0, 0.3);
`

export const TopBox = styled.div`
  width: 100%;
  display: flex;
  justify-content: end;  
`

export const Close = styled.div`
  width: 40px;
  height: 40px;
  margin-top: -20px;
  color: skyblue;
  font-size: 30px;
  text-align: end;
  transition: all 0.2s;

&:hover {
  transform: scale(1.2);
  transition: all 0.2s;
}
`

export const Title = styled.input`
  width: 100%;
  height: 45px;
  margin-top: 10px;
  padding: 0 5%;
  box-sizing: border-box;
  border-radius: 10px;
  border: none;
  box-shadow: 1px 1px 1px rgba(0, 0, 0, 0.3);
  font-family: 'LINESeedKR-Bd' !important;

  &:focus {
    outline: 3px solid rgb(195, 230, 245);
  }
`

export const Content = styled.textarea`
  width: 100%;
  height: 300px;
  margin-top: 30px;
  padding: 5% 5%; 
  resize: none;
  box-sizing: border-box;
  border: none;
  border-radius: 10px;
  box-shadow: 1px 1px 1px rgba(0, 0, 0, 0.3);
  font-family: 'LINESeedKR-Bd' !important;
  
  &:focus {
    outline: 3px solid rgb(195, 230, 245);
  }
`

export const Submit = styled.button`
  width: 100%;
  height: 45px;
  margin-top: 30px;
  border: none;
  border-radius: 10px;
  box-shadow: 1px 1px 1px rgba(0, 0, 0, 0.3);
  background-color: rgb(195, 230, 245);
  transition: all 0.2s;
  font-family: 'LINESeedKR-Bd' !important;

  &:hover {
    transform: scale(1.03);
    transition: all 0.2s;
  }
`