import {
  BrowserRouter as Router,
  useLocation,
} from "react-router-dom";
import AppRoutes from './routes/AppRoutes';
import GlobalStyle from './GlobalStyle';
import "./index.css";
import NavIcon from '../src/components/common/NavBar/NavIcon';
import useStarStore from '../src/stores/starStore';
import { useEffect } from "react";

// 네비게이션바 예외 처리
const ConditionalNav = () => {
  const location = useLocation();

  if (
    location.pathname === "/entry" ||
    location.pathname === "/login" ||
    location.pathname === "/signup"
  ) {
    return null;
  }

  return <NavIcon />;
};

const App = () => {

  const resetClickedStars = useStarStore(state => state.resetClickedStars);

  useEffect(() => {
    // 마운트 시 클릭된 별 배열 초기화
    resetClickedStars();
  }, [resetClickedStars]);

  return (
    <>
      <div id="appWarning" className="TooSmall">
        <p>화면이 StellAR의 하늘보다 작아요 같은 멘트</p>
      </div>

      <div id="WebDesign" className="WebSize">
        <p>웹일 때, 디자인 넣을 예정</p>
      </div>

      <div className="AppSize">
        <Router>
          <GlobalStyle />
          <AppRoutes />
          <ConditionalNav />
        </Router>
      </div>
    </>
  );
};

export default App;