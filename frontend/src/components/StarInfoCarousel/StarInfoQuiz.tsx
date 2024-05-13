import * as s from '../style/StarInfoCarouselStyle';
import '../../pages/style/Fontawsome';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import Swal from 'sweetalert2';
import Lottie from 'lottie-react';
import astroLottie from '../../assets/lottie/astro.json';
import { useEffect, useState } from 'react';

type Props = {
  constellationData: ConstellationDetail;
  setModalOpen: (isOpen: boolean) => void;
  quizData: QuizType[];
};

const StarInfoQuiz = ({ constellationData, setModalOpen, quizData }: Props) => {
  const [currentQuiz, setCurrentQuiz] = useState<QuizType | null>(null);

  // 랜덤 퀴즈 선택
  useEffect(() => {
    const randomQuiz = quizData[Math.floor(Math.random() * quizData.length)];
    setCurrentQuiz(randomQuiz);
  }, [quizData]);

  const handleAnswer = (xo: string) => (event: React.MouseEvent<HTMLButtonElement>) => {
    setModalOpen(true); // 모달 열기
    if (currentQuiz && currentQuiz.constellationQuestionAnswer === xo) {
      Swal.fire({
        title: '정답!',
        text: '정답입니다! 카드를 넘겨 자세히 알아보세요.',
        icon: 'success',
        confirmButtonText: '확인',
      }).then(() => {
        setModalOpen(false); // 모달 닫기
      });
    } else {
      Swal.fire({
        title: '오답!',
        text: '오답입니다. 카드를 넘겨 정답을 알아보세요!',
        icon: 'error',
        confirmButtonText: '확인',
      }).then(() => {
        setModalOpen(false); // 모달 닫기
      });
    }
  };

  return (
    <s.CardWrapper>
      <s.CardHeader>
        {constellationData?.constellationSubName} QUIZ
      </s.CardHeader>
      <div className="flex flex-col items-center justify-start h-full">
        <div className="w-[140px]">
          <Lottie animationData={astroLottie} />
        </div>

        <s.QuizBox>
          <p className="q">Q. {currentQuiz?.constellationQuestionContents}</p>
          <div className="answer_box">
            <button className="o" onClick={handleAnswer('Y')}>
              <FontAwesomeIcon icon="o" color="white" className="icon" />
            </button>

            <button className="x" onClick={handleAnswer('N')}>
              <FontAwesomeIcon icon="xmark" color="white" className="icon" />
            </button>
          </div>
        </s.QuizBox>
      </div>
    </s.CardWrapper>
  );
};

export default StarInfoQuiz;
