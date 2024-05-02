import * as h from './style/HomePageStyle';
import MainCanvas from '../components/Star/MainCanvas';
import useStarStore from '../stores/starStore';
import StarName from '../components/Star/StarName';
import { useEffect } from 'react';

const HomePage = () => {
  const starStore = useStarStore();

  useEffect(() => {
    starStore.setStarClicked(false);
  }, []);
  return (
    <>
      <h.Wrapper>
        {starStore.starClicked ? <StarName /> : null}
        <MainCanvas />
      </h.Wrapper>
    </>
  );
};

export default HomePage;
