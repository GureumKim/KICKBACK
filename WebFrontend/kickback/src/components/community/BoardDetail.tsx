import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import API from "../../config.js";
import useBearStore from "../state/state.js";

interface BoardData {
  id: number;
  title: string;
  content: string;
  nickname: string;
  createdDate: Date;
  updatedDate: Date;
}

interface CommentData {
  id: number;
  boardId: number;
  nickname: string;
  commentContent: string;
}

function BoardDetail() {
  const { id } = useParams();
  const [loading, setLoading] = useState(true);
  const [board, setBoard] = useState<BoardData | null>(null);
  const [comments, setComments] = useState<CommentData[]>([]);
  const [commentContent, setCommentContent] = useState("");
  const userNickname = useBearStore((state) => state.nickname);

  const navigate = useNavigate();
  // 수정 버튼 로직
  const moveToUpdate = () => {
    navigate("/update/" + id);
  };

  const token = localStorage.getItem("accessToken");
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };
  // 삭제 기능 구현
  const deleteBoard = async () => {
    if (window.confirm("게시글을 삭제하시겠습니까?")) {
      try {
        await axios.delete(`${API.BOARD}/${id}`, config);
        alert("삭제되었습니다.");
        navigate("/board");
      } catch (error) {
        console.log("게시글 삭제 중 오류 발생: ", error);
      }
    }
  };

  // 댓글 작성 관련
  const submitComment = async () => {
    try {
      const response = await axios.post(
        `${API.COMMENT}/${id}`,
        {
          boardId: id,
          nickname: userNickname, // useBearStore에서 가져온 사용자 닉네임
          commentContent: commentContent,
        },
        config
      ); // config에는 토큰을 포함한 헤더 정보가 포함되어 있음

      if (response.status === 200) {
        alert("댓글이 작성되었습니다.");
        setComments([...comments, response.data]); // 기존 댓글 목록에 새로운 댓글 추가
        setCommentContent(""); // 댓글 입력 폼 초기화
      }
    } catch (error) {
      console.error("댓글 작성 중 오류 발생: ", error);
    }
  };

  useEffect(() => {
    setLoading(true);
    axios
      .all([
        axios.get(`${API.BOARD}/${id}`),
        axios.get(`${API.COMMENT_INFO}/${id}`),
      ])
      .then(
        axios.spread((boardResponse, commentsResponse) => {
          setBoard(boardResponse.data);
          setComments(commentsResponse.data);
          setLoading(false);
        })
      )
      .catch((error) => {
        console.error("Error fetching data: ", error);
        setLoading(false);
      });
  }, [id]);

  if (loading) return <h2>loading...</h2>;

  return (
    <div>
      {board && (
        <div>
          <h1>글 상세 내용</h1>
          <h2>{board.title}</h2>
          <p>{board.content}</p>
          <p>{`작성자: ${board.nickname}`}</p>
          <p>{`작성 시간: ${board.createdDate}`}</p>
          <p>{`수정 시간: ${board.updatedDate}`}</p>
          {board.createdDate === board.updatedDate ? (
            <p>　</p>
          ) : (
            <p>수정된 게시글</p>
          )}
          {board.nickname === userNickname && (
            <button onClick={moveToUpdate}>수정</button>
          )}
          {board.nickname === userNickname && (
            <button onClick={deleteBoard}>삭제</button>
          )}
        </div>
      )}
      <br></br>
      <h3>댓글 작성</h3>
      <textarea
        name="commentContent"
        value={commentContent}
        onChange={(e) => setCommentContent(e.target.value)}
      />
      <button onClick={submitComment}>댓글 작성</button>
      <h3>댓글 목록</h3>
      <div>
        {comments.map((comment) => (
          <div key={comment.id}>
            <p>{`닉네임: ${comment.nickname}`}</p>
            <p>{`댓글: ${comment.commentContent}`}</p>
          </div>
        ))}
      </div>
    </div>
  );
}

export default BoardDetail;
