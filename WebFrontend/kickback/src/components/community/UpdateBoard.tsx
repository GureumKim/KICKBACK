import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import API from "../../config.js";
import useBearStore from "../state/state";

interface Board {
  title: string;
  content: string;
}

function UpdateBoard() {
  // 로그인 상태를 받아오기 위한 로직들
  const token = localStorage.getItem("accessToken");
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };
  const { isAuthenticated } = useBearStore();

  const navigate = useNavigate();
  const { id } = useParams();
  const [boardData, setBoardData] = useState<Board>({
    title: "",
    content: "",
  });

  const { title, content } = boardData; // 비구조화 할당

  const onChange = (event) => {
    const { value, name } = event.target;
    setBoardData({
      ...boardData,
      [name]: value,
    });
  };

  // 인증되지 않은 경우 메인 페이지로 리다이렉트합니다.
  useEffect(() => {
    if (!isAuthenticated) {
      navigate("/login");
    } else {
      getBoard();
    }
  }, [isAuthenticated, navigate]);

  const getBoard = async () => {
    const response = await (await axios.get(`${API.BOARD}/${id}`)).data;
    setBoardData(response);
  };

  const updateBoard = async () => {
    await axios.put(`${API.BOARD}/${id}`, boardData, config).then((res) => {
      alert("수정되었습니다.");
      navigate(-1);
    });
  };

  return (
    <>
      <h1>게시글 수정</h1>
      <h2>제목</h2>
      <input type="text" name="title" value={title} onChange={onChange} />
      <br />
      <h2>내용</h2>
      <textarea name="content" value={content} onChange={onChange} />

      <button onClick={updateBoard}>수정</button>
    </>
  );
}

export default UpdateBoard;
