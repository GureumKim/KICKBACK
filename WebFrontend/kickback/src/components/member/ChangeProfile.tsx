import axios from "axios";
import { useEffect, useState } from "react";
import API from "../../config.js";
import useBearStore from "../state/state";
import { useNavigate } from "react-router-dom";

interface ProfileData {
  nickname: string;
  profileImage: File | null;
}

const ALLOW_FILE_EXTENSION = ["jpg", "jpeg", "png"];
const FILE_SIZE_MAX_LIMIT = 5 * 1024 * 1024; // 5mb 한도

function ChangeProfile() {
  const navigate = useNavigate();
  const [profile, setProfile] = useState<ProfileData>({
    nickname: "",
    profileImage: null,
  });
  const [errorMessage, setErrorMessage] = useState<string>("");
  const token = localStorage.getItem("accessToken");
  const modify = useBearStore((state) => state.modify);
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };

  const handleNicknameChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setProfile({ ...profile, nickname: e.target.value });
  };

  const handleImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files[0];
    if (file) {
      if (!isValidFileType(file)) {
        setErrorMessage(
          "올바른 파일 형식이 아닙니다. jpg, jpeg, png 파일을 업로드해주세요."
        );
        return;
      }
      if (!isValidFileSize(file)) {
        setErrorMessage(
          "파일 크기가 너무 큽니다. 5MB 이하의 파일을 업로드해주세요."
        );
        return;
      }
      setProfile({ ...profile, profileImage: file });
    }
  };

  const isValidFileType = (file: File) => {
    const fileType = file.name.split(".").pop()?.toLowerCase();
    return fileType && ALLOW_FILE_EXTENSION.includes(fileType);
  };

  const isValidFileSize = (file: File) => {
    return file.size <= FILE_SIZE_MAX_LIMIT;
  };

  // 기존 닉네임 로드
  useEffect(() => {
    const fetchProfile = async () => {
      try {
        const response = await axios.get(`${API.PROFILE}`, config);
        setProfile((profile) => ({
          ...profile,
          nickname: response.data.dataBody.nickname,
        }));
      } catch (error) {
        console.error("프로필 정보 로드 실패:", error);
      }
    };

    fetchProfile();
  }, []);

  const handleSubmit = async () => {
    try {
      const formData = new FormData();
      formData.append("nickname", profile.nickname);
      if (profile.profileImage) {
        formData.append("profileImage", profile.profileImage);
      }

      const response = await axios.patch(`${API.CHANGE}`, formData, config);
      console.log(response.data);
      if (response.status === 200) {
        modify(profile.nickname);
        alert("프로필 변경이 완료되었습니다.");
        navigate("/profile");
      }
    } catch (error) {
      console.error("프로필 변경 실패:", error);
      alert("프로필 변경에 실패했습니다.");
    }
  };

  return (
    <>
      <h1>프로필 변경</h1>
      {errorMessage && <p>{errorMessage}</p>}
      <div>
        <label>닉네임:</label>
        <input
          type="text"
          value={profile.nickname}
          onChange={handleNicknameChange}
        />
      </div>
      <div>
        <label>프로필 이미지:</label>
        <input type="file" accept="image/*" onChange={handleImageChange} />
      </div>
      <button onClick={handleSubmit}>저장</button>
    </>
  );
}

export default ChangeProfile;
