import React from 'react';
import StarInfoCarousel from '../components/StarInfoCarousel/StarInfoCarousel';
import * as h from './style/HomePageStyle';

const HomePage = () => {
  return (
    <>
      <h.Wrapper>
        별 보는 홈페이지 화면입니당
        <StarInfoCarousel active={1} />
      </h.Wrapper>
    </>
  );
};

export default HomePage;
