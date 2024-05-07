import axios from "axios";
import { useEffect, useState } from "react";
import { useShallow } from "zustand/react/shallow";
import useAuthStore from "../../../stores/AuthStore";
import { useNavigate, Link } from "react-router-dom";
import * as s from "../../../styles/Community/Article/Articles";

interface Articles {
  id: number;
  title: string;
  content: string;
  nickname: string;
  createdDate: string;
  updatedDate: string;
}

function Articles() {
  const { PATH, isLogin } = useAuthStore(
    useShallow((state) => ({
      PATH: state.PATH,
      isLogin: state.isLogin,
    }))
  );

  const navigate = useNavigate();

  const [board, setBoard] = useState<Articles[]>([]);

  const getBoard = async () => {
    const response = (await axios.get(`${PATH}/api/v1/board/all`)).data;
    setBoard(response.reverse());
  };

  useEffect(() => {
    getBoard();
    console.log(board);
  }, []);

  return (
    <>
      {/* {isLogin ? (
        <s onClick={() => navigate("/community/create")}>게시글 작성</s>
      ) : null} */}
      <s.Wrapper>
        <s.Container>
          <s.Article className="index">
            <div className="id">ID</div>
            <div className="title">제목</div>
            <div className="nickname">작성자</div>
          </s.Article>
          {board.map((board) => (
            <s.Article key={board.id}>
              <div className="id">{board.id}</div>
              <div className="title">
                <Link to={`/community/Article/${board.id}`}>{board.title}</Link>
              </div>
              <div className="nickname">{board.nickname}</div>
            </s.Article>
          ))}
        </s.Container>
      </s.Wrapper>
    </>
  );
}

export default Articles;
