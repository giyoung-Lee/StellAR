import React from 'react';
import * as m from '../style/MyStarStyle';
import useStarStore from '../../stores/starStore';
import getXYZ from '../../hooks/getXYZ';
import useUserStore from '../../stores/userStore';
import { useNavigate } from 'react-router-dom';

type Props = {
  constellation: MyConstellation;
};

const MyStarItem = ({ constellation }: Props) => {
  const starStore = useStarStore();
  const userStore = useUserStore();
  const navigate = useNavigate();

  const formatDate = (dateString: string) => {
    const date = new Date(dateString);
    return date.toLocaleDateString('ko-KR', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
    });
  };

  const findMyConstellation = () => {
    starStore.setZoomFromOther(true);
    const { x, y, z } = getXYZ(
      constellation.hourRA,
      constellation.degreeDEC,
      userStore.userLat,
      userStore.userLng,
    );
    starStore.setZoomX(-1 * x * constellation.nomalizedMagV);
    starStore.setZoomY(z * constellation.nomalizedMagV);
    starStore.setZoomZ(y * constellation.nomalizedMagV);
    starStore.setZoomStarId(constellation.name);
    starStore.setStarClicked(true);
    navigate('/');
  };

  return (
    <>
      <m.ConstellationInfo onClick={findMyConstellation}>
        <m.ConstellationName>{constellation.name}</m.ConstellationName>
        <m.ConstellationDes>{constellation.description}</m.ConstellationDes>
        <m.Date>{formatDate(constellation.createTime)}</m.Date>
      </m.ConstellationInfo>
    </>
  );
};

export default MyStarItem;

