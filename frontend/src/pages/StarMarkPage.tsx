import React, { useEffect } from 'react';
import * as p from './style/CommonPageStyle';
import * as h from '../components/style/common/HeaderStyle';
import MarkList from '../components/StarMark/MarkList';
import { useQuery } from '@tanstack/react-query';
import { GetStarMark } from '../apis/StarMarkApis';
import Loading from '../components/common/Loading/Loading';
import useUserStore from '../stores/userStore';

const StarMarkPage = () => {
  const userStore = useUserStore();

  const {
    isLoading: isStarMarkLoading,
    data: starMarkData,
    isError: isStarMarkError,
    refetch: getStarMarkRefetch,
  } = useQuery({
    queryKey: ['get-starMarks'],
    queryFn: () => {
      return GetStarMark(userStore.userId);
    },
  });

  if (isStarMarkLoading) {
    return <Loading />;
  }

  return (
    <p.Wrapper>
      <h.Header className="starmark">
        <h.Title>별마크</h.Title>
      </h.Header>
      <MarkList starMarkData={starMarkData?.data} />
    </p.Wrapper>
  );
};

export default StarMarkPage;
