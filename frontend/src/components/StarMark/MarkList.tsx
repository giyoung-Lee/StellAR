import * as l from '../style/common/ListCardStyle';
import MarkItem from './MarkItem';
import Lottie from 'lottie-react';
import emptyLottie from '../../assets/lottie/empty.json';

type Props = {
  starMarkData: StarMarkType[];
};

const MarkList = ({ starMarkData }: Props) => {
  const marklist = starMarkData;
  return (
    <l.Wrapper>
      {marklist?.length > 0 ? (
        marklist.map((item, index) => (
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
        ))
      ) : (
        <l.Empty>
          <div className="message">
            <p>별마크된 별이 없어요!</p>
            <p>밤하늘에서 나의 별을 저장해보세요</p>
          </div>
          <div className="lottie">
            <Lottie animationData={emptyLottie} />
          </div>
        </l.Empty>
      )}
    </l.Wrapper>
  );
};

export default MarkList;
