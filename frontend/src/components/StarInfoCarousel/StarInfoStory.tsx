import React from 'react';
import * as s from '../style/StarInfoCarouselStyle';

import '../../pages/style/Fontawsome';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import Swal from 'sweetalert2';

type Props = {
  constellationData: ConstellationDetail;
  setModalOpen: (isOpen: boolean) => void;
};

const StarInfoStory = ({ constellationData, setModalOpen }: Props) => {
  const handleError = () => {
    setModalOpen(true); // 모달 열기
    Swal.fire({
      title: '오답!',
      text: '어쩌고저쩌고 입니당~',
      icon: 'error',
      confirmButtonText: '확인',
    }).then(() => {
      setModalOpen(false); // 모달 닫기
    });
  };

  const handleSuccess = () => {
    setModalOpen(true); // 모달 열기
    Swal.fire({
      title: '정답!',
      text: '어쩌고저쩌고 입니당~',
      icon: 'success',
      confirmButtonText: '확인',
    }).then(() => {
      setModalOpen(false); // 모달 닫기
    });
  };
  return (
    <s.CardWrapper>
      <s.CardHeader>
        {constellationData?.constellationSubName} 이야기
      </s.CardHeader>
      <s.Mythology>{constellationData?.constellationStory}</s.Mythology>
    </s.CardWrapper>
  );
};

export default StarInfoStory;

