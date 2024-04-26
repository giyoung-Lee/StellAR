import React, { useState } from 'react';
import styled from 'styled-components';

const FixedContainer = styled.div`
  position: fixed;
  display: flex;
  justify-content: center;
  min-width: 100%;
  bottom: 5vw;
`;

const CheckboxWrapper = styled.div`
  * {
    -webkit-tap-highlight-color: transparent;
    outline: none;
    display: flex;
    justify-content: center;
  }

  input[type='checkbox'] {
    display: none;
  }

  label {
    --size: 50px;
    --shadow: calc(var(--size) * 0.07) calc(var(--size) * 0.1);
    position: fixed;
    display: block;
    width: var(--size);
    height: var(--size);
    margin: 0 auto;
    background-color: #4158d0;
    background-image: linear-gradient(
      43deg,
      #4158d0 0%,
      #c850c0 46%,
      #ffcc70 100%
    );
    border-radius: 50%;
    box-shadow: 0 var(--shadow) #ffbeb8;
    cursor: pointer;
    transition:
      0.2s ease transform,
      0.2s ease background-color,
      0.2s ease box-shadow;
    overflow: hidden;
    z-index: 1;

    &:active {
      transform: scale(0.9);
    }
  }

  input[type='checkbox']:checked + label {
    bottom: 29vh;
    background-color: #4158d0;
    background-image: linear-gradient(
      43deg,
      #4158d0 0%,
      #c850c0 46%,
      #ffcc70 100%
    );
    box-shadow:
      rgba(0, 0, 0, 0.3) 0px 19px 38px,
      rgba(0, 0, 0, 0.22) 0px 15px 12px;
  }

  input[type='checkbox']:checked + label:before {
    width: 0;
    height: 0;
  }

  ul {
    position: relative;
    width: 300px;
    height: 300px;
    margin: 0 auto;
    list-style: none;
  }

  li {
    position: absolute;
    transform-origin: 50% 150px; // 원의 중심으로부터 반지름만큼 떨어진 곳에 위치
    width: 100px;
    height: 40px;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  li:nth-child(1) {
    transform: rotate(0deg) translate(0);
  }
  li:nth-child(2) {
    transform: rotate(60deg) translate(0);
  }
  li:nth-child(3) {
    transform: rotate(120deg) translate(0);
  }
  li:nth-child(4) {
    transform: rotate(180deg) translate(0);
  }
  li:nth-child(5) {
    transform: rotate(240deg) translate(0);
  }
  li:nth-child(6) {
    transform: rotate(300deg) translate(0);
  }

  section {
    margin-bottom: 10vh;
  }
`;

const NavBar = () => {
  const [angle, setAngle] = useState(0);
  const [startX, setStartX] = useState(0);
  const [startY, setStartY] = useState(0);
  const [isDragging, setIsDragging] = useState(false);

  const handleTouchStart = (e: React.TouchEvent) => {
    setStartX(e.touches[0].clientX);
    setStartY(e.touches[0].clientY);
    setIsDragging(true);
  };

  const handleTouchMove = (e: React.TouchEvent) => {
    if (isDragging) {
      const touchX = e.touches[0].clientX;
      const touchY = e.touches[0].clientY;
      const diffX = - startX + touchX;
      const diffY = - startY + touchY;
      setAngle((prev) => prev + diffX * 0.008 + diffY * 0.008);
    }
  };

  const handleTouchEnd = () => {
    setIsDragging(false);
  };

  return (
    <FixedContainer>
      <CheckboxWrapper>
        <ul
          style={{ transform: `rotate(${angle}deg)` }}
          onTouchStart={handleTouchStart}
          onTouchMove={handleTouchMove}
          onTouchEnd={handleTouchEnd}
        >
          <li>홈</li>
          <li>AR</li>
          <li>구매</li>
          <li>이벤트</li>
          <li>My별자리</li>
          <li>별마크</li>
        </ul>

        <section>
          <input id="nav_btn" type="checkbox" />
          <label htmlFor="nav_btn"></label>
        </section>
      </CheckboxWrapper>
    </FixedContainer>
  );
};

export default NavBar;
