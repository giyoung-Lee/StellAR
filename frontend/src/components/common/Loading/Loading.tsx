import { useEffect } from 'react';
import './Loading.css';
import useLoadingStore from '../../../stores/loadingStore';
import { getRandomInt } from '../../../utils/random';

const Loading = () => {
  const { loadingMessage } = useLoadingStore();

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

      <div className="absolute mt-5 text-center flex flex-col justify-center items-center">
        <p className="message">
          {loadingMessage ? loadingMessage[getRandomInt(0, 6)]?.contents : null}
        </p>
      </div>
    </div>
  );
};

export default Loading;
