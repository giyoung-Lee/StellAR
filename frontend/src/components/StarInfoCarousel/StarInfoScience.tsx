import React from 'react';
import * as s from '../style/StarInfoCarouselStyle';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

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
          <p>
            <FontAwesomeIcon
              icon={['far', 'star']}
              style={{ paddingRight: '5px' }}
            />
            알파별 : {constellationData?.constellationDesc}
          </p>
        )}
      </s.ScienceInfo>
    </s.CardWrapper>
  );
};

export default StarInfoScience;
