import { BrowserRouter as Router } from 'react-router-dom';
import AppRoutes from './routes/AppRoutes';

const App = () => {
  return (
    <>
      {/* 화면 크기 너무 작으면 서비스 이용 제한 */}
      <div id="appWarning" className="TooSmall">
        <p>화면이 StellAR의 하늘보다 작아요</p>
      </div>
      {/* 화면 크기 정상 범위 */}
      <div className="AppSize">
        <Router>
          <AppRoutes />
        </Router>
      </div>
    </>
  );
};

export default App;
