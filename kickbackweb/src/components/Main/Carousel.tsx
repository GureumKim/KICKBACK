import React, { useState, useEffect } from 'react';
import { CarouselContainer, SlideContainer, Slide, Image, IndicatorContainer, Indicator, LeftArrow, RightArrow } from '../../styles/Main/MainCarousel';
import caroueslImg1 from "../../assets/carousel1.png"
import caroueslImg2 from "../../assets/carousel2.png"
import caroueslImg3 from "../../assets/carousel3.png"

const Carousel = () => {
  const [currentSlide, setCurrentSlide] = useState(0);
  const images = [
    caroueslImg1,
    caroueslImg2,
    caroueslImg3
  ]

  useEffect(() => {
    const interval = setInterval(() => {
      goToNextSlide();
    }, 3000);

    return () => clearInterval(interval);
  }, [currentSlide]);

  const goToNextSlide = () => {
    setCurrentSlide((prevSlide) => (prevSlide + 1) % images.length);
  };

  const goToPreviousSlide = () => {
    setCurrentSlide((prevSlide) => (prevSlide === 0 ? images.length - 1 : prevSlide - 1));
  };

  return (
    <CarouselContainer>
      <LeftArrow onClick={goToPreviousSlide}>&lt;</LeftArrow>
      <SlideContainer
        style={{
          transform: `translateX(-${currentSlide * 100}%)`,
          transition: "transform 1s ease"
        }}
      >
        {images.map((image, index) => (
          <Slide key={index}>
            <Image src={image} alt={`Slide ${index}`} />
          </Slide>
        ))}
      </SlideContainer>
      <RightArrow onClick={goToNextSlide}>&gt;</RightArrow>
      <IndicatorContainer>
        {images.map((_, index) => (
          <Indicator key={index} onClick={() => setCurrentSlide(index)} style={{ backgroundColor: index === currentSlide ? 'white' : 'darkgray' }} />
        ))}
      </IndicatorContainer>
    </CarouselContainer>
  );
}

export default Carousel;
