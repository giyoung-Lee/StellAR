import { BrowserRouter as Router } from 'react-router-dom';
import AppRoutes from './routes/AppRoutes';
import GlobalStyle from './GlobalStyle';
import "./index.css";

const App = () => {
  return (
    <>
      {/* 화면 크기 너무 작으면 서비스 이용 제한 */}
      <div id="appWarning" className="TooSmall">
        <p>화면이 StellAR의 하늘보다 작아요 같은 멘트</p>
      </div>

      {/* 화면 크기 웹 */}
      <div id="WebDesign" className="WebSize">
        <p>웹일 때, 디자인 넣을 예정</p>
      </div>

      {/* 화면 크기 모바일 */}
      <div className="AppSize">
        <Router>
          <GlobalStyle />
          <AppRoutes />
        </Router>
      </div>
    </>
  );
};

export default App;
