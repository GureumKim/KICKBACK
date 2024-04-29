import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import styles from "./Login.module.css";
import useBearStore from "../state/state";
import API from "../../config.js";

interface UserInfo {
  email: string;
  password: string;
  nickname: string;
}

function SignUp() {
  const [formData, setFormData] = useState<UserInfo>({
    email: "",
    password: "",
    nickname: "",
  });

  const [message, setMessage] = useState<string>("");
  const [cpassword, setCPassword] = useState<string>("");

  const login = useBearStore((state) => state.login);
  const navigate = useNavigate();

  const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.id === "cpassword") {
      setCPassword(e.target.value);
    } else {
      setFormData((prev) => ({
        ...prev,
        [e.target.id]: e.target.value,
      }));
    }
  };

  const onSubmitHandler = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    try {
      // 회원가입 요청
      await axios.post(`${API.SIGNUP}`, formData);

      // 회원가입 성공 후 로그인 요청
      const loginResponse = await axios.post(`${API.LOGIN}`, {
        email: formData.email,
        password: formData.password,
      });

      if (loginResponse.status === 200) {
        // 로그인 성공 시 토큰 저장
        const accessToken = loginResponse.headers["accesstoken"];
        localStorage.setItem("accessToken", accessToken);

        // Zustand 상태 업데이트
        const userInfo = loginResponse.data.dataBody.memberInfo.nickname;
        login(accessToken, userInfo);
        navigate("/notice");
      } else {
        alert("로그인에 실패했습니다.");
      }
    } catch (error) {
      console.error("회원가입 또는 로그인 요청 처리 중 문제 발생:", error);
      alert("처리 중 문제가 발생했습니다.");
    }
  };

  return (
    <div className={styles["main"]}>
      <form className={styles["form"]} onSubmit={onSubmitHandler}>
        <div className={styles["user-image"]}>
          <div className={styles["head"]} />
          <div className={styles["body"]} />
        </div>
        <p className={styles["heading"]}>SignUp</p>
        <div className={styles["inputs-div"]}>
          <input
            onChange={onChange}
            value={formData.email}
            type="text"
            id="email"
            name="email"
            placeholder="이메일"
          />
          <input
            onChange={onChange}
            value={formData.nickname}
            type="text"
            id="nickname"
            name="nickname"
            placeholder="별명"
          />
          <input
            onChange={onChange}
            value={formData.password}
            type="password"
            id="password"
            name="password"
            placeholder="비밀번호"
          />
          <input
            onChange={onChange}
            value={cpassword}
            type="password"
            id="cpassword"
            name="cpassword"
            placeholder="비밀번호 확인"
          />

          <button>submit</button>
          <div className={styles["err-msg-div"]}>{message}</div>
        </div>
        <p className={styles["p-link"]}>
          아이디가 있으신가요?{" "}
          <span className={styles["link"]} onClick={() => navigate("/login")}>
            로그인
          </span>
        </p>
      </form>
    </div>
  );
}

export default SignUp;
