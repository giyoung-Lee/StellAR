import React from 'react';
import * as s from '../style/StarInfoCarouselStyle';
import Tau from '/img/taurus.webp';

//https://www.mediastorehouse.com/granger-art-on-demand/zodiac-taurus-fresco-1575-villa-farnese-6207397.html

const StarInfoImage = () => {
  return (
    <s.CardWrapper>
      <s.CardTitle>황소자리</s.CardTitle>
      <s.CardImage src={Tau} alt="mythology_image" />
    </s.CardWrapper>
  );
};

export default StarInfoImage;

