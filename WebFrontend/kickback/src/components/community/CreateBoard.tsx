import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import useBearStore from "../state/state";
import API from "../../config.js";

interface Board {
  title: string;
  content: string;
}

function CreateBoard() {
  // 토큰값을 넘겨주기 위한 로직
  const token = localStorage.getItem("accessToken");
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };
  const { isAuthenticated } = useBearStore();
  const navigate = useNavigate();
  const [boardData, setBoardData] = useState<Board>({
    title: "",
    content: "",
  });

  // 인증되지 않은 경우 메인 페이지로 리다이렉트합니다.
  useEffect(() => {
    if (!isAuthenticated) {
      navigate("/login");
    }
  }, [isAuthenticated, navigate]);

  // 게시글 생성 처리 함수
  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    try {
      // API 호출을 통해 게시글 생성
      await axios.post(API.CREATE_BOARD, boardData, config);

      // 게시글 생성 후 /board 페이지로 리다이렉트
      navigate("/board");
    } catch (error) {
      console.error("게시글 생성 중 오류가 발생했습니다.", error);
      // 오류 처리 로직 추가 (예: 사용자에게 오류 메시지 표시)
    }
  };

  return (
    <>
      <h1>게시글 생성</h1>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="제목"
          value={boardData.title}
          onChange={(e) =>
            setBoardData({ ...boardData, title: e.target.value })
          }
          required
        />
        <textarea
          placeholder="내용"
          value={boardData.content}
          onChange={(e) =>
            setBoardData({ ...boardData, content: e.target.value })
          }
          required
        />
        <button type="submit">게시글 생성</button>
      </form>
    </>
  );
}

export default CreateBoard;
