import * as h from './style/HomePageStyle';
import MainCanvas from '../components/Star/MainCanvas';
import useStarStore from '../stores/starStore';
import StarName from '../components/Star/StarName';
import React, { useEffect, useState } from 'react';
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
  // const [renderKey, setRenderKey] = useState(0); // 강제 렌더링을 위한 key

  useEffect(() => {
    starStore.setStarClicked(false);
    starStore.setStarId('');
    starStore.setStarPosition(null);
    constellationStore.setConstellationClicked(false);
  }, []);

  // useEffect(() => {
  //   setRenderKey((prevKey) => prevKey + 1);
  // }, [starStore.starClicked]);

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
        {(starStore.starClicked && starStore.linkedStars.length < 1) ||
        (starStore.planetClicked && starStore.linkedStars.length < 1) ||
        (starStore.zoomFromOther && starStore.linkedStars.length < 1) ? (
          <StarName />
        ) : null}

        {starStore.linkedStars.length > 0 ? (
          <div className="absolute flex flex-col justify-between h-[200px] z-[1000]">
            <button className="p-3 bg-white bg-opacity-25 rounded-xl shadow-custom border-opacity-18 backdrop-blur-sm">
              나만의 별자리 생성
            </button>

            <button className="p-3 bg-white bg-opacity-25 rounded-xl shadow-custom border-opacity-18 backdrop-blur-sm">
              다시 선택하기
            </button>
          </div>
        ) : null}

        {constellationStore.constellationClicked ? (
          <StarInfoCarousel active={0} />
        ) : null}
        <MainCanvas />
      </h.Wrapper>
    </>
  );
};

export default HomePage;
