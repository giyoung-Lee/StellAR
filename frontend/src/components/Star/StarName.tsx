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

  const handleReset = () => {
    starStore.setStarClicked(false);
  };

  return (
    <>
      <n.Wrapper ref={starNameRef}>
        <div className="flex justify-between p-1">
          <span>별 이름: </span>
          <div onClick={handleReset}>
          <FontAwesomeIcon icon="xmark" />
          </div>
        </div>
        <span className=''>{starStore.starId}</span>
        {checkStarIdExists(starStore.starId) ? (
        <p>
          <FontAwesomeIcon icon={['fas', 'star']} />
          <n.BookMarkName>{checkStarIdExists(starStore.starId)}</n.BookMarkName>
        </p>
      ) : (
        <MarkBtn starName={starStore.starId} />
        )}
    </n.Wrapper>
    </>
  );
};

export default StarName;