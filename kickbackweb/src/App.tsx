import React from "react";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Main from "./routes/Main";
import Login from "./routes/Login";
import SignUp from "./routes/SignUp";
import Footer from "./components/Nav/Footer";
import NaviBar from "./components/Nav/NaviBar";
import RankSpeed from "./routes/RankSpeed";
import Community from "./routes/Community";
import CreateCommunity from "./components/Community/CreateCommunity";
import CommunityDetail from "./components/Community/CommunityDetail";
import UpdateCommunity from "./components/Community/UpdateCommunity";
import GameIntro from "./routes/GameIntro";
import ModeIntro from "./routes/ModeIntro";
import ControlIntro from "./routes/ControlIntro";
import MyPage from "./routes/MyPage";

function App() {
  return (
    <BrowserRouter>
      <NaviBar />
      <Routes>
        <Route path="/" element={<Main />}></Route>
        <Route path="/login" element={<Login />}></Route>
        <Route path="/signup" element={<SignUp />}></Route>
        <Route path="/community/:type" element={<Community />} />
        <Route path="/community/Article/:id" element={<CommunityDetail />}></Route>
        <Route
          path="/community/update/:id"
          element={<UpdateCommunity />}
        ></Route>
        <Route path="/community/create" element={<CreateCommunity />}></Route>
        <Route path="/rank/speed" element={<RankSpeed />}></Route>
        <Route path="/intro/game" element={<GameIntro />}></Route>
        <Route path="/intro/mode" element={<ModeIntro />}></Route>
        <Route path="/intro/control" element={<ControlIntro />}></Route>
        <Route path="/mypage/:nickname" element={<MyPage />}></Route>
      </Routes>
      <Footer />
    </BrowserRouter>
  );
}

export default App;
