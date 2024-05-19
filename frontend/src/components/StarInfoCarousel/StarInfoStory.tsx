import * as s from '../style/StarInfoCarouselStyle';
import '../../pages/style/Fontawsome';

type Props = {
  constellationData: ConstellationDetail;
};

const StarInfoStory = ({ constellationData }: Props) => {
  return (
    <s.CardWrapper>
      <s.CardHeader>
        {constellationData?.constellationSubName} 이야기
      </s.CardHeader>
      <s.Mythology>{constellationData?.constellationStory}</s.Mythology>
    </s.CardWrapper>
  );
};

export default StarInfoStory;

