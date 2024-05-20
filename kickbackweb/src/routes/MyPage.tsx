import React, { useEffect, useRef, useState } from "react";
import { IntroBox, ImgBox, TextBox } from "../styles/Intro/GameIntro";
import img1 from "../assets/uphillTrack.png";
import { UserBox, RecordBox, ContentBox } from "../styles/MyPage/MyPage";
import useUserStore from "../stores/UserStore";
import { useShallow } from "zustand/react/shallow";
import axios from "axios";
import useAuthStore from "../stores/AuthStore";

import MexicoTrack from "../assets/MexicoTrack.png"
import CebuTrack from "../assets/CebuTrack.png"
import UTrack from "../assets/uphillTrack.png"
import { Cookies } from "react-cookie";

interface SearchData {
  nickname: string,
  profileImage: string,
  rank: number,
  time: string
}

const MyPage = () => {
  const [mexico, setMexico] = useState<SearchData>();
  const [cebu, setCebu] = useState<SearchData>();
  const [hill, setHill] = useState<SearchData>();
  const [complete,setComplete] = useState(false);
  
  const { nickname, profileImage,setUser } = useUserStore(
    useShallow((state) => ({
      nickname: state.nickname,
      profileImage: state.profileImage,
      setUser: state.setUser,
    }))
  );
  const { PATH } = useAuthStore(
    useShallow((state) => ({
      PATH: state.PATH,
    }))
  );

  const getMapRecord = (map: string) => {
    axios
      .get(`${PATH}/api/v1/ranking/search`, {
        params: {
          mapName: map,
          nickname,
        },
      })
      .then((res) => {
        if (map === "MEXICO") {
          setMexico(res.data.dataBody);
        } else if (map === "CEBU") {
          setCebu(res.data.dataBody);
        } else {
          setHill(res.data.dataBody);
        }
      })
      .catch((error) => {
        console.log(error);
      });
  };

  useEffect(() => {
    getMapRecord("MEXICO");
    getMapRecord("CEBU");
    getMapRecord("DOWNHILL");
  }, []);
  
  const [profileFile, setProfileFile] = useState<File | null>(null);
  const [avatar, setAvatar] = useState(profileImage === null ? "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png" : profileImage);
  const fileInput = useRef<HTMLInputElement>(null); // 파일 입력을 위한 ref 생성

  const uploadImg = (event: React.ChangeEvent<HTMLInputElement>) => {
    if (event.target.files && event.target.files[0]) {
      const uploadFile = event.target.files[0];

      // 이미지 파일 상태 업데이트
      setProfileFile(uploadFile);

      // 미리보기 이미지 설정
      const reader = new FileReader();
      reader.readAsDataURL(uploadFile);
      reader.onloadend = () => {
        const result = reader.result as string;
        setAvatar(result);
      };
    }
  };

  const profileUpload = async () => {
    if (!profileFile || !nickname) {
      alert("프로필 이미지 또는 닉네임이 설정되지 않았습니다.");
      return;
    }

    const formData = new FormData();
    
    formData.append('profileImage', profileFile); // 파일 추가
    formData.append('nickname', nickname); // 닉네임 추가

    try {
      const response = await axios.patch(`${PATH}/api/v1/member/update`, formData, {
        withCredentials: true,
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });
      setUser(response.data.dataBody)
      setComplete(false)
    } catch (error) {
      alert('프로필 업데이트 실패');
    }
  };

  return (
    <IntroBox>
      <ImgBox>
        <img src={img1} alt="이미지" />
        <div className="text">마이페이지</div>
      </ImgBox>
      <UserBox>
        <div className="item">
          <img
            src={avatar}
            alt="프로필"
          />
          <input
            type="file"
            accept="image/jpg,image/jpeg, image/png"
            name="profile_img"
            onChange={uploadImg}
            ref={fileInput}
            style={{ display: "none" }}
          />
          {complete ? <div className='updata-btn' onClick={profileUpload}>완료</div> : <div onClick={() => {
              if (fileInput.current) {
                fileInput.current.click();
              }
              setComplete(true)
            }} className='updata-btn'>프로필 수정</div>}
        </div>
        <div className="item"></div>
      </UserBox>
      <RecordBox>
        <div className="text">나의 기록 <span>MY RECORD</span></div>
        <ContentBox>
          <div className="content">
            <div className="record">
              <img src={MexicoTrack} alt="멕시코 기록" />
              <div className="abs">MEXICO</div>
            </div>
            <div className="record">
              {mexico?.time}
            </div>
          </div>
          <div className="content">
            <div className="record">
              <img src={CebuTrack} alt="세부 기록" />
              <div className="abs">CEBU</div>
            </div>
            <div className="record">
              {cebu?.time}
            </div>
          </div>
          <div className="content">
            <div className="record">
              <img src={UTrack} alt="다운힐 기록" />
              <div className="abs">DOWN HILL</div>
            </div>
            <div className="record">
              {hill?.time}
            </div>
          </div>
        </ContentBox>
      </RecordBox>
    </IntroBox>
  );
};

export default MyPage;
