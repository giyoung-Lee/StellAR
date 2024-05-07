import * as h from './style/HomePageStyle';
import MainCanvas from '../components/Star/MainCanvas';
import useStarStore from '../stores/starStore';
import StarName from '../components/Star/StarName';
import React, { useEffect } from 'react';
import MarkBtn from '../components/StarMark/MarkBtn';
import { useQuery } from '@tanstack/react-query';
import { GetStarMark } from '../apis/StarMarkApis';
import useUserStore from '../stores/userStore';
import Loading from '../components/common/Loading/Loading';
import useConstellationStore from '../stores/constellationStore';
import StarInfoCarousel from '../components/StarInfoCarousel/StarInfoCarousel';

const HomePage = () => {
  const starStore = useStarStore();
  const userStore = useUserStore();
  const constellationStore = useConstellationStore();

  useEffect(() => {
    starStore.setStarClicked(false);
    constellationStore.setConstellationClicked(false);
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
        {starStore.starClicked || starStore.planetClicked ? <StarName /> : null}
        {constellationStore.constellationClicked ? (
          <StarInfoCarousel active={0} />
        ) : null}
        <MainCanvas />
      </h.Wrapper>
    </>
  );
};

export default HomePage;
