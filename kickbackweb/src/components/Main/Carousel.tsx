import React, { useState } from 'react';
import {LoginBox, TopBox, InputTag, UserBox } from '../../styles/Main/MainCarousel';
import downloadBtn from "../../assets/downloadBtn.png"
import { ref, getDownloadURL } from "firebase/storage";
import { storage } from "../../config/Firebase.js";
import mycat from "../../assets/mycats.png"
import useUserStore from '../../stores/UserStore';
import { useShallow } from 'zustand/react/shallow';
import axios from 'axios';
import useAuthStore from '../../stores/AuthStore';
import { useNavigate } from 'react-router';
import { FaRegEyeSlash } from "react-icons/fa";
import { IoEyeOutline } from "react-icons/io5";
import Images from './Images';

const Carousel = () => {
  /**===============================
   * 백업 코드 !! , 사실 필요 없을 듯
   * =========================
   */
  // const handleDownload = () => {

  //   if (!isLogin) {
  //     alert("로그인 후 이용해주세요!")
  //     return;
  //   }
  //   const fileRef = ref(storage, "KICKBACK_v1.0.3.zip");

  //   getDownloadURL(fileRef)
  //     .then((url) => {
  //       console.log("들어옴");
  //       fetch(url)
  //         .then(response => response.blob())
  //         .then(blob => {
  //           const downloadUrl = window.URL.createObjectURL(blob);
  //           const link = document.createElement('a');
  //           link.href = downloadUrl;
  //           link.download = "KICKBACK_v1.0.3.zip";
  //           document.body.appendChild(link);
  //           link.click();
  //           document.body.removeChild(link);
  //           window.URL.revokeObjectURL(downloadUrl);
  //         });
  //     })
  //     .catch((error) => {
  //       console.error('Error downloading the file:', error);
  //     });
  // };

  // 이걸로 진행 정도 표현 하는 거임
  // zustand로 관리해서 
  // 다른 페이지에 있다 메인와도 확인할 수 있으면 더 좋을 듯!
  const [progress, setProgress] = useState(0);
  const downloadFile = () => {
    const fileRef = ref(storage, 'KICKBACK_v1.0.3.zip'); // 'storage' 객체와 경로를 'ref'에 전달
    getDownloadURL(fileRef)
      .then((url: string) => { // url 파라미터에 'string' 타입을 명시
        const xhr = new XMLHttpRequest();
        xhr.responseType = 'blob';
        xhr.onload = (event) => {
          const blob = xhr.response;
          const downloadUrl = window.URL.createObjectURL(blob);
          const link = document.createElement('a');
          link.href = downloadUrl;
          link.download = 'KickBack.zip';
          document.body.appendChild(link);
          link.click();
          document.body.removeChild(link);
          window.URL.revokeObjectURL(downloadUrl);
        };
        xhr.onprogress = (event) => {
          if (event.lengthComputable) {
            const percentage = (event.loaded / event.total) * 100;
            setProgress(percentage);
          }
        };
        xhr.open('GET', url);
        xhr.send();
      })
      .catch((error) => {
        console.error('Error downloading the file:', error);
      });
  };

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();
  const [pwShow, setPwShow] = useState(false);

  const { PATH, login, isLogin, logout } =
    useAuthStore(
      useShallow((state) => ({
        PATH: state.PATH,
        login: state.login,
        isLogin: state.isLogin,
        logout: state.logout
      }))
    );

  const { setUser,nickname, profileImage } =
    useUserStore(
      useShallow((state) => ({
        setUser: state.setUser,
        nickname: state.nickname,
        profileImage: state.profileImage,
      }))
    )

  const changeEmail = (event: React.ChangeEvent<HTMLInputElement>) => {
    setEmail(event.target.value);
  }

  const changePassword = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(event.target.value);
  }

  const Login = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    
    axios.post(`${PATH}/api/v1/member/login`, {
      email,
      password
    }, {
      withCredentials: true
    })
      .then((res) => {
        setUser(res.data.dataBody)
        login()
      })
      .catch((error) => {
        alert(error.response.data.dataHeader.resultMessage)
      })
  }

  return (
    <TopBox>
      {/* 얘는 백업코드 */}
      {/* <Images handleDownload={handleDownload} /> */}
      <Images handleDownload={downloadFile} />
      {/* 여기가 프로세스 코드!!!!!!!  */}
      <p>Download Progress: {progress.toFixed(2)}%</p> 
      <LoginBox>
        <div className='item'>
          <div className='content'>
            <div className='in'>
              <img src={mycat} alt="캣" />
            </div>
            <div className='in'>
              <div className='main'>공지사항</div>
              <div className='text'>최신 클라이언트 버전에서 발견된 버그들을 수정했습니다.</div>
            </div>
          </div>
          {isLogin ? <UserBox>
            <div className='item2'>
              <img src={profileImage === null ? "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png":profileImage} alt='프로필' />
            </div>
            <div className='item2'>
              <div className='text1'>
                <div>{nickname}</div>
                <div className='logout' onClick={logout}>로그아웃</div>
              </div>
              <div className='text1' onClick={() => navigate(`/mypage/${nickname}`)}>
                마이페이지
              </div>
            </div>
          </UserBox> :
            <form className='content' onSubmit={Login}>
              <div className='text'>
                <div className='kick'>KICKBACK 로그인</div>
                <div className='back' onClick={() => navigate("/signup")}>회원가입 »</div>
              </div>
              <div className='con'>
                <div className='con1'>
                  <InputTag>
                    <div className='content2'>아이디</div>
                    <input className='content2' placeholder='이메일' value={email} onChange={changeEmail} maxLength={20} type='email' /></InputTag>
                  <InputTag>
                    <div className='content2'>비밀번호</div>
                    <input className='content2' placeholder='비밀번호' type={pwShow ? 'text' : 'password'} value={password} onChange={changePassword} maxLength={16} />
                    {pwShow ? <FaRegEyeSlash className='abs' onClick={() => setPwShow(false)} /> : <IoEyeOutline className='abs' onClick={() => setPwShow(true)} />}
                  </InputTag>
                </div >
                <div className='con1'><input type="submit" className='btn' value="로그인" /></div>
              </div>
            </form>
          }

        </div>
      </LoginBox>
    </TopBox>
  );
}

export default Carousel;
