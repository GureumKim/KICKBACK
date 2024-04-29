import React from 'react'
import ModeCom from '../components/Main/ModeCom'
import Carousel from '../components/Main/Carousel'
import Notice from '../components/Main/Notice'
import NaviBar from '../components/Nav/NaviBar'
import Footer from '../components/Nav/Footer'

const Main = () => {
  return (
    <>
      <NaviBar />
      <Carousel />
      <Notice />
      <ModeCom />
      <Footer />
    </>
  )
}

export default Main