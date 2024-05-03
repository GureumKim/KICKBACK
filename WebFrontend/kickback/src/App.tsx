import React from "react";

import { BrowserRouter, Route, Routes } from "react-router-dom";
import MainPage from "./components/main/MainPage";
import Login from "./components/member/Login";
import SignUp from "./components/member/SignUp";
import Board from "./components/community/Board";
import Notice from "./components/community/Notice";
import Qna from "./components/community/Qna";
import Navbar from "./components/common/Navbar";
import Start from "./components/main/Start";
import BoardDetail from "./components/community/BoardDetail";
import CreateBoard from "./components/community/CreateBoard";
import UpdateBoard from "./components/community/UpdateBoard";
import Profile from "./components/member/Profile";
import ChangePassword from "./components/member/ChangePassword";
import ChangeProfile from "./components/member/ChangeProfile";
import Rank from "./components/member/Rank";

function App() {
  return (
    <>
      <Navbar />
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Start />} />
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<SignUp />} />
          <Route path="/board" element={<Board />} />
          <Route path="/board/create" element={<CreateBoard />} />
          <Route path="/board/:id" element={<BoardDetail />} />
          <Route path="/update/:id" element={<UpdateBoard />} />
          <Route path="/notice" element={<Notice />} />
          <Route path="/qna" element={<Qna />} />
          <Route path="/mainpage" element={<MainPage />} />
          <Route path="/profile" element={<Profile />} />
          <Route path="/profile/password" element={<ChangePassword />} />
          <Route path="/profile/change" element={<ChangeProfile />} />
          <Route path="/rank" element={<Rank />} />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
