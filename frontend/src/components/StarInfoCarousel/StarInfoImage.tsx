import * as s from '../style/StarInfoCarouselStyle';
//https://www.mediastorehouse.com/granger-art-on-demand/zodiac-taurus-fresco-1575-villa-farnese-6207397.html

type Props = {
  constellationImg: string;
};

const StarInfoImage = ({ constellationImg }: Props) => {
  return (
    <s.ImageWrapper>
      <s.CardImage src={constellationImg} alt="mythology_image" />
    </s.ImageWrapper>
  );
};

export default StarInfoImage;
