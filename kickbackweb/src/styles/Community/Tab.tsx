import styled from 'styled-components';

export const Tab = styled.div`
  width: 100%;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: space-around;
  background-color: rgba(37, 36, 40, 0.5);
  color: white;
  
  :hover {
    transition: all 0.2s;
    transform: scale(1.3);
    font-weight: bold;
  }

  .activate {
    transform: scale(1.3);
    font-weight: bold;
  }
`

export const menu = styled.div`
  transition: all 0.2s;
  cursor: pointer;
`