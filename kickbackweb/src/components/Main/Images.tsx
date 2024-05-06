import { useState, useEffect } from "react";
import * as s from "../../styles/Main/Images";
import caroueslImg1 from "../../assets/intro3.png";
import caroueslImg2 from "../../assets/intro4.png";
import caroueslImg3 from "../../assets/intro5.png";
import caroueslImg4 from "../../assets/intro6.png";
import downloadBtn from "../../assets/downloadBtn.png";

interface Props {
  handleDownload: () => void;
}

function Images({ handleDownload }: Props) {
  const [currentSlide, setCurrentSlide] = useState(0);
  const [prevSlide, setPrevSlide] = useState<number | null>(null);
  const images = [caroueslImg1, caroueslImg2, caroueslImg3, caroueslImg4];

  useEffect(() => {
    const interval = setInterval(() => {
      setPrevSlide(currentSlide);
      goToNextSlide();
    }, 5000);

    return () => clearInterval(interval);
  }, [currentSlide]);

  const goToNextSlide = () => {
    setCurrentSlide((prevSlide) => (prevSlide + 1) % images.length);
  };

  const goToPreviousSlide = () => {
    setCurrentSlide((prevSlide) =>
      prevSlide === 0 ? images.length - 1 : prevSlide - 1
    );
  };

  return (
    <>
      <s.CarouselContainer>
        <s.SlideContainer>
          {images.map((image, index) => {
            return (
              <s.Slide
                key={index}
                style={{
                  opacity:
                    index === currentSlide ? 1 : index === prevSlide ? 0.7 : 0,
                  zIndex: index === currentSlide ? 2 : 1, // 현재 슬라이드를 최상위로 배치
                }}
              >
                <s.Image src={image} alt={`Slide ${index}`} />
              </s.Slide>
            );
          })}
        </s.SlideContainer>
      </s.CarouselContainer>
      <s.CadBox>
        <s.InBox>
          <s.LeftArrow onClick={goToPreviousSlide} className="item">
            &lt;
          </s.LeftArrow>
          <div className="item"></div>
          <div className="item">
            <img
              src={downloadBtn}
              alt="다운로드 버튼"
              onClick={handleDownload}
            />
          </div>
          <div className="item"></div>
          <s.RightArrow onClick={goToNextSlide} className="item">
            &gt;
          </s.RightArrow>
        </s.InBox>
      </s.CadBox>
    </>
  );
}

export default Images;
