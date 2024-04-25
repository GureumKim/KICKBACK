import React from "react";

import { BrowserRouter, Route, Routes } from "react-router-dom";
import MainPage from "./components/main/MainPage";
import Login from "./components/member/Login";
import SignUp from "./components/member/SignUp";
import Board from "./components/community/Board";
import Notice from "./components/community/Notice";
import Qna from "./components/community/Qna";
import Navbar from "./components/common/Navbar";
import useBearStore from "./components/state/state";

function App() {
  const isUserValid = useBearStore((state) => state.isUserValid);
  return (
    <>
      <Navbar />
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<MainPage />} />
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<SignUp />} />
          <Route path="/board" element={<Board />} />
          <Route path="/notice" element={<Notice />} />
          <Route path="/qna" element={<Qna />} />
          <Route path="/*" element={<MainPage />} />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
