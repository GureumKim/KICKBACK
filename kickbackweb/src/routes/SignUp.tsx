import React, { useEffect } from 'react'
import SignUpCom from '../components/User/SignUpCom';
import { LoginBackBox, LogoBox } from '../styles/User/Login'
import { useNavigate } from 'react-router';
import select1 from "../assets/select1.png"
import logo from "../assets/logo.png"

const SignUp = () => {
  const navigate = useNavigate();
  useEffect(() => {
    window.scrollTo(0, 0);
  })
  return (
    <div style={{ backgroundImage: `url(${select1})`, height: "120vh", backgroundSize: "cover" }}>
      <div style={{ display: "flex", height: "100%", width: "100%", alignItems: "center", flexDirection: "column", justifyContent:"center" }}>
        <LogoBox>
          <img src={logo} alt="로고" />
        </LogoBox>
        <LoginBackBox>
          <SignUpCom />
        </LoginBackBox>
      </div>
    </div>
  )
}

export default SignUp