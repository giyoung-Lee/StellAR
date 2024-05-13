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
      <s.QuizBox>
        <p className="q">
          Q. 어쩌고저쩌고 자리는 어쩌고저쩌고인 그런 그런것이다. 질문이 별로
          안길수도있지만 길수도있다.
        </p>
        <div className="answer_box">
          <button className="o" onClick={handleError}>
            <FontAwesomeIcon icon="o" color="white" className="icon" />
          </button>

          <button className="x" onClick={handleSuccess}>
            <FontAwesomeIcon icon="xmark" color="white" className="icon" />
          </button>
        </div>
      </s.QuizBox>
      <s.Mythology>{constellationData?.constellationStory}</s.Mythology>
    </s.CardWrapper>
  );
};

export default StarInfoStory;

