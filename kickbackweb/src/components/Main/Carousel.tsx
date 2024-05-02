import React, { useState, useEffect } from 'react';
import { CarouselContainer, SlideContainer, Slide, Image, IndicatorContainer, LeftArrow, RightArrow } from '../../styles/Main/MainCarousel';
import caroueslImg1 from "../../assets/carousel1.png"
import caroueslImg2 from "../../assets/carousel2.png"
import caroueslImg3 from "../../assets/carousel3.png"
import downloadBtn from "../../assets/downloadBtn.png"
import { ref, getDownloadURL } from "firebase/storage";
import { storage } from "../../config/Firebase.js";

const Carousel = () => {

  const [error, setError] = useState<string | null>(null);
  
  const handleDownload = () => {
    const fileRef = ref(storage, "토키도키.mp3");

    getDownloadURL(fileRef)
      .then((url) => {
        fetch(url)
          .then(response => response.blob())
          .then(blob => {
            const downloadUrl = window.URL.createObjectURL(blob);
            const link = document.createElement('a');
            link.href = downloadUrl;
            link.download = "토키도키.mp3";
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
            window.URL.revokeObjectURL(downloadUrl);
          });
      })
      .catch((error) => {
        console.error('Error downloading the file:', error);
      });
  };

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
          transition: "opacity 1.5s ease-in-out"
        }}
      >
        {images.map((image, index) => (
          <Slide key={index} style={{
            opacity: index === currentSlide ? 1 : 0.7,
            transition: "opacity 1.5s ease-in-out"
          }}>
            <Image src={image} alt={`Slide ${index}`} />
          </Slide>
        ))}
      </SlideContainer>
      <RightArrow onClick={goToNextSlide}>&gt;</RightArrow>
      <IndicatorContainer>
        <img src={downloadBtn} alt="게임 다운로드" style={{ cursor: "pointer" }} onClick={handleDownload} />
      </IndicatorContainer>
    </CarouselContainer>
  );
}

export default Carousel;
