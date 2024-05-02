import { Navigate, useNavigate } from "react-router-dom";
import axios from "axios";
import { SubmitHandler, useForm } from "react-hook-form";
import API from "../../config.js";
import { useState } from "react";

type Inputs = {
  currentPassword: string;
  updatedPassword: string;
  checkPassword: string;
};

// 비밀번호 변경 관련 화면에 표시될 내용 컴포넌트
function ChangePasswordContent() {
  const navigate = useNavigate();
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<Inputs>();

  const onSubmit: SubmitHandler<Inputs> = async (data) => {
    const { currentPassword, updatedPassword, checkPassword } = data;
    if (currentPassword === updatedPassword) {
      alert("현재 비밀번호와 새로운 비밀번호가 같습니다.");
      return console.log("현재 비밀번호와 새로운 비밀번호가 같습니다.");
    }
    if (updatedPassword !== checkPassword) {
      // 비밀번호가 일치하지 않을 경우 에러 처리
      alert("비밀번호가 일치하지 않습니다.");
      return console.log("비밀번호가 일치하지 않습니다.");
    }

    try {
      const token = localStorage.getItem("accessToken");
      const config = {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      };
      await axios.patch(`${API.PASSWORD}`, data, config);
      // 비밀번호 변경 성공 시 처리
      alert("비밀번호가 성공적으로 변경되었습니다.");
      // 변경 후 다른 화면으로 이동하고자 할 경우 navigate 함수 사용
      navigate("/profile");
    } catch (error) {
      console.error("비밀번호 변경 실패:", error);
      // 실패 시에 대한 처리
      // 예: 사용자에게 알림 표시
    }
  };

  return (
    <>
      <h1>비밀번호 변경</h1>
      <form onSubmit={handleSubmit(onSubmit)}>
        <input
          type="password"
          placeholder="현재 비밀번호"
          {...register("currentPassword", {
            required: true,
            pattern: {
              value:
                /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,16}$/,
              message: "비밀번호 형식을 다시 점검해주세요.",
            },
          })}
        />{" "}
        <br></br>
        <input
          type="password"
          placeholder="새 비밀번호"
          {...register("updatedPassword", {
            required: true,
            pattern: {
              value:
                /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,16}$/,
              message: "비밀번호 형식을 다시 점검해주세요.",
            },
          })}
        />{" "}
        <br></br>
        <input
          type="password"
          placeholder="새 비밀번호 확인"
          {...register("checkPassword", {
            required: true,
            pattern: {
              value:
                /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,16}$/,
              message: "비밀번호 형식을 다시 점검해주세요.",
            },
          })}
        />
        <br></br>
        {errors.currentPassword && <span>현재 비밀번호를 입력해주세요.</span>}
        {errors.updatedPassword && <span>새로운 비밀번호를 입력해주세요.</span>}
        {errors.checkPassword && <span>비밀번호 확인을 입력해주세요.</span>}
        <br></br>
        <input type="submit" />
      </form>
    </>
  );
}

// 조건에 따라 로그인 페이지로 이동시키는 함수
function redirectToLogin() {
  return <Navigate to="/login" />;
}

function ChangePassword() {
  const token = localStorage.getItem("accessToken");
  return (
    <>
      <>{token === null ? redirectToLogin() : <ChangePasswordContent />}</>
    </>
  );
}

export default ChangePassword;
