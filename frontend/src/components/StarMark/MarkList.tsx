import React from 'react';
import * as l from '../style/common/ListCardStyle';
import MarkItem from './MarkItem';

const MarkList = () => {
  const marklist = new Array(10).fill(0);
  return (
    <l.Wrapper>
      {marklist.map((item, index) => (
        <l.Card>
          <MarkItem />
        </l.Card>
      ))}
    </l.Wrapper>
  );
};

export default MarkList;
