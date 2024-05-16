import * as s from '../style/StarInfoCarouselStyle';
import '../../pages/style/Fontawsome';

type Props = {
  constellationData: ConstellationDetail;
  setModalOpen: (isOpen: boolean) => void;
};

const StarInfoStory = ({ constellationData, setModalOpen }: Props) => {
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

