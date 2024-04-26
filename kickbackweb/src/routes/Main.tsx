import React from 'react'
import ModeCom from '../components/Main/ModeCom'
import Carousel from '../components/Main/Carousel'
import Notice from '../components/Main/Notice'

const Main = () => {
  return (
    <>
      <Carousel />
      <Notice />
      <ModeCom />
    </>
  )
}

export default Main