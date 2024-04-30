import { useNavigate } from "react-router-dom";

function Start() {
  const navigate = useNavigate();
  // start 버튼을 누를 때 작동하는 함수
  // 로그인 되어있으면 공지사항 페이지로, 로그인이 안 되어 있으면 로그인 페이지로 이동함.
  const startButton = () => {
    const token = localStorage.getItem("accessToken");
    if (token) {
      navigate("/notice");
    } else {
      navigate("/login");
    }
  };

  return (
    <>
      <h1>시작하시겠습니까?</h1>
      <button onClick={startButton}>Start</button>
    </>
  );
}

export default Start;
