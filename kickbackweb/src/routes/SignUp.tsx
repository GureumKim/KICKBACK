import React, { useEffect } from 'react'
import SignUpCom from '../components/User/SignUpCom';
import { LoginBackBox, LogoBox } from '../styles/User/Login'
import { useNavigate } from 'react-router';
import select1 from "../assets/signup1.png"
import logo from "../assets/logo4.png"

const SignUp = () => {
  const navigate = useNavigate();
  useEffect(() => {
    window.scrollTo(0, 0);
  })
  return (
    <div style={{ backgroundImage: `url(${select1})`, height: "100%", backgroundSize: "cover" }}>
      <div style={{ display: "flex", height: "100%", width: "100%", alignItems: "center", flexDirection: "column", justifyContent:"center" }}>
        <LogoBox>
          회원가입
        </LogoBox>
        <LoginBackBox>
          <SignUpCom />
        </LoginBackBox>
      </div>
    </div>
  )
}

export default SignUp