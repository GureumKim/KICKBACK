import React, { useState } from 'react'
import { useNavigate } from 'react-router';
import useAuthStore from '../../stores/AuthStore';
import useUserStore from '../../stores/UserStore';
import { useShallow } from "zustand/react/shallow";
import axios from 'axios';
import { FaRegEyeSlash } from "react-icons/fa";
import { IoEyeOutline } from "react-icons/io5";

const LoginCom = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();
  const [pwShow,setPwShow] = useState(false);

  const { PATH, login } =
    useAuthStore(
      useShallow((state) => ({
        PATH: state.PATH,
        login: state.login
      }))
    );

  const { setUser } =
    useUserStore(
      useShallow((state) => ({
        setUser: state.setUser,
      }))
    )

  const changeEmail = (event: React.ChangeEvent<HTMLInputElement>) => {
    setEmail(event.target.value);
  }

  const changePassword = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(event.target.value);
  }

  const Login = async () => {
    axios.post(`${PATH}/api/v1/member/login`, {
      email,
      password
    }, {
      withCredentials: true
    })
      .then((res) => {
        setUser(res.data)
        login()
        navigate("/")
      })
      .catch((error) => {
        console.log(error)
        // alert(error.response.data.dataHeader.resultMessage)
      })
  }

  return (
    <>
      <div className='item'>
        <div className='content'>아이디</div>
        <input className='content' placeholder='이메일' value={email} onChange={changeEmail} maxLength={20} type='email'/>
      </div>
      <div className='item'>
        <div className='content'>비밀번호</div>
        <input className='content' placeholder='특수문자, 영문, 숫자 포함 8 ~ 16 자리' type={pwShow ? 'text':'password'} value={password} onChange={changePassword} maxLength={16}/>
        {pwShow ? <FaRegEyeSlash className='abs' onClick={() => setPwShow(false)}/> : <IoEyeOutline className='abs' onClick={() => setPwShow(true)} />}
      </div>
      <div className='item'>
        <div className='btn' onClick={Login}>로그인</div>
      </div>
    </>
  )
}

export default LoginCom