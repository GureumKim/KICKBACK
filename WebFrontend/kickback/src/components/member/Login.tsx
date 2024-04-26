import React, { useState } from "react";
import styles from "./Login.module.css";
import useBearStore from "../state/state";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import API from "../../config.js";

interface UserInfo {
  email: string;
  password: string;
}

function Login() {
  const [formData, setFormData] = useState<UserInfo>({
    email: "",
    password: "",
  });
  const [message, setMessage] = useState<string>("");

  const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { id, value } = e.target;
    setFormData((prev) => ({ ...prev, [id]: value }));
  };

  const login = useBearStore((state) => state.login);
  const navigate = useNavigate();

  const onSubmitHandler = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (formData.email.length < 4) {
      setMessage("이메일을 다시 한 번 확인해 주세요.");
      return;
    } else if (formData.password.length < 4) {
      setMessage("비밀번호를 다시 한 번 확인해 주세요.");
      return;
    }
    setMessage("");

    try {
      const response = await axios.post(`${API.LOGIN}`, formData);

      console.log(response);
      console.log(response.headers);
      console.log(response.data);

      if (response.status === 200) {
        const accessToken = response.headers["accesstoken"];
        const refreshToken = response.headers["refreshtoken"];

        console.log(
          "accessToken ",
          accessToken,
          " refreshToken ",
          refreshToken
        );
        // 추출한 토큰을 로컬 스토리지에 저장
        localStorage.setItem("accessToken", accessToken);

        // ZUSTAND에 업데이트하기
        const userInfo = response.data.nickname;

        login(userInfo);
        navigate("/notice");
      } else {
        alert("로그인에 실패했습니다.");
      }
    } catch (error) {
      console.error("로그인 요청 중 오류 발생", error);
      alert("로그인 처리 중 문제가 발생했습니다.");
    }
  };

  return (
    <div className={styles["main"]}>
      <form className={styles["form"]} onSubmit={onSubmitHandler}>
        <div className={styles["user-image"]}>
          <div className={styles["head"]} />
          <div className={styles["body"]} />
        </div>
        <p className={styles["heading"]}>Login</p>
        <div className={styles["inputs-div"]}>
          <input
            onChange={onChange}
            value={formData.email}
            type={"text"}
            id="email"
            name="email"
            placeholder="이메일"
          />
          <input
            onChange={onChange}
            value={formData.password}
            type={"password"}
            id="password"
            name="password"
            placeholder="비밀번호"
          />
          <button type="submit">Submit</button>
          <div className={styles["err-msg-div"]}>{message}</div>
        </div>
        <p className={styles["p-link"]}>
          처음 오셨나요?{" "}
          <span className={styles["link"]} onClick={() => navigate("/signup")}>
            회원가입
          </span>
        </p>
      </form>
    </div>
  );
}

export default Login;
