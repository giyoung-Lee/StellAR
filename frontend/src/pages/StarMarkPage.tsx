import React from 'react';
import * as p from './style/CommonPageStyle';
import * as h from '../components/style/common/HeaderStyle';
import MarkList from '../components/StarMark/MarkList';

const StarMarkPage = () => {
  return (
    <p.Wrapper>
      <h.Header>
        <h.Title>별마크</h.Title>
      </h.Header>
      <MarkList />
    </p.Wrapper>
  );
};

export default StarMarkPage;
