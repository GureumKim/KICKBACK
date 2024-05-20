import axios from "axios";
import API from "../../config.js";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

interface Qna {
  email: string;
  content: string;
}

function Qna() {
  const navigate = useNavigate();
  const [qna, setQna] = useState<Qna>({
    email: "",
    content: "",
  });
  // 로딩 상태 추가
  const [isLoading, setIsLoading] = useState(false);

  const validateEmail = (email: string) => {
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return regex.test(email);
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    setIsLoading(true); // 요청 전에 로딩 상태를 true로 설정합니다.
    try {
      await axios.post(`${API.QNA}`, qna);
      navigate("/board");
      alert("문의사항이 제출되었습니다.");
    } catch (error) {
      alert("문의사항 처리 중 오류가 발생했습니다.");
      console.error("문의사항 처리 중 오류가 발생했습니다.", error);
    } finally {
      setIsLoading(false); // 요청이 완료되면 로딩 상태를 false로 설정합니다.
    }
  };
  return (
    <>
      <h1>Q&A</h1>
      <form onSubmit={handleSubmit}>
        <h2>이메일 주소</h2>
        <input
          type="email"
          value={qna.email}
          placeholder="이메일"
          onChange={(e) => setQna({ ...qna, email: e.target.value })}
        ></input>
        <h2>문의내용</h2>
        <textarea
          placeholder="문의내용"
          value={qna.content}
          onChange={(e) => setQna({ ...qna, content: e.target.value })}
        ></textarea>
        <br />
        <button type="submit" disabled={!validateEmail(qna.email) || isLoading}>
          보내기
        </button>
      </form>
      {isLoading && <p>전송하는 중...</p>}{" "}
      {/* 로딩 상태일 때 메시지를 보여줍니다 */}
    </>
  );
}

export default Qna;
