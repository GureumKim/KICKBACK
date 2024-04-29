import axios from "axios";
import { Navigate } from "react-router-dom";
import API from "../../config.js";
import { useEffect, useState } from "react";

// 프로필 내용을 표시하는 컴포넌트
function ProfileContent() {
  // 프로필 정보 받아오기
  const [profile, setProfile] = useState(null);

  useEffect(() => {
    const getProfile = async () => {
      try {
        const token = localStorage.getItem("accessToken");
        const config = {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        };
        const response = await axios.get(`${API.PROFILE}`, config);
        setProfile(response.data);
        console.log(response.data);
      } catch (error) {
        console.error(error);
        // 예외 처리 로직 추가
        if (error.response && error.response.status === 401) {
          // 토큰이 만료되거나 유효하지 않은 경우
          redirectToLogin();
        } else {
          // 다른 예외 처리
          alert("프로필 정보를 가져오는 데 실패했습니다.");
        }
      }
    };
    getProfile();
  }, []);

  if (!profile) {
    return null; // 프로필이 없으면 아무것도 렌더링하지 않음
  }

  return (
    <>
      <h1>{profile.dataBody.nickname}님 환영합니다.</h1>
      <span>사용자 이메일: {profile.dataBody.email}</span>
      <br />
      프로필 이미지:{" "}
      {profile.dataBody.profileImage ? (
        <img src={profile.dataBody.profileImage} />
      ) : (
        <img src="default_profile.png" />
      )}
    </>
  );
}

// 조건에 따라 로그인 페이지로 이동시키는 함수
function redirectToLogin() {
  return <Navigate to="/login" />;
}

function Profile() {
  const token = localStorage.getItem("accessToken");

  // 토큰이 null이면 로그인 페이지로 이동, 아니면 프로필 내용을 표시
  return <>{token === null ? redirectToLogin() : <ProfileContent />}</>;
}

export default Profile;
