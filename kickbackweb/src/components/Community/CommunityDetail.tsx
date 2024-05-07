import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router";
import useUserStore from "../../stores/UserStore";
import { useShallow } from "zustand/react/shallow";
import useAuthStore from "../../stores/AuthStore";

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

function CommunityDetail() {
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

  const navigate = useNavigate();
  const { id } = useParams();
  const [board, setBoard] = useState<BoardData | null>(null);
  const [comments, setComments] = useState<CommentData[]>([]);
  const [isEditing, setIsEditing] = useState(false);
  const [currentEditingId, setCurrentEditingId] = useState<number | null>(null);
  const [commentContent, setCommentContent] = useState(""); // 댓글 내용 상태
  const [editCommentContent, setEditCommentContent] = useState(""); // 수정할 댓글 내용 상태

  // 삭제 기능 구현
  const deleteBoard = async () => {
    if (window.confirm("게시글을 삭제하시겠습니까?")) {
      try {
        await axios.delete(`${PATH}/api/v1/board/${id}`, {
          withCredentials: true,
        });
        alert("게시글이 삭제되었습니다.");
        navigate("/community");
      } catch (error) {
        console.log("게시글 삭제 중 오류 발생: ", error);
      }
    }
  };

  // 댓글 작성 관련
  const submitComment = async () => {
    try {
      const response = await axios.post(
        `${PATH}/api/v1/comment/${id}`,
        {
          boardId: id,
          nickname: nickname,
          commentContent: commentContent,
        },
        { withCredentials: true }
      );

      if (response.status === 200) {
        alert("댓글이 작성되었습니다.");
        setComments([...comments, response.data]);
        setCommentContent("");
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
    await axios.put(
      `${PATH}/api/v1/comment/${commentId}`,
      { commentContent },
      { withCredentials: true }
    ); // 수정: 수정할 댓글 내용 전달
  };

  // 댓글 삭제 관련
  const deleteComment = async (commentId: number) => {
    if (window.confirm("댓글을 삭제하시겠습니까?")) {
      try {
        await axios.delete(`${PATH}/api/v1/comment/${commentId}`, {
          withCredentials: true,
        });
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
    axios
      .all([
        axios.get(`${PATH}/api/v1/board/${id}`),
        axios.get(`${PATH}/api/v1/comment/read/${id}`),
      ])
      .then(
        axios.spread((boardResponse, commentsResponse) => {
          setBoard(boardResponse.data);
          setComments(commentsResponse.data);
        })
      )
      .catch((error) => {
        console.error("Error fetching data: ", error);
      });
  }, [id]);

  return (
    <>
      {/* <h1>
        <br />
        <br />
        <br />
      </h1>
      <div className={styles.container}>
        {board && (
          <div>
            <h1 className={styles.boardTitle}>자유게시판</h1>
            <div className={styles.separator}></div>
            <h2>글 제목: {board.title}</h2>
            <div className={styles.separator}></div>
            <p>{board.content}</p>
            <br />
            <br />
            <p>{`작성자: ${board.nickname}`}</p>
            <p>{`작성 일자: ${new Date(board.createdDate).toLocaleDateString(
              "ko-KR"
            )}`}</p>
            <p>{`수정 일자: ${new Date(board.updatedDate).toLocaleDateString(
              "ko-KR"
            )}`}</p>
            {board.nickname === nickname && (
              <button
                className={styles.button_submit}
                onClick={() => navigate("/community/update/" + id)}
              >
                수정
              </button>
            )}
            　
            {board.nickname === nickname && (
              <button onClick={deleteBoard} className={styles.button_submit}>
                삭제
              </button>
            )}
          </div>
        )}
        <br></br>
        <div className={styles.separator}></div>
        <h2>댓글 작성</h2>
        <textarea
          className={styles.comment_textarea}
          name="commentContent"
          value={commentContent}
          onChange={(e) => setCommentContent(e.target.value)}
        />
        <br />
        <button className={styles.button_submit} onClick={submitComment}>
          댓글 작성
        </button>
        <div className={styles.separator}></div>
        <h2>댓글 목록</h2>
        <br />
        <div>
          {comments.map((comment) => (
            <div key={comment.id}>
              <p>{`닉네임: ${comment.nickname}`}</p>
              {isEditing && currentEditingId === comment.id ? (
                <>
                  <textarea
                    className={styles.comment_textarea}
                    value={editCommentContent}
                    onChange={(e) => setEditCommentContent(e.target.value)} // 수정: editCommentContent 상태로 변경
                  />
                  <br />
                  <button
                    className={styles.button_submit}
                    onClick={applyEditComment}
                  >
                    수정 적용
                  </button>
                </>
              ) : (
                <>
                  <p>{`댓글: ${comment.commentContent}`}</p>
                  {comment.nickname === nickname && (
                    <button
                      className={styles.button_submit}
                      onClick={() => startEditComment(comment)}
                    >
                      댓글 수정
                    </button>
                  )}
                </>
              )}
              　
              {comment.nickname === nickname && (
                <button
                  className={styles.button_submit}
                  onClick={() => deleteComment(comment.id)}
                >
                  댓글 삭제
                </button>
              )}
              <div className={styles.separator}></div>
            </div>
          ))}
          <br />
          <br />
          <br />
          <br />
        </div>
      </div> */}
    </>
  );
}

export default CommunityDetail;
