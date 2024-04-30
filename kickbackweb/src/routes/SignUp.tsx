import React from 'react'
import { LogoBox } from '../styles/User/Login';
import Logo from "../assets/logo3.png"
import SignUpCom from '../components/User/SignUpCom';
import { LoginBackBox } from '../styles/User/Login'
import { useNavigate } from 'react-router';

const SignUp = () => {
  const navigate = useNavigate();
  return (
    <>
      <LoginBackBox>
        <div className='select'>
          <div className='text' style={{ backgroundColor: "#393939", borderRadius: "5px", color: "#bbbbbb" }} onClick={() => navigate("/login")}>KICKBACK 로그인</div>
          <div className='text' style={{ backgroundColor: "#0278f6", borderRadius: "5px", color: "white" }} onClick={() => navigate("/signup")}>KICKBACK 회원가입</div>
        </div>
        <SignUpCom />
      </LoginBackBox>
    </>
  )
}

export default SignUp