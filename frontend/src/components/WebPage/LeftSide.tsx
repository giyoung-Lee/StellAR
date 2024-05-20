import * as l from '../style/WebPageStyle';
import logo from '/icons/webLogo.png';
import qrcode from '/icons/qrcode.png';
import { useNavigate } from 'react-router-dom';

const LeftSide = () => {
  const navigate = useNavigate();

  return (
    <l.LeftWrapper>
      <l.LogoImg src={logo} />
      <l.LogoMessage>내 손 안의 작은 우주</l.LogoMessage>
      <l.GoApp>
        <div className="light-button">
          <button className="bt">
            <div className="light-holder">
              <p>다운로드</p>
              <div className="dot"></div>
              <div className="light"></div>
            </div>
            <div className="button-holder">
              <img src={qrcode} />
            </div>
          </button>
        </div>
      </l.GoApp>
      <l.Preview onClick={() => navigate('/preview')}>
        <button className="learn-more">
          <span className="circle" aria-hidden="true">
            <span className="icon arrow"></span>
          </span>
          <span className="button-text">웹으로 미리보기</span>
        </button>
      </l.Preview>
    </l.LeftWrapper>
  );
};

export default LeftSide;
