import * as h from './style/HomePageStyle';
import useStarStore from '../stores/starStore';
import StarName from '../components/Star/StarName';
import { useEffect } from 'react';
import { useQuery } from '@tanstack/react-query';
import { GetStarMark } from '../apis/StarMarkApis';
import useUserStore from '../stores/userStore';
import Loading from '../components/common/Loading/Loading';
import useConstellationStore from '../stores/constellationStore';
import StarInfoCarousel from '../components/StarInfoCarousel/StarInfoCarousel';
import EmbCanvas from '../components/Star/EmbCanvas';

const EmbPage = () => {
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
        <EmbCanvas />
      </h.Wrapper>
    </>
  );
};

export default EmbPage;
