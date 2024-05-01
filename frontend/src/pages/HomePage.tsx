import * as h from './style/HomePageStyle';
import MainCanvas from '../components/Star/MainCanvas';
import useStarStore from '../stores/starStore';
import StarName from '../components/Star/StarName';
import { useEffect } from 'react';
import MarkBtn from '../components/StarMark/MarkBtn';

const HomePage = () => {
  const starStore = useStarStore();

  useEffect(() => {
    starStore.setStarClicked(false);
  }, []);
  return (
    <>
      <h.Wrapper>
        {/* {starStore.starClicked ? <StarName /> : null} */}
        {starStore.starClicked ? <MarkBtn /> : null}
        {/* <StarInfoCarousel active={0} /> */}
        <MainCanvas />
      </h.Wrapper>
    </>
  );
};

export default HomePage;

