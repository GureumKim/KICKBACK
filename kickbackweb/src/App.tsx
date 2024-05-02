import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Main from './routes/Main';
import Login from './routes/Login';
import SignUp from './routes/SignUp';
import Footer from './components/Nav/Footer';
import NaviBar from './components/Nav/NaviBar';
import RankSpeed from './routes/RankSpeed';

function App() {
  return (
    <BrowserRouter>
    <NaviBar />
      <Routes>
        <Route path='/' element={<Main />}></Route>
        <Route path='/login' element={<Login />}></Route>
        <Route path='/signup' element={<SignUp />}></Route>
        <Route path='/rank/speed' element={<RankSpeed />}></Route>
      </Routes>
      <Footer />
    </BrowserRouter>
  );
}

export default App;
