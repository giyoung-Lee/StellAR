import React, { useEffect, useRef } from 'react';
import useStarStore from '../../stores/starStore';
import * as n from '../style/StarNameStyle';

const StarName = () => {
  const starStore = useStarStore();
  const starNameRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (
        starNameRef.current &&
        !starNameRef.current.contains(event.target as Node)
      ) {
        starStore.setStarClicked(false);
      }
    };

    document.addEventListener('mousedown', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, [starStore]);

  return <n.Wrapper ref={starNameRef}>{starStore.starId}</n.Wrapper>;
};

export default StarName;
