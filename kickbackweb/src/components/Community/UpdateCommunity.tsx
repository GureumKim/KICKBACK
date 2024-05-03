import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router";
import useUserStore from "../../stores/UserStore";
import { useShallow } from "zustand/react/shallow";
import useAuthStore from "../../stores/AuthStore";
import styles from "./../../styles/Community/Community.module.css";

interface Board {
  title: string;
  content: string;
}

function UpdateCommunity() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [boardData, setBoardData] = useState<Board>({
    title: "",
    content: "",
  });

  const { title, content } = boardData; // 비구조화 할당

  const onChange = (
    event: React.ChangeEvent<HTMLTextAreaElement | HTMLInputElement>
  ) => {
    const { value, name } = event.target;
    setBoardData({
      ...boardData,
      [name]: value,
    });
  };

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

  // 인증되지 않은 경우 메인 페이지로 리다이렉트합니다.
  useEffect(() => {
    if (!isLogin) {
      navigate("/login");
    } else {
      getBoard();
    }
  }, [isLogin, navigate]);

  const getBoard = async () => {
    const response = await axios.get(`${PATH}/api/v1/board/${id}`);
    setBoardData(response.data);
  };

  const updateBoard = async () => {
    await axios
      .put(`${PATH}/api/v1/board/${id}`, boardData, {
        withCredentials: true,
      })
      .then((res) => {
        alert("게시글이 수정되었습니다.");
        navigate(-1);
      });
  };

  return (
    <>
      {" "}
      <h1>
        <br />
        <br />
        <br />
        <br />
        <br />
      </h1>
      <div className={styles.container}>
        <h1 className={styles.boardTitle}>게시글 수정</h1>
        <br />
        <input
          className={styles.title_textarea}
          type="text"
          name="title"
          value={title}
          onChange={onChange}
        />
        <br />
        <br />
        <textarea
          className={styles.content_textarea}
          name="content"
          value={content}
          onChange={onChange}
        />
        <br />
        <br />
        <button className={styles.button_submit_right} onClick={updateBoard}>
          수정
        </button>
        <br />
        <br />
        <br />
        <br />
        <br />
        <br />
      </div>
    </>
  );
}

export default UpdateCommunity;
