import React, { useState, useEffect } from 'react';
import { CarouselContainer, SlideContainer, Slide, Image, LeftArrow, RightArrow, CadBox, InBox, LoginBox, TopBox, InputTag, UserBox } from '../../styles/Main/MainCarousel';
import caroueslImg1 from "../../assets/carousel1.png"
import caroueslImg2 from "../../assets/carousel2.png"
import caroueslImg3 from "../../assets/carousel3.png"
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

const Carousel = () => {
  const [currentImage, setCurrentImage] = useState<number>(0);
  const handleDownload = () => {

    if (!isLogin) {
      alert("로그인 후 이용해주세요!")
      return;
    }
    const fileRef = ref(storage, "토키도키.mp3");

    getDownloadURL(fileRef)
      .then((url) => {
        fetch(url)
          .then(response => response.blob())
          .then(blob => {
            const downloadUrl = window.URL.createObjectURL(blob);
            const link = document.createElement('a');
            link.href = downloadUrl;
            link.download = "토키도키.mp3";
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
            window.URL.revokeObjectURL(downloadUrl);
          });
      })
      .catch((error) => {
        console.error('Error downloading the file:', error);
      });
  };

  const [currentSlide, setCurrentSlide] = useState(0);
  const images = [
    caroueslImg1,
    caroueslImg2,
    caroueslImg3
  ]

  useEffect(() => {
    const interval = setInterval(() => {
      goToNextSlide();
    }, 3000);

    return () => clearInterval(interval);
  }, [currentSlide]);

  const goToNextSlide = () => {
    setCurrentSlide((prevSlide) => (prevSlide + 1) % images.length);
  };

  const goToPreviousSlide = () => {
    setCurrentSlide((prevSlide) => (prevSlide === 0 ? images.length - 1 : prevSlide - 1));
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
      <CarouselContainer>
        <SlideContainer
          style={{
            transform: `translateX(-${currentSlide * 100}%)`,
            // transition: "opacity 1.5s ease-in-out"
          }}
        >
          {images.map((image, index) => {
            return (
              <Slide key={index} style={{
                opacity: index === currentSlide ? 1 : 0.7,
                transition: "opacity 1.5s ease-in-out"
              }}>
                <Image src={image} alt={`Slide ${index}`} />
              </Slide>
            )
          })}
        </SlideContainer>
      </CarouselContainer>
      <CadBox>
        <InBox>
          <LeftArrow onClick={goToPreviousSlide} className='item'>&lt;</LeftArrow>
          <div className='item'></div>
          <div className='item'>
            <img src={downloadBtn} alt="다운로드 버튼" onClick={handleDownload} />
          </div>
          <div className='item'></div>
          <RightArrow onClick={goToNextSlide} className='item'>&gt;</RightArrow>
        </InBox>
      </CadBox>
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
              <div className='text1'>
                마이페이지
              </div>
            </div>
          </UserBox> :
            <form className='content' onSubmit={Login}>
              <div className='text'>KICKBACK 로그인</div>
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
