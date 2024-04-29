import React, { useRef, useState } from 'react';
import * as s from '../style/StarInfoCarouselStyle';
import StarInfoImage from './StarInfoImage';
import StarInfoScience from './StarInfoScience';
import StarInfoStory from './StarInfoStory';

const StarInfoCarousel = ({ active }: { active: number }) => {
  const [activeSlide, setActiveSlide] = useState(active);
  const [dragStartX, setDragStartX] = useState(0);
  const [dragged, setDragged] = useState(false);
  const carouselRef = useRef<HTMLDivElement>(null);
  const carousel = ['image', 'science', 'story'];

  const handleTouchStart = (event: React.TouchEvent) => {
    setDragStartX(event.touches[0].clientX);
    setDragged(false);
  };

  const handleTouchMove = (event: React.TouchEvent) => {
    if (!carouselRef.current || dragged) return;
    const dragDistance = event.touches[0].clientX - dragStartX;
    if (Math.abs(dragDistance) > 50) {
      setDragged(true);
      if (dragDistance > 0) prev();
      else next();
    }
  };

  const handleTouchEnd = () => {
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
        transform: 'translateX(-110%) translateZ(-130px) scale(0.95)',
        zIndex: 9,
      };
    else if (activeSlide + 1 === index)
      return {
        opacity: 0.9,
        transform: 'translateX(110%) translateZ(-130px) scale(0.95)',
        zIndex: 9,
      };
    else if (activeSlide - 2 === index)
      return {
        opacity: 0,
        transform: 'translateX(-200%) translateZ(-330px) scale(0.95)',
      };
    else if (activeSlide + 2 === index)
      return {
        opacity: 0,
        transform: 'translateX(200%) translateZ(-330px) scale(0.95)',
      };
  };

  return (
    <s.Wrapper>
      <s.Carousel
        ref={carouselRef}
        onTouchStart={handleTouchStart}
        onTouchMove={handleTouchMove}
        onTouchEnd={handleTouchEnd}
      >
        {carousel.map((card, idx) => (
          <s.CarouselItem key={idx} style={{ ...getStyles(idx) }}>
            {card === 'image' ? (
              <StarInfoImage />
            ) : card === 'science' ? (
              <StarInfoScience />
            ) : (
              <StarInfoStory />
            )}
          </s.CarouselItem>
        ))}
      </s.Carousel>
    </s.Wrapper>
  );
};

export default StarInfoCarousel;

