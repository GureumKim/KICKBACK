import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Main from './routes/Main';
import Login from './routes/Login';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path='/' element={<Main />}></Route>
        <Route path='/login' element={<Login />}></Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
