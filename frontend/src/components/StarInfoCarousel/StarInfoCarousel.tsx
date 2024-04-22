import React, { useEffect, useRef, useState } from 'react';
import * as s from '../style/StarInfoCarouselStyle';
import StarInfoCarouselCard from './StarInfoCarouselCard';

type Props = {
  active: number;
};

const StarInfoCarousel = ({ active }: Props) => {
  const [activeSlide, setActiveSlide] = useState(active);
  const [dragStartX, setDragStartX] = useState(0);
  const [dragged, setDragged] = useState(false);
  const carouselRef = useRef<HTMLDivElement>(null);
  const carousel = ['image', 'science', 'story'];

  const handleMouseDown = (event: React.MouseEvent) => {
    setDragStartX(event.clientX);
    setDragged(false);
    event.preventDefault();
  };

  const handleMouseMove = (event: React.MouseEvent) => {
    if (!carouselRef.current || dragged) return;
    const dragDistance = event.clientX - dragStartX;
    console.log(dragDistance, dragStartX);
    if (Math.abs(dragDistance) > 50) {
      setDragged(true);
      if (dragDistance > 0) prev();
      else next();
    }
  };

  const handleMouseUp = () => {
    setDragStartX(0);
    setDragged(false);
  };

  const next = () => {
    activeSlide < 2 && setActiveSlide(activeSlide + 1);
  };
  const prev = () => {
    activeSlide > 0 && setActiveSlide(activeSlide - 1);
  };
  const getStyles = (index: number) => {
    if (activeSlide === index)
      return {
        transform: 'translateX(0px) translateZ(0px) rotateY(0deg)',
        zIndex: 10,
      };
    else if (activeSlide - 1 === index)
      return {
        opacity: 0.9,
        transform:
          'translateX(-200px) translateZ(-130px) rotateY(-55deg) scale(0.95)',
        zIndex: 9,
      };
    else if (activeSlide + 1 === index)
      return {
        opacity: 0.9,
        transform:
          'translateX(200px) translateZ(-130px) rotateY(55deg) scale(0.95)',
        zIndex: 9,
      };
    else if (activeSlide - 2 === index)
      return {
        opacity: 0,
        transform:
          'translateX(-300px) translateZ(-330px) rotateY(-55deg) scale(0.95)',
      };
    else if (activeSlide + 2 === index)
      return {
        opacity: 0,
        transform:
          'translateX(300px) translateZ(-330px) rotateY(55deg) scale(0.95)',
      };
  };

  return (
    <s.Wrapper>
      <s.BtnWrapper>
        <s.Btn onClick={prev}>이전</s.Btn>
        <s.Btn onClick={next}>다음</s.Btn>
      </s.BtnWrapper>
      <s.Carousel
        ref={carouselRef}
        onMouseDown={handleMouseDown}
        onMouseMove={handleMouseMove}
        onMouseUp={handleMouseUp}
      >
        {carousel.map((card, idx) => (
          <s.CarouselItem style={{ ...getStyles(idx) }}>
            <StarInfoCarouselCard />
          </s.CarouselItem>
        ))}
      </s.Carousel>
    </s.Wrapper>
  );
};

export default StarInfoCarousel;
