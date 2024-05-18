import * as w from './style/WebPageStyle';
import LeftSide from '../components/WebPage/LeftSide';
import RightSide from '../components/WebPage/RightSide';
import { useEffect } from 'react';
import useUserStore from '../stores/userStore';

const WebPage = () => {
  const userStore = useUserStore();
  useEffect(() => {
    userStore.setIsLogin(false);
    userStore.setUser({ userId: '' });
  }, []);
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

