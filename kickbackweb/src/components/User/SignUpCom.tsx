import React, { useState } from 'react'
import { FaRegEyeSlash } from "react-icons/fa";
import { IoEyeOutline } from "react-icons/io5";
import { useNavigate } from 'react-router';
import useAuthStore from '../../stores/AuthStore';
import { useShallow } from 'zustand/react/shallow';
import axios from 'axios';

const SignUpCom = () => {
  const [nickname, setNickname] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [password2, setPassword2] = useState("");
  const navigate = useNavigate();
  const [pwShow, setPwShow] = useState(false);
  const [pw2Show, setPw2Show] = useState(false);

  const { PATH, login } =
    useAuthStore(
      useShallow((state) => ({
        PATH: state.PATH,
        login: state.login
      }))
    );

  const changeNickName = (event: React.ChangeEvent<HTMLInputElement>) => {
    setNickname(event.target.value);
  }

  const changeEmail = (event: React.ChangeEvent<HTMLInputElement>) => {
    setEmail(event.target.value);
  }

  const changePassword = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(event.target.value);
  }

  const changePassword2 = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPassword2(event.target.value);
  }

  const SignUp = async () => {
    axios.post(`${PATH}/api/v1/member/signup`, {
      email,
      password,
      nickname
    }, {
      withCredentials: true
    })
      .then((res) => {
        console.log(res)
        navigate("/login")
      })
      .catch((error) => {
        console.log(error)
        // alert(error.response.data.dataHeader.resultMessage)
      })
  }

  return (
    <>
      <div className='item'>
        <div className='content'>닉네임</div>
        <input className='content' placeholder='최대 6글자' maxLength={6} value={nickname} onChange={changeNickName}/>
        <div className='content'></div>
      </div>
      <div className='item'>
        <div className='content'>아이디</div>
        <input className='content' placeholder='이메일' type='email' maxLength={20} value={email} onChange={changeEmail} />
        <div className='content'></div>
      </div>
      <div className='item'>
        <div className='content'>비밀번호</div>
        <input className='content' placeholder='특수문자, 영문, 숫자 포함 8 ~ 16 자리' type={pwShow ? 'text' : 'password'} value={password} onChange={changePassword} maxLength={16} />
        {pwShow ? <FaRegEyeSlash className='abs' onClick={() => setPwShow(false)} /> : <IoEyeOutline className='abs' onClick={() => setPwShow(true)} />}
      </div>
      <div className='item'>
        <div className='content'>비밀번호 확인</div>
        <input className='content' placeholder='비밀번호 재 입력' type={pw2Show ? 'text' : 'password'} value={password2} onChange={changePassword2} maxLength={16} />
        {pw2Show ? <FaRegEyeSlash className='abs' onClick={() => setPw2Show(false)} /> : <IoEyeOutline className='abs' onClick={() => setPw2Show(true)} />}
      </div>
      <div className='item'>
        <div className='btn' onClick={SignUp}>회원가입</div>
      </div>
    </>
  )
}

export default SignUpCom