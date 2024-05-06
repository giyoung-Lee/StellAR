import React from 'react';
import * as l from '../style/WebPageStyle';
import logo from '/icons/webLogo.png';
import qrcode from '/icons/qrcode.png';

const LeftSide = () => {
  return (
    <l.LeftWrapper>
      <l.LogoImg src={logo} />
      {/* <l.ServiceInfo>
        <div className="cards">
          <div className="card">
            <p className="tip">실시간 별자리 AR 3d 맵</p>
          </div>
          <div className="card">
            <p className="tip">나만의 별자리를 만들어보세요</p>
          </div>
          <div className="card">
            <p className="tip">스마트 빔! 머라고하징 ㅋㅅㅋ</p>
          </div>
        </div>
      </l.ServiceInfo> */}
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
    </l.LeftWrapper>
  );
};

export default LeftSide;

