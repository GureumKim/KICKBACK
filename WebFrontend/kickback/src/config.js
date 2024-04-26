const BASE_URL = "http://localhost:8080/api/v1";

const API = {
  // 계정 관련 api
  // 회원가입
  LOGIN: `${BASE_URL}/member/login`,
  // 회원가입
  SIGNUP: `${BASE_URL}/member/signup`,

  // 게시글 관련 api
  // 게시판 글 생성
  CREATE_BOARD: `${BASE_URL}/board/create`,
  // 전체 게시판 조회
  BOARD_ALL: `${BASE_URL}/board/all`,
  // 단일 게시글 조회, 게시글 수정, 게시글 삭제에 같이 이용할 api
  BOARD: `${BASE_URL}/board`,

  // 댓글 관련 api
  // 댓글 생성, 삭제 수정에 같이 이용할 api 주소
  COMMENT: `${BASE_URL}/comment`,
  // 게시글별 댓글 조회
  COMMENT_INFO: `${BASE_URL}/comment/read`,
};

export default API;
