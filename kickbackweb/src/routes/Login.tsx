import React, { useState } from 'react'
import { LogoBox } from '../styles/User/Login';
import Logo from "../assets/logo3.png"
import LoginCom from '../components/User/LoginCom';
import SignUpCom from '../components/User/SignUpCom';
import { LoginBackBox } from '../styles/User/Login'
import { useNavigate } from 'react-router';

const Login = () => {
  const [doLogin,setDoLogin] = useState(true);
  const navigate = useNavigate();

  return (
    <>
      <LogoBox>
        <img src={Logo} alt="해위" onClick={() => navigate("/")} style={{ cursor:"pointer" }}/>
      </LogoBox>
      <LoginBackBox>
        <div className='select'>
          <div className='text' style={{ backgroundColor: doLogin ? "#0278f6": "#393939", borderRadius: "5px", color: doLogin ? "white":"#bbbbbb" }} onClick={() => setDoLogin(true)}>KICKBACK 로그인</div>
          <div className='text' style={{ backgroundColor: !doLogin ? "#0278f6": "#393939", borderRadius: "5px", color: !doLogin ? "white":"#bbbbbb" }} onClick={() => setDoLogin(false)}>KICKBACK 회원가입</div>
        </div>
        {doLogin ? <LoginCom />:<SignUpCom />}
      </LoginBackBox>
    </>
  )
}

export default Login