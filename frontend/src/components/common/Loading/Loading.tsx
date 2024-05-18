import './Loading.css';
import { getRandomInt } from '../../../utils/random';
import pieceKnowledge from '../../../assets/piece_knowledge.json';
import { useEffect, useState } from 'react';

const Loading = () => {
  const [loadingMessage, setLoadingMessage] = useState('');

  useEffect(() => {
    const messages = pieceKnowledge.piece_knowledge;
    const randomIndex = getRandomInt(0, messages.length - 1);
    setLoadingMessage(messages[randomIndex]?.contents);
  }, []);

  return (
    <div className="flex flex-col items-center justify-center min-h-[100vh]">
      <div className="pyramid-loader mb-44">
        <div className="wrapper-pyramid">
          <span className="side side1"></span>
          <span className="side side2"></span>
          <span className="side side3"></span>
          <span className="side side4"></span>
          <span className="shadow"></span>
        </div>
      </div>
      <div className="absolute flex flex-col items-center justify-center mt-5 text-center">
        <p className="message">{loadingMessage}</p>
      </div>
    </div>
  );
};

export default Loading;

