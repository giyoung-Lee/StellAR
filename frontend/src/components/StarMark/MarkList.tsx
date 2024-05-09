import React from 'react';
import * as l from '../style/common/ListCardStyle';
import MarkItem from './MarkItem';

type Props = {
  starMarkData: StarMarkType[];
};

const MarkList = ({ starMarkData }: Props) => {
  const marklist = starMarkData;
  return (
    <l.Wrapper>
      {marklist.map((item, index) => (
        <l.Card key={index}>
          <MarkItem
            starId={item.starId}
            bookmarkName={item.bookmarkName}
            createTime={item.craeteTime}
            RA={item.hourRA}
            DEC={item.degreeDEC}
            nomalizedMagV={item.nomalizedMagV}
          />
        </l.Card>
      ))}
    </l.Wrapper>
  );
};

export default MarkList;
