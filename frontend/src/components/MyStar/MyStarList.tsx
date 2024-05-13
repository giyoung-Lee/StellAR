import React from 'react';
import * as l from '../style/common/ListCardStyle';
import MarkItem from './MyStarItem';
import MyStarItem from './MyStarItem';

type Props = {
  userConstellationData: MyConstellation[];
};

const MyStarList = ({ userConstellationData }: Props) => {
  return (
    <l.Wrapper>
      {userConstellationData.map((constellation, index) => (
        <l.Card key={index}>
          <MyStarItem constellation={constellation} />
        </l.Card>
      ))}
    </l.Wrapper>
  );
};

export default MyStarList;
