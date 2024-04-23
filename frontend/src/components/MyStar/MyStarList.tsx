import React from 'react';
import * as l from '../common/ListCardStyle';
import MarkItem from './MyStarItem';
import MyStarItem from './MyStarItem';

const MyStarList = () => {
  const myStarList = new Array(2).fill(0);
  return (
    <l.Wrapper>
      {myStarList.map((item, index) => (
        <l.Card>
          <MyStarItem />
        </l.Card>
      ))}
    </l.Wrapper>
  );
};

export default MyStarList;
