import React, { useEffect, useRef } from 'react';
import useStarStore from '../../stores/starStore';
import * as n from '../style/StarNameStyle';
import MarkBtn from '../StarMark/MarkBtn';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import '../../pages/style/Fontawsome';

const StarName = () => {
  const starStore = useStarStore();
  const starNameRef = useRef<HTMLDivElement>(null);

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
        <MarkBtn starName={starStore.starId} />
      </n.Wrapper>
    </>
  );
};

export default StarName;
