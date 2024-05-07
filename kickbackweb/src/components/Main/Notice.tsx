import React, { useEffect, useState } from 'react'
import { NoticeBox, TitleBox } from '../../styles/Main/Notice'
import { ContentBox } from '../../styles/MyPage/MyPage';

import MexicoTrack from "../../assets/MexicoTrack.png"
import CebuTrack from "../../assets/CebuTrack.png"
import UTrack from "../../assets/uphillTrack.png"
import axios from 'axios';
import useAuthStore from '../../stores/AuthStore';
import { useShallow } from 'zustand/react/shallow';

interface SearchData {
  nickname: string,
  profileImage: string,
  rank: number,
  time: string
}

const Notice = () => {
  const [mexico, setMexico] = useState<SearchData>();
  const [cebu, setCebu] = useState<SearchData>();
  const [hill, setHill] = useState<SearchData>();

  const { PATH } = useAuthStore(
    useShallow((state) => ({
      PATH: state.PATH,
    }))
  );

  const getMapData = (map: string) => {
    axios.get(`${PATH}/api/v1/ranking/speed/all`, {
      params: {
        mapName: map,
      }
    })
      .then((res) => {
        if (map === "MEXICO") {
          setMexico(res.data[0]);
        } else if (map === "CEBU") {
          setCebu(res.data[0]);
        } else {
          setHill(res.data[0]);
        }
      })
      .catch((error) => console.log(error))
  }

  useEffect(() => {
    getMapData("MEXICO");
    getMapData("CEBU");
    getMapData("DOWNHILL");
  }, []);

  return (
    <div style={{ backgroundColor: "#f9f9f9" }}>
      <TitleBox>명예의 전당 <span>Hall of Fame</span></TitleBox>
      <NoticeBox>
        <ContentBox>
          <div className="content">
            <div className="record">
              <img src={MexicoTrack} alt="멕시코 기록" />
              <div className="abs">MEXICO</div>
            </div>
            <div className="record">
              <div className='p'>
                <img src={mexico?.profileImage === null ?
                  "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png" :
                  mexico?.profileImage} alt="프로필" />
                <div>{mexico?.nickname}</div>
              </div>
              <div>기록 : {mexico?.time}</div>
            </div>
          </div>
          <div className="content">
            <div className="record">
              <img src={CebuTrack} alt="세부 기록" />
              <div className="abs">CEBU</div>
            </div>
            <div className="record">
              <div className='p'>
                <img src={cebu?.profileImage === null ?
                  "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png" :
                  cebu?.profileImage} alt="프로필" />
                <div>{cebu?.nickname}</div>
              </div>
              <div>기록 : {cebu?.time}</div>
            </div>
          </div>
          <div className="content">
            <div className="record">
              <img src={UTrack} alt="다운힐 기록" />
              <div className="abs">DOWN HILL</div>
            </div>
            <div className="record">
              <div className='p'>
                <img src={hill?.profileImage === null ?
                  "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png" :
                  hill?.profileImage} alt="프로필" />
                <div>{hill?.nickname}</div>
              </div>
              <div>기록 : {hill?.time}</div>
            </div>
          </div>
        </ContentBox>
      </NoticeBox>
    </div>
  )
}

export default Notice