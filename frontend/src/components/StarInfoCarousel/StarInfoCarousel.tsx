import React, { useEffect, useRef, useState } from 'react';
import * as s from '../style/StarInfoCarouselStyle';
import StarInfoImage from './StarInfoImage';
import StarInfoScience from './StarInfoScience';
import StarInfoStory from './StarInfoStory';
import useConstellationStore from '../../stores/constellationStore';
import { useQuery } from '@tanstack/react-query';
import Loading from '../common/Loading/Loading';
import { GetConstellationDetail } from '../../apis/StarApis';

import prevArrow from '/img/prev.png';

const StarInfoCarousel = ({ active }: { active: number }) => {
  const [activeSlide, setActiveSlide] = useState(active);
  const [dragStartX, setDragStartX] = useState(0);
  const [dragged, setDragged] = useState(false);
  const carouselRef = useRef<HTMLDivElement>(null);
  const Ref = useRef<HTMLDivElement>(null);
  const carousel = ['image', 'science', 'story'];

  const [isModalOpen, setModalOpen] = useState(false);

  const constellationStore = useConstellationStore();

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

  useEffect(() => {
    const handleClick = (e: MouseEvent) => {
      if (Ref.current && !Ref.current.contains(e.target as Node)) {
        if (!isModalOpen) {
          constellationStore.setConstellationClicked(false);
        }
      }
    };
    window.addEventListener('mousedown', handleClick);
    return () => window.removeEventListener('mousedown', handleClick);
  }, [isModalOpen, Ref]);

  const { isLoading: isConstellationLoading, data: constellationData } =
    useQuery({
      queryKey: ['get-constellation-detail'],
      queryFn: () =>
        GetConstellationDetail(constellationStore.constellationName),
    });

  if (isConstellationLoading) {
    return <Loading />;
  }

  return (
    <s.Wrapper ref={Ref}>
      <img
        className="prev arrow"
        src={prevArrow}
        alt="prev_button"
        onClick={prev}
      />
      <img
        className="next arrow"
        src={prevArrow}
        alt="next_button"
        onClick={next}
      />
      <s.Carousel
        ref={carouselRef}
        onTouchStart={handleTouchStart}
        onTouchMove={handleTouchMove}
        onTouchEnd={handleTouchEnd}
      >
        {carousel.map((card, idx) => (
          <s.CarouselItem key={idx} style={{ ...getStyles(idx) }}>
            {card === 'image' ? (
              <StarInfoImage
                constellationImg={constellationData?.data?.constellationImg}
              />
            ) : card === 'science' ? (
              <StarInfoScience constellationData={constellationData?.data} />
            ) : (
              <StarInfoStory
                constellationData={constellationData?.data}
                setModalOpen={setModalOpen}
              />
            )}
          </s.CarouselItem>
        ))}
      </s.Carousel>
    </s.Wrapper>
  );
};

export default StarInfoCarousel;

