import React, { useEffect, useRef, useState } from 'react';
import useStarStore from '../../stores/starStore';
import * as n from '../style/StarNameStyle';
import MarkBtn from '../StarMark/MarkBtn';
import '../../pages/style/Fontawsome';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { useNavigate } from 'react-router-dom';

const StarName = () => {
  const starStore = useStarStore();
  const starNameRef = useRef<HTMLDivElement>(null);

  const checkStarIdExists = (starId: string) => {
    const star = starStore.markedStars.find((star) => star.starId === starId);
    return star ? star.bookmarkName : null;
  };

  useEffect(() => {
    const handleClickOutside = (event: MouseEvent | TouchEvent) => {
      event.stopPropagation();
      if (
        starNameRef.current &&
        !starNameRef.current.contains(event.target as Node)
      ) {
        starStore.setStarClicked(false);
      }
    };

    document.addEventListener('mousedown', handleClickOutside);
    document.addEventListener('mousemove', handleClickOutside);
    document.addEventListener('touchmove', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
      document.removeEventListener('mousemove', handleClickOutside);
      document.removeEventListener('touchmove', handleClickOutside);
    };
  }, [starStore]);

  return (
    <n.Wrapper ref={starNameRef}>
      {starStore.starId}
      {checkStarIdExists(starStore.starId) ? (
        <p>
          <FontAwesomeIcon icon={['fas', 'star']} />
          <n.BookMarkName>{checkStarIdExists(starStore.starId)}</n.BookMarkName>
        </p>
      ) : (
        <MarkBtn starName={starStore.starId} />
      )}
    </n.Wrapper>
  );
};

export default StarName;
