import axios from "axios";
import { useEffect, useState } from "react";
import { useShallow } from "zustand/react/shallow";
import useUserStore from "../../../stores/UserStore";
import useAuthStore from "../../../stores/AuthStore";
import * as s from "../../../styles/Community/Article/Articles";
import Create from "./Create";
import Delete from "./Delete";

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

  const { nickname } = useUserStore(
    useShallow((state) => ({
      nickname: state.nickname,
    }))
  );

  const [board, setBoard] = useState<Articles[]>([]);
  const [create, setCreate] = useState<Boolean>(false);
  const [background, setBackground] = useState<Boolean>(false);

  const getBoard = async () => {
    const response = (await axios.get(`${PATH}/api/v1/board/all`)).data;
    setBoard(response.reverse());
  };

  const createArticle = () => {
    setCreate(!create);
    setBackground(!background);
  };

  const [del, setDel] = useState<Boolean>(false);

  const deleteArticle = () => {
    setDel(!del);
  };

  useEffect(() => {
    getBoard();
    console.log(board);
  }, []);

  return (
    <>
      <s.Wrapper>
        <s.Container>
          {isLogin ? (
            <s.Article className="create" onClick={() => createArticle()}>
              +
            </s.Article>
          ) : null}
          {board.map((board) =>
            board.nickname === nickname ? (
              <s.Article key={board.id} className="mine">
                <div className="title">
                  {board.nickname}: {board.title}
                </div>
                <div className="content">{board.content}</div>
                <s.BottomBox>
                  {del ? (
                      <Delete id={board.id} deleteArticle={deleteArticle} />
                  ) : null}
                    <s.Delete onClick={() => deleteArticle()}>🗑</s.Delete>
                </s.BottomBox>
              </s.Article>
            ) : (
              <s.Article key={board.id}>
                <div className="title">
                  {board.nickname}: {board.title}
                </div>
                <div className="content">{board.content}</div>
              </s.Article>
            )
          )}
        </s.Container>
        {create ? <Create createArticle={createArticle} /> : null}
      </s.Wrapper>
    </>
  );
}

export default Articles;
