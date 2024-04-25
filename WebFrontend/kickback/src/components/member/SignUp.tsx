import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import styles from "./Login.module.css";

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

    if (cpassword !== formData.password) {
      setMessage("비밀번호가 일치하지 않습니다.");
      return;
    } else if (
      !formData.email ||
      !formData.nickname ||
      !formData.password ||
      !cpassword
    ) {
      setMessage("입력하지 않은 부분이 있는지 다시 한 번 확인해 주세요");
      return;
    } else if (formData.email.length < 4) {
      setMessage("이메일을 다시 한 번 확인해 주세요.");
      return;
    } else if (formData.nickname.length < 3) {
      setMessage("닉네임은 4자 이상 입력해야 합니다.");
      return;
    } else if (formData.password.length < 4) {
      setMessage("비밀번호를 다시 한 번 확인해 주세요.");
      return;
    }

    setMessage("");

    try {
      const response = await axios.post(
        "http://localhost:8080/api/v1/member/signup",
        formData
      );

      // 회원가입이 성공하면 토큰을 받아와 localStorage에 저장
      localStorage.setItem("token", response.data.token);

      alert("회원가입이 완료되었습니다.");
      navigate("/notice");
    } catch (error) {
      console.error("회원가입 요청 처리 중 문제 발생:", error);
      alert("회원가입 처리 중 문제가 발생했습니다.");
    }
    console.log("FORM DATA", formData);
  };

  // 로그인 상태 확인 함수
  const isLoggedIn = () => {
    return localStorage.getItem("token") !== null;
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
          처음 오셨나요? <span className={styles["link"]}>회원가입</span>
        </p>
      </form>
    </div>
  );
}

export default SignUp;
