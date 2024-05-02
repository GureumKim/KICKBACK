import axios from "axios";
import { Navigate, useNavigate } from "react-router-dom";
import API from "../../config.js";
import { useEffect, useState } from "react";

function ProfileContent() {
  const navigate = useNavigate();
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
        if (error.response && error.response.status === 401) {
          // 토큰이 만료되거나 유효하지 않은 경우, 로그인 페이지로 이동
          navigate("/login");
        } else {
          alert("프로필 정보를 가져오는 데 실패했습니다.");
        }
      }
    };
    getProfile();
  }, [navigate]);

  if (!profile) {
    return null;
  }

  return (
    <>
      <h1>{profile.dataBody.nickname}님 환영합니다.</h1>
      <span>사용자 이메일: {profile.dataBody.email}</span>
      <br />
      프로필 이미지:{" "}
      {profile.dataBody.profileImage ? (
        <img src={profile.dataBody.profileImage} alt="프로필 이미지" />
      ) : (
        <img src="default_profile.png" alt="기본 프로필 이미지" />
      )}
      <br />
      <button onClick={() => navigate("/profile/change")}>프로필 변경</button>
      <button onClick={() => navigate("/profile/password")}>
        비밀번호 변경
      </button>
    </>
  );
}

function Profile() {
  const token = localStorage.getItem("accessToken");
  const navigate = useNavigate();

  useEffect(() => {
    if (token === null) {
      navigate("/login");
    }
  }, [token, navigate]);

  return token === null ? null : <ProfileContent />;
}

export default Profile;
