import axios from "axios";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { Board as BoardData } from "./Board";

interface BoardData {
  id: number;
  title: string;
  content: string;
  nickname: string;
  createdDate: Date;
  updatedDate: Date;
}

function BoardDetail() {
  const { id } = useParams();
  const [loading, setLoading] = useState(true);
  const [board, setBoard] = useState<BoardData>([]);
  const getBoard = async () => {
    try {
      const response = await (
        await axios.get(`http://localhost:8080/api/v1/board/${id}`)
      ).data;
      setBoard(response);
      setLoading(false);
      console.log("response", response);
    } catch (error) {
      console.error("catch error", error);
      setLoading(false);
    }
  };

  useEffect(() => {
    getBoard();
  }, [id]);
  return (
    <>
      {loading ? (
        <h2>loading...</h2>
      ) : (
        board && (
          <div>
            <h1>{board.title}</h1>
            <p>{board.content}</p>
            <p>{`작성자: ${board.nickname}`}</p>
            <p>{`작성 시간: ${board.createdDate}`}</p>
            <p>{`수정 시간: ${board.updatedDate}`}</p>
            {/* 수정된 게시물의 경우 '수정된 게시물' 표시됨. */}
            {board.createdDate == board.updatedDate ? (
              <p>수정 안 된 게시글</p>
            ) : (
              <p>수정된 게시글</p>
            )}
          </div>
        )
      )}
    </>
  );
}
export default BoardDetail;
