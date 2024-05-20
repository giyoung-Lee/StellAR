import { useEffect } from 'react';
import * as p from './style/CommonPageStyle';
import * as h from '../components/style/common/HeaderStyle';
import MarkList from '../components/StarMark/MarkList';
import { useQuery } from '@tanstack/react-query';
import { GetStarMark } from '../apis/StarMarkApis';
import Loading from '../components/common/Loading/Loading';
import useUserStore from '../stores/userStore';
import useStarStore from '../stores/starStore';

const StarMarkPage = () => {
  const userStore = useUserStore();
  const starStore = useStarStore();

  const {
    isLoading: isStarMarkLoading,
    data: starMarkData,
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
    <p.Wrapper>
      <h.Header className="starmark">
        <h.Title>별마크</h.Title>
      </h.Header>
      <MarkList starMarkData={starStore.markedStars} />
    </p.Wrapper>
  );
};

export default StarMarkPage;
