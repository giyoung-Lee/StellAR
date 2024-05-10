import React from 'react';
import * as p from './style/CommonPageStyle';
import * as h from '../components/style/common/HeaderStyle';
import MyStarList from '../components/MyStar/MyStarList';
import useUserStore from '../stores/userStore';
import { useQuery } from '@tanstack/react-query';
import { GetUserConstellation } from '../apis/MyConstApis';
import Loading from '../components/common/Loading/Loading';

const MyStarPage = () => {
  const userStore = useUserStore();

  const {
    isLoading: isUserConstellationLoading,
    data: userConstellationData,
    isError,
    refetch: getUserConstellationRefetch,
  } = useQuery({
    queryKey: ['get-userConstellation'],
    queryFn: () => GetUserConstellation(userStore.userId),
  });

  if (isUserConstellationLoading) {
    return <Loading />;
  }

  return (
    <p.Wrapper>
      <h.Header>
        <h.Title>나만의 별자리</h.Title>
      </h.Header>
      <MyStarList userConstellationData={userConstellationData?.data} />
    </p.Wrapper>
  );
};

export default MyStarPage;
