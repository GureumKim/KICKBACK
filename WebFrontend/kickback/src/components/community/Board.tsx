import React, { useEffect, useState } from "react";
import API from "../../config.js";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";
import useBearStore from "../state/state.js";

function Board() {
  type Board = {
    id: number;
    title: string;
    content: string;
    nickname: string;
    createdDate: Date;
    updatedDate: Date;
  };

  const navigate = useNavigate();

  const [boardList, setBoardList] = useState<Board[]>([]);

  const getBoardList = async () => {
    const response = (await axios.get(`${API.BOARD_ALL}`)).data;
    setBoardList(response);
  };

  // 로그인 여부 확인을 위해 토큰 받아오기
  const accessToken = localStorage.getItem("accessToken");

  const logout = useBearStore((state) => state.logout);

  // 로그아웃 로직
  const onSubmitHandler = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    try {
      // 로그아웃 요청
      const response = await axios.post(
        `${API.LOGOUT}`,
        {},
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        }
      );

      if (response.status === 200) {
        // zustand에 업데이트 하기
        logout();
        navigate("/login");
      } else {
        alert("로그아웃에 실패했습니다.");
      }
    } catch (error) {
      console.error("로그아웃 요청 중 오류 발생", error);
      alert("로그아웃 처리 중 오류가 발생했습니다.");
    }
  };

  useEffect(() => {
    getBoardList();
  }, []);

  const createBoard = () => {
    navigate("/board/create");
  };

  return (
    <>
      {accessToken ? (
        <button onClick={onSubmitHandler}>로그아웃</button>
      ) : (
        <p>로그인이 필요합니다.</p>
      )}
      <h1>게시판</h1>
      {accessToken ? (
        <button onClick={createBoard}>게시글 작성</button>
      ) : (
        <p>로그인이 필요합니다.</p>
      )}

      {boardList.map((Board) => (
        <div key={Board.id}>
          <li key={Board.id}>
            <Link to={`/board/${Board.id}`}>{Board.title}</Link>
          </li>
          <br />
        </div>
      ))}
    </>
  );
}

export default Board;
