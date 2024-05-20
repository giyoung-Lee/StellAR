import * as s from '../style/StarInfoCarouselStyle';

type Props = {
  constellationData: ConstellationDetail;
};

const StarInfoScience = ({ constellationData }: Props) => {
  return (
    <s.CardWrapper>
      <s.CardHeader>
        {constellationData?.constellationSubName}
        <s.CardHeaderName>
          {constellationData?.constellationId}
        </s.CardHeaderName>
      </s.CardHeader>

      <s.ScienceInfo>
        <p>{constellationData?.constellationSeason}철 별자리</p>
        <p>
          관측 시기: {constellationData?.constellationStartObservation} ~{' '}
          {constellationData?.constellationEndObservation}
        </p>
        {constellationData?.constellationId == 'Bukdu' ? null : (
          <p>알파(α)별: {constellationData?.constellationDesc}</p>
        )}
        {constellationData?.constellationId == 'Bukdu' ? null : (
          <p className='absolute text-sm bottom-5 left-6 w-[80%]'>* 알파별: 별자리에서 가장 밝게 빛나는 별 </p>
        )}
      </s.ScienceInfo>
    </s.CardWrapper>
  );
};

export default StarInfoScience;
