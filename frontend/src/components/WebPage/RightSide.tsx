import MainCanvas from '../Star/MainCanvas';
import * as r from '../style/WebPageStyle';
import { useEffect, useState } from 'react';
import logo from '/icons/logo_nobg.png';
import styled from 'styled-components';
import Background from '/img/background.jpg';

const RightSide = () => {
  // 웹에 처음 접속하거나 껐다가 켰을 때 로딩 페이지를 띄울 상태(5초)
  const [isFirstLoading, setIsFirstLoading] = useState(true);

  useEffect(() => {
    // 세션스토리지에서 hasVisited 값 가져오기
    const hasVisited = sessionStorage.getItem('hasVisited');

    if (hasVisited) {
      setIsFirstLoading(false);
    } else {
      setTimeout(() => {
        setIsFirstLoading(false);
        sessionStorage.setItem('hasVisited', 'true');
      }, 5000);
    }
  }, []);

  // 로딩바 만들어보자
  const [loadingProgress, setLoadingProgress] = useState(0);

  useEffect(() => {
    if (isFirstLoading) {
      const interval = setInterval(() => {
        setLoadingProgress((prevProgress) => {
          if (prevProgress < 100) {
            return prevProgress + 2; // 0.1초에 2%씩 증가
          }
          clearInterval(interval);
          return 100;
        });
      }, 100); // 0.1초마다 실행
    }
  }, [isFirstLoading]);

  return (
    <r.RightWrapper>
      <r.CanvasContainer>
        <div className="card">
          <div className="content">
            <div className="back">
              <div className="back-content">
                {/* 로딩페이지 구성 */}
                {isFirstLoading && (
                  <LoadingContainer>
                    <div className="flex flex-col items-center min-h-screen">
                      <img
                        src={logo}
                        alt="logo_image"
                        className="w-[270px] mt-[10vh]"
                      />
                      <div className="m-20 text-lg text-center">
                        <p>조금만 기다려주세요..</p>
                        <p>우주를 만들고 있어요..</p>
                      </div>
                      <LoadingBar>
                        <LoadingProgress progress={loadingProgress} />
                      </LoadingBar>
                    </div>
                  </LoadingContainer>
                )}

                <MainCanvas />
                {/* <HomePage /> */}
              </div>
            </div>
          </div>
        </div>
      </r.CanvasContainer>
    </r.RightWrapper>
  );
};

export default RightSide;

// 로딩바 관련이다!
const LoadingContainer = styled.div`
  position: fixed;
  z-index: 20000000;
  width: 100%;
  height: 100%;
  background-color: black;
  background-image: url(${Background});
  background-size: cover;
`;

interface LoadingProgressProps {
  progress: number;
}

const LoadingBar = styled.div`
  width: 70%;
  height: 20px;
  background-color: #eee;
  margin-top: 20px;
`;

const LoadingProgress = styled.div<LoadingProgressProps>`
  width: ${(props) => props.progress}%;
  height: 100%;
  background-color: #667eea;
  transition: width 0.1s linear;
`;
