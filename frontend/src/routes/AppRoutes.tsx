import { useEffect, useState } from 'react';
import { Route, Routes, useLocation } from 'react-router-dom';
import styled, { keyframes } from 'styled-components';

import HomePage from '../pages/HomePage';
import NotFoundPage from '../pages/NotFoundPage';
import EntryPage from '../pages/EntryPage';
import LoginPage from '../pages/LoginPage';
import SignupPage from '../pages/SignupPage';
import MyStarPage from '../pages/MyStarPage';
import StarMarkPage from '../pages/StarMarkPage';
import EventPage from '../pages/EventPage';
import ShopPage from '../pages/ShopPage';
import OrderPage from '../pages/OrderPage';

const AppRoutes = () => {
  const location = useLocation();
  const [displayLocation, setDisplayLocation] = useState(location);
  const [transitionStage, setTransitionStage] = useState('fadeIn');

  useEffect(() => {
    if (location !== displayLocation) setTransitionStage('fadeOut');
  }, [location]);

  return (
    <PageContainer
      className={`${transitionStage}`}
      onAnimationEnd={() => {
        setTransitionStage('fadeIn');
        setDisplayLocation(location);
      }}
    >
      <Routes location={displayLocation}>
        <Route path="/" element={<HomePage />} />
        <Route path="*" element={<NotFoundPage />} />
        <Route path="/entry" element={<EntryPage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/signup" element={<SignupPage />} />
        <Route path="/myStar/:id" element={<MyStarPage />} />
        <Route path="/starMark/:id" element={<StarMarkPage />} />
        <Route path="/event" element={<EventPage />} />
        <Route path="/shop" element={<ShopPage />} />
        <Route path="/order/:id" element={<OrderPage />} />
      </Routes>
    </PageContainer>
  );
};

export const fadeInAnimation = keyframes`
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
`;
export const fadeOutAnimation = keyframes`
  0% {
    opacity: 1;
  }
  100% {
    opacity: 0;
  }
`;
export const PageContainer = styled.div`
  opacity: 0;
  &.fadeIn {
    animation: ${fadeInAnimation} 1000ms;
    animation-fill-mode: forwards;
  }
  &.fadeOut {
    animation: ${fadeOutAnimation} 1000ms;
    animation-fill-mode: forwards;
    // animation-fill-mode :애니메이션의 끝난 후의 상태를 설정
    // forward : 애니메이션이 끝난 후 마지막 CSS 그대로 있음
  }
`;

export default AppRoutes;
