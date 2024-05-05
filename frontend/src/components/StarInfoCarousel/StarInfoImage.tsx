import React from 'react';
import * as s from '../style/StarInfoCarouselStyle';
import Tau from '/img/taurus.webp';
import useConstellationStore from '../../stores/constellationStore';

//https://www.mediastorehouse.com/granger-art-on-demand/zodiac-taurus-fresco-1575-villa-farnese-6207397.html

type Props = {
  constellationImg: string;
};

const StarInfoImage = ({ constellationImg }: Props) => {
  const constellationStore = useConstellationStore();
  return (
    <s.CardWrapper>
      <s.CardTitle>{constellationStore.constellationName}</s.CardTitle>
      <s.CardImage src={Tau} alt="mythology_image" />
    </s.CardWrapper>
  );
};

export default StarInfoImage;

