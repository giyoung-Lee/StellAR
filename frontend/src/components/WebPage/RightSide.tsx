import React from 'react';
import MainCanvas from '../Star/MainCanvas';
import * as r from '../style/WebPageStyle';

const RightSide = () => {
  return (
    <r.RightWrapper>
      <r.CanvasContainer>
        <MainCanvas />
      </r.CanvasContainer>
    </r.RightWrapper>
  );
};

export default RightSide;

