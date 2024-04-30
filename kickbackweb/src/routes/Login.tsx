import React from 'react'
import LoginCom from '../components/User/LoginCom';
import { LoginBackBox } from '../styles/User/Login'
import { useNavigate } from 'react-router';

const Login = () => {
  const navigate = useNavigate();
  return (
    <>
      <LoginBackBox>
        <div className='select'>
          <div className='text' style={{ backgroundColor: "#0278f6", borderRadius: "5px", color: "white" }} onClick={() => navigate("/login")}>KICKBACK 로그인</div>
          <div className='text' style={{ backgroundColor: "#393939", borderRadius: "5px", color: "#bbbbbb" }} onClick={() =>  navigate("/signup")}>KICKBACK 회원가입</div>
        </div>
        <LoginCom />
      </LoginBackBox>
    </>
  )
}

export default Login