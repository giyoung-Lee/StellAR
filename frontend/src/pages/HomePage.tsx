import * as h from './style/HomePageStyle';
import MainCanvas from '../components/Star/MainCanvas';
import useStarStore from '../stores/starStore';
import StarName from '../components/Star/StarName';

const HomePage = () => {
  const starStore = useStarStore();
  return (
    <>
      <h.Wrapper>
        {starStore.starClicked ? <StarName /> : null}
        {/* <StarInfoCarousel active={0} /> */}
        <MainCanvas />
      </h.Wrapper>
    </>
  );
};

export default HomePage;
