import React from 'react';
import * as l from '../style/common/ListCardStyle';
import MarkItem from './MyStarItem';
import MyStarItem from './MyStarItem';
import Lottie from 'lottie-react';
import emptyLottie from '../../assets/lottie/empty.json';

type Props = {
  userConstellationData: MyConstellation[];
};

const MyStarList = ({ userConstellationData }: Props) => {
  return (
    <l.Wrapper>
      {userConstellationData?.length > 0 ? (
        userConstellationData.map((constellation, index) => (
          <l.Card key={index}>
            <MyStarItem constellation={constellation} />
          </l.Card>
        ))
      ) : (
        <l.Empty>
          <div className="message">
            <p>나만의 별자리가 없어요!</p>
            <p>홈에서 나만의 별자리를 만들어보세요</p>
          </div>
          <div className="lottie">
            <Lottie animationData={emptyLottie} />
          </div>
        </l.Empty>
      )}
    </l.Wrapper>
  );
};

export default MyStarList;
