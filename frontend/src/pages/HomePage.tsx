import React from 'react';
import StarInfoCarousel from '../components/StarInfoCarousel/StarInfoCarousel';
import * as h from './style/HomePageStyle';
import MainCanvas from '../components/Star/MainCanvas';

const HomePage = () => {
  return (
    <>
      <h.Wrapper>
        {/* <StarInfoCarousel active={0} /> */}
        <MainCanvas />
      </h.Wrapper>
    </>
  );
};

export default HomePage;
