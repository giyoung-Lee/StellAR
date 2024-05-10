import React from 'react';
import * as m from '../style/MyStarStyle';

type Props = {
  constellation: MyConstellation;
};

const MyStarItem = ({ constellation }: Props) => {
  return (
    <>
      <m.StarInfo>
        <m.StarName>{constellation.name}</m.StarName>
        <p>{constellation.description}</p>
        <m.Date>{constellation.createTime}</m.Date>
      </m.StarInfo>
    </>
  );
};

export default MyStarItem;
