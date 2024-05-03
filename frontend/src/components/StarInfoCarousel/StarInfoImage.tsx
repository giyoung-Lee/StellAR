import React from 'react';
import * as s from '../style/StarInfoCarouselStyle';
import Tau from '/img/taurus.webp';

//https://www.mediastorehouse.com/granger-art-on-demand/zodiac-taurus-fresco-1575-villa-farnese-6207397.html

type Props = {
  constellationName: string;
};

const StarInfoImage = ({ constellationName }: Props) => {
  return (
    <s.CardWrapper>
      <s.CardTitle>{constellationName}</s.CardTitle>
      <s.CardImage src={Tau} alt="mythology_image" />
    </s.CardWrapper>
  );
};

export default StarInfoImage;
