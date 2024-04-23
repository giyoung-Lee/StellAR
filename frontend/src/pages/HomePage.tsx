import React from 'react';
import StarInfoCarousel from '../components/StarInfoCarousel/StarInfoCarousel';
import * as h from './style/HomePageStyle';

const HomePage = () => {
  return (
    <>
      <h.Wrapper>
        <StarInfoCarousel active={0} />
      </h.Wrapper>
    </>
  );
};

export default HomePage;

