import React from 'react';
import background from '/img/background.jpg';
import * as m from './style/StarMarkPageStyle';
import * as h from '../components/common/HeaderStyle';
import MarkList from '../components/StarMark/MarkList';

const StarMarkPage = () => {
  return (
    <m.Wrapper $background={background}>
      <h.Header>
        <h.Title>별마크</h.Title>
      </h.Header>
      <MarkList />
    </m.Wrapper>
  );
};

export default StarMarkPage;
