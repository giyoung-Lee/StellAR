import React from 'react';
import useStarStore from '../../stores/starStore';
import * as n from '../style/StarNameStyle';

const StarName = () => {
  const starStore = useStarStore();
  return <n.Wrapper>{starStore.starId}</n.Wrapper>;
};

export default StarName;
