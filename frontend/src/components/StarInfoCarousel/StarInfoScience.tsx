import React from 'react';
import * as s from '../style/StarInfoCarouselStyle';

const StarInfoScience = () => {
  return (
    <s.CardWrapper>
      <s.CardHeader>
        황소자리
        <s.CardHeaderName>Taurus</s.CardHeaderName>
      </s.CardHeader>

      <s.ScienceInfo>
        <p>봄철 별자리(3월, 4월)</p>
        <p>관측 시기: 1월 ~ 9월</p>
        <p>
          알데바란(Aldebaran, Alpha Tauri)은 황소자리의 알파성으로 분광형 K5
          III의 오렌지색 거성이다. 지름은 6100만km로 태양의 약 45배, 질량은
          태양의 1.16배, 밝기는 태양의 약 600배, 그리고 표면온도는 3900K 이다.
        </p>
      </s.ScienceInfo>
    </s.CardWrapper>
  );
};

export default StarInfoScience;

