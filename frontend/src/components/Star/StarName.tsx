import React, { useEffect, useRef } from 'react';
import useStarStore from '../../stores/starStore';
import * as n from '../style/StarNameStyle';
import MarkBtn from '../StarMark/MarkBtn';

const StarName = () => {
  const starStore = useStarStore();
  const starNameRef = useRef<HTMLDivElement>(null);

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
      <MarkBtn starName={starStore.starId} />
    </n.Wrapper>
  );
};

export default StarName;
