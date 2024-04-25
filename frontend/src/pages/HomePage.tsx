import * as h from './style/HomePageStyle';
import MainCanvas from '../components/Star/MainCanvas';
import NavIcon from '../components/common/NavBar/NavIcon';

const HomePage = () => {
  return (
    <>
      <h.Wrapper>
        {/* <StarInfoCarousel active={0} /> */}
        <MainCanvas />
      </h.Wrapper>
      <NavIcon />
    </>
  );
};

export default HomePage;
