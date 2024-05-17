import './Loading.css';
import { getRandomInt } from '../../../utils/random';
import pieceKnowledge from '../../../assets/piece_knowledge.json'

const Loading = () => {
  const loadingMessage = pieceKnowledge.piece_knowledge

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
        <p className="message">
          {loadingMessage ? loadingMessage[getRandomInt(0, 6)]?.contents : null}
        </p>
      </div>
    </div>
  );
};

export default Loading;
