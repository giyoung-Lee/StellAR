import * as h from './style/HomePageStyle';
import MainCanvas from '../components/Star/MainCanvas';
import useStarStore from '../stores/starStore';
import StarName from '../components/Star/StarName';
import { useEffect } from 'react';
import MarkBtn from '../components/StarMark/MarkBtn';
import { useQuery } from '@tanstack/react-query';
import { GetStarMark } from '../apis/StarMarkApis';
import useUserStore from '../stores/userStore';
import Loading from '../components/common/Loading/Loading';

const HomePage = () => {
  const starStore = useStarStore();
  const userStore = useUserStore();

  useEffect(() => {
    starStore.setStarClicked(false);
  }, []);

  const {
    isLoading: isStarMarkLoading,
    data: starMarkData,
    isError: isStarMarkError,
    refetch: getStarMarkRefetch,
  } = useQuery({
    queryKey: ['get-starMarks'],
    queryFn: () => GetStarMark(userStore.userId),
  });

  useEffect(() => {
    if (starMarkData) {
      starStore.setMarkedStars(starMarkData.data);
    }
  }, [starMarkData]);

  useEffect(() => {
    getStarMarkRefetch();
  }, [starStore.markSaveToggle]);

  if (isStarMarkLoading) {
    return <Loading />;
  }

  return (
    <>
      <h.Wrapper>
        {starStore.starClicked ? <StarName /> : null}
        {/* {starStore.starClicked ? <MarkBtn /> : null} */}
        {/* <StarInfoCarousel active={0} /> */}
        <MainCanvas />
      </h.Wrapper>
    </>
  );
};

export default HomePage;
