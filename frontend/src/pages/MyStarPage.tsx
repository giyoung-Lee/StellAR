import React from 'react';
import * as p from './style/CommonPageStyle';
import * as h from '../components/common/HeaderStyle';
import MyStarList from '../components/MyStar/MyStarList';

const MyStarPage = () => {
  return (
    <p.Wrapper>
      <h.Header>
        <h.Title>나만의 별자리</h.Title>
      </h.Header>
      <MyStarList />
    </p.Wrapper>
  );
};

export default MyStarPage;
