import React, { useEffect, useState } from "react";

import axios from "axios";

function Board() {
  type Board = {
    id: number;
    title: string;
    content: string;
    nickname: string;
    createdDate: Date;
    updatedDate: Date;
  };

  const [boardList, setBoardList] = useState<Board[]>([]);

  const getBoardList = async () => {
    const response = (await axios.get("http://localhost:8080/api/v1/board/all"))
      .data;
    setBoardList(response);
  };

  useEffect(() => {
    getBoardList();
  }, []);

  return (
    <>
      <h1>게시판</h1>
      {boardList.map((Board) => (
        <div key={Board.id}>
          {Board.nickname}
          <br />
          {Board.title}
          <br />
          {Board.content}
          <br />
          {Board.createdDate}
          <br />
          {Board.updatedDate}
          <br />
          <br />
        </div>
      ))}
    </>
  );
}

export default Board;
