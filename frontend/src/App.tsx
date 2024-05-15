import { BrowserRouter as Router, useLocation } from 'react-router-dom';
import AppRoutes from './routes/AppRoutes';
import GlobalStyle from './GlobalStyle';
import './index.css';
import NavIcon from '../src/components/common/NavBar/NavIcon';
import useStarStore from '../src/stores/starStore';
import { useEffect, useRef, useState } from 'react';
import WebRoutes from './routes/WebRoutes';
import '../src/pages/style/Fontawsome';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

// 네비게이션바 예외 처리
const ConditionalNav = () => {
  const location = useLocation();

  if (
    location.pathname === '/entry' ||
    location.pathname === '/login' ||
    location.pathname === '/signup'
  ) {
    return null;
  }

  return <NavIcon />;
};

const App = () => {
  const starStore = useStarStore();
  const resetLinkedStars = useStarStore((state) => state.resetLinkedStars);

  // 오디오 관련 코드
  const [isPlaying, setIsPlaying] = useState(false);
  const audioRef = useRef<HTMLAudioElement>(null);

  const toggleMusic = () => {
    if (audioRef.current) {
      if (isPlaying) {
        audioRef.current.pause();
      } else {
        audioRef.current.play();
      }
      setIsPlaying(!isPlaying);
    }
  };

  useEffect(() => {
    // 마운트 시 클릭된 별 배열 초기화
    if (!starStore.starClicked) {
      resetLinkedStars();
    }
    // console.log(`클릭 되었는가? ${starStore.starClicked}`)
  }, [resetLinkedStars, starStore.starClicked]);

  return (
    <>
      {/* 배경음악 깔기 */}
      <audio ref={audioRef} loop>
        <source src="/bgm.mp3" type="audio/mpeg" />
        Your browser does not support the audio element.
      </audio>

      <div id="appWarning" className="TooSmall">
        <p>화면이 StellAR의 하늘보다 작아요</p>
      </div>

      <div id="WebDesign" className="WebSize">
        <Router>
          <GlobalStyle />
          <WebRoutes />
        </Router>
      </div>

      <div className="AppSize">
        <Router>
          <GlobalStyle />
          <AppRoutes />
          <button className="fixed bottom-3 right-3 z-[16777273]" onClick={toggleMusic}>
            {isPlaying ? (
              <FontAwesomeIcon icon="volume-high" size="xl" />
            ) : (
              <FontAwesomeIcon icon="volume-xmark" size="xl" />
            )}
          </button>
          <ConditionalNav />
        </Router>
      </div>
    </>
  );
};

export default App;
