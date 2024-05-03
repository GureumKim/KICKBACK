import axios from "axios";
import React, { useEffect, useState } from "react";
import { useShallow } from "zustand/react/shallow";
import useAuthStore from "../../stores/AuthStore";
import { useNavigate, Link } from "react-router-dom";
import styles from "./../../styles/Community/Community.module.css";

interface Board {
  id: number;
  title: string;
  content: string;
  nickname: string;
  createdDate: string;
  updatedDate: string;
}

const Board = () => {
  const { PATH, isLogin } = useAuthStore(
    useShallow((state) => ({
      PATH: state.PATH,
      isLogin: state.isLogin,
    }))
  );

  const navigate = useNavigate();

  const [board, setBoard] = useState<Board[]>([]);

  const getBoard = async () => {
    const response = (await axios.get(`${PATH}/api/v1/board/all`)).data;
    setBoard(response.reverse());
  };

  useEffect(() => {
    getBoard();
  }, []);

  return (
    <>
      <h1>
        <br />
        <br />
        <br />
        <br />
      </h1>
      <div className={styles.container}>
        <h1 className={styles.boardTitle}>게시글 목록</h1>
        {isLogin ? (
          <button
            className={styles.boardButton}
            onClick={() => navigate("/community/create")}
          >
            게시글 작성
          </button>
        ) : null}
        <br />
        <br />
        <br />
        <br />
        <table>
          <thead>
            <tr>
              <th className="id">ID</th>
              <th className="title">제목</th>
              <th className="nickname">작성자</th>
            </tr>
          </thead>
          <tbody>
            {board.map((board) => (
              <tr key={board.id} className={styles.boardRow}>
                <td className="id">{board.id}</td>
                <td className="title">
                  <Link className={styles.link} to={`/community/${board.id}`}>
                    {board.title}
                  </Link>
                </td>
                <td className="nickname">{board.nickname}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      <br />
      <br />
    </>
  );
};

export default Board;
