const BASE_URL = "http://localhost:8080/api/v1";
const ACCOUNT = `${BASE_URL}/member`;
// 게시글 관련 기본 api, 단일 게시글 조회, 게시글 수정, 게시글 삭제에 같이 이용할 api
const BOARD = `${BASE_URL}/board`;
// 댓글 관련 기본 api,  댓글 생성, 삭제 및 수정에도 같이 이용함.
const COMMENT = `${BASE_URL}/comment`;

const API = {
  // 회원가입
  LOGIN: `${ACCOUNT}/login`,
  // 회원가입
  SIGNUP: `${ACCOUNT}/signup`,
  // 로그아웃
  LOGOUT: `${ACCOUNT}/logout`,
  // 회원정보 조회
  PROFILE: `${ACCOUNT}/get`,
  // 비밀번호 변경
  PASSWORD: `${ACCOUNT}/password/change`,
  // 프로필 변경
  CHANGE: `${ACCOUNT}/update`,
  // 회원탈퇴
  DELETE: `${ACCOUNT}/delete`,

  // 게시글 관련 api
  // 게시판 글 생성
  CREATE_BOARD: `${BOARD}/create`,
  // 전체 게시판 조회
  BOARD_ALL: `${BOARD}/all`,

  // 댓글 관련 api
  // 게시글별 댓글 조회
  COMMENT_INFO: `${COMMENT}/read`,

  // 이메일 q&a 보내기
  QNA: `${BASE_URL}/qna`,
};

export default API;
