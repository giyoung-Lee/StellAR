import * as w from './style/WebPageStyle';
import LeftSide from '../components/WebPage/LeftSide';
import RightSide from '../components/WebPage/RightSide';

const WebPage = () => {
  return (
    <w.Container>
      <w.Wrapper>
        <w.LeftSide>
          <LeftSide />
        </w.LeftSide>
        <w.RightSide>
          <RightSide />
        </w.RightSide>
      </w.Wrapper>
    </w.Container>
  );
};

export default WebPage;

