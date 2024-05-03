import { Cookies } from "react-cookie";
import { useShallow } from "zustand/react/shallow";
import useAuthStore from "../../stores/AuthStore";
import { useNavigate } from "react-router";
import useUserStore from "../../stores/UserStore";
import { useEffect, useState } from "react";
import axios from "axios";

interface Board {
  title: string;
  content: string;
}

const CreateCommunity = () => {
  const navigate = useNavigate();
  const [boardData, setBoardData] = useState<Board>({
    title: "",
    content: "",
  });
  const { PATH, isLogin } = useAuthStore(
    useShallow((state) => ({
      PATH: state.PATH,
      isLogin: state.isLogin,
    }))
  );

  const { nickname } = useUserStore(
    useShallow((state) => ({
      nickname: state.nickname,
    }))
  );

  // 인증되지 않은 경우 메인 페이지로 리다이렉트
  useEffect(() => {
    if (!isLogin) {
      navigate("/login");
    }
  }, [isLogin, navigate]);

  // 게시글 생성 처리 함수
  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      await axios.post(`${PATH}/api/v1/board/create`, boardData, {
        withCredentials: true,
      });
      alert("게시글 생성이 완료되었습니다.");
      // 게시글 생성 후 게시글 전체 목록 페이지로 리다이렉트
      navigate("/community");
    } catch (error) {
      console.error("게시글 작성 중 오류 발생", error);
    }
  };

  return (
    <>
      <h1>
        <br />
        <br />
        <br />
        <br />
        <br />
      </h1>
      <h1>게시글 작성</h1>
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
};

export default CreateCommunity;
