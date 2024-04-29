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
  const [isEditing, setIsEditing] = useState(false);
  const [currentEditingId, setCurrentEditingId] = useState<number | null>(null);
  const [commentContent, setCommentContent] = useState(""); // 추가: 댓글 내용 상태
  const [editCommentContent, setEditCommentContent] = useState(""); // 추가: 수정할 댓글 내용 상태
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
          nickname: userNickname,
          commentContent: commentContent, // 수정: commentContent 상태 사용
        },
        config
      );

      if (response.status === 200) {
        alert("댓글이 작성되었습니다.");
        setComments([...comments, response.data]);
        setCommentContent(""); // 작성 후 초기화
      }
    } catch (error) {
      console.error("댓글 작성 중 오류 발생: ", error);
    }
  };

  // 댓글 수정 관련
  const startEditComment = (comment: CommentData) => {
    setIsEditing(true);
    setCurrentEditingId(comment.id);
    setEditCommentContent(comment.commentContent);
  };

  const applyEditComment = async () => {
    if (currentEditingId && editCommentContent.trim() !== "") {
      try {
        const response = await modifyComment(
          currentEditingId.toString(),
          editCommentContent
        );
        alert("댓글이 수정되었습니다.");

        // 댓글 배열 업데이트
        const updatedComments = comments.map((comment) => {
          if (comment.id === currentEditingId) {
            // 현재 수정 중인 댓글을 찾으면, 수정된 내용으로 업데이트
            return { ...comment, commentContent: editCommentContent };
          }
          return comment; // 다른 댓글은 그대로 유지
        });

        setComments(updatedComments); // 업데이트된 댓글 배열로 상태 업데이트

        // 수정 상태 초기화
        setIsEditing(false);
        setCurrentEditingId(null);
        setEditCommentContent("");
      } catch (error) {
        console.error("댓글 수정 중 오류 발생: ", error);
      }
    }
  };

  const modifyComment = async (commentId: string, commentContent: string) => {
    await axios.put(`${API.COMMENT}/${commentId}`, { commentContent }, config); // 수정: 수정할 댓글 내용 전달
  };

  // 댓글 삭제 관련
  const deleteComment = async (commentId: string) => {
    if (window.confirm("댓글을 삭제하시겠습니까?")) {
      try {
        await axios.delete(`${API.COMMENT}/${commentId}`, config);
        alert("댓글이 삭제되었습니다.");
        // 삭제된 댓글을 제외한 새로운 댓글 배열 생성
        const updatedComments = comments.filter(
          (comment) => comment.id !== commentId
        );
        setComments(updatedComments); // 새로운 댓글 배열로 상태 업데이트
      } catch (error) {
        console.error("댓글 삭제 중 오류 발생: ", error);
      }
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
            {isEditing && currentEditingId === comment.id ? (
              <>
                <textarea
                  value={editCommentContent}
                  onChange={(e) => setEditCommentContent(e.target.value)} // 수정: editCommentContent 상태로 변경
                />
                <button onClick={applyEditComment}>수정 적용</button>
              </>
            ) : (
              <>
                <p>{`댓글: ${comment.commentContent}`}</p>
                {comment.nickname === userNickname && (
                  <button onClick={() => startEditComment(comment)}>
                    댓글 수정
                  </button>
                )}
              </>
            )}
            {comment.nickname === userNickname && (
              <button onClick={() => deleteComment(comment.id)}>
                댓글 삭제
              </button>
            )}
          </div>
        ))}
      </div>
    </div>
  );
}

export default BoardDetail;
