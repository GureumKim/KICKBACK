import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Main from './routes/Main';
import NaviBar from './components/Nav/NaviBar';
import Footer from './components/Nav/Footer';
import Login from './routes/Login';

function App() {
  return (
    <BrowserRouter>
    <NaviBar />
      <Routes>
        <Route path='/' element={<Main />}></Route>
        <Route path='/login' element={<Login />}></Route>
      </Routes>
      <Footer />
    </BrowserRouter>
  );
}

export default App;
