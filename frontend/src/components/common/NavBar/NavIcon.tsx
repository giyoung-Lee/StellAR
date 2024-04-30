import React, { useState, useRef, useEffect } from 'react';
import styled from 'styled-components';
import { Link } from 'react-router-dom';
import useStarStore from '../../../stores/starStore';

const FixedContainer = styled.div`
  position: fixed;
  display: flex;
  justify-content: center;
  min-width: 100%;
  bottom: 0;
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
    position: relative;
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
    transition: 0.2s ease;
    overflow: hidden;
  }

  label:before {
    content: '';
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 60%;
    height: 60%;
    background-color: #000000;
    border-radius: 50%;
    box-shadow:
      inset 0 2px 4px rgba(255, 190, 184, 0.7),
      // 내부 상단에 밝은 하이라이트
      0 5px 8px rgba(0, 0, 0, 0.5),
      // 외부에 부드러운 그림자
      0 -5px 10px rgba(255, 190, 184, 0.3); // 외부 상단에 반사 효과
  }

  .checkbox-wrapper input[type='checkbox']:checked + label {
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
    /* width: 0;
    height: 0; */
  }

  ul {
    position: relative;
    width: 340px;
    height: 340px;
    margin: 0 auto;
    list-style: none;
    border-radius: 50%;
    background-image: url('/img/Board_img.png');
    background-size: cover;
  }

  li {
    position: absolute;
    transform-origin: 50% 170px; // 원의 중심으로부터 반지름만큼 떨어진 곳에 위치
    width: 100px;
    height: 110px;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  li span {
    color: #000000;
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
`;

const NavBar = () => {
  const ulRef = useRef<HTMLUListElement>(null);
  const [centerX, setCenterX] = useState(0);
  const [centerY, setCenterY] = useState(0);
  const [touchX, setTouchX] = useState(0);
  const [touchY, setTouchY] = useState(0);
  const [isDragging, setIsDragging] = useState(false);
  const [currAngle, setCurrAngle] = useState(0);
  const [prevAngle, setPrevAngle] = useState(0);
  const [finalAngle, setFinalAngle] = useState(0);
  const [isChecked, setIsChecked] = useState(true);

  const { isARMode, setARMode } = useStarStore();

  const handleARButtonClick = () => {
    setARMode(!isARMode);
    console.log(isARMode);
  };

  const handleCheckbox = () => {
    setIsChecked(!isChecked);
  };

  // useEffect(() => {
  //   if (ulRef.current) {
  //     const rect = ulRef.current.getBoundingClientRect();
  //     const centerY = rect.top + rect.height / 2; // ul의 중심 Y 위치 계산
  //     // console.log('UL의 y 위치:', centerY);
  //     setCenterY(centerY);
  //   }
  // }, [centerY]);

  const handleTouchStart = (e: React.TouchEvent) => {
    const touchX = e.touches[0].clientX;
    const touchY = e.touches[0].clientY;
    setTouchX(touchX);
    setTouchY(touchY);
    setIsDragging(true);

    const centerX = window.innerWidth / 2;
    if (ulRef.current) {
      const rect = ulRef.current.getBoundingClientRect();
      const centerY = rect.top + rect.height / 2;
      setCenterY(centerY);
    }
    setCenterX(centerX);

    const radian = Math.atan2(touchX - centerX, centerY - touchY);
    setCurrAngle(radian * (180 / Math.PI));
  };

  const handleTouchMove = (e: React.TouchEvent) => {
    if (isDragging) {
      const newTouchX = e.touches[0].clientX;
      const newTouchY = e.touches[0].clientY;

      const radian = Math.atan2(newTouchX - centerX, centerY - newTouchY);
      const newAngle = radian * (180 / Math.PI);

      // 직접 계산된 newAngle을 사용하여 상태 업데이트
      let angleDelta = newAngle - currAngle;
      if (angleDelta > 180) {
        angleDelta -= 360;
      } else if (angleDelta < -180) {
        angleDelta += 360;
      }

      setFinalAngle((prevFinalAngle) => prevFinalAngle + angleDelta);
      setPrevAngle(currAngle);
      setCurrAngle(newAngle);
    }
  };

  const handleTouchEnd = () => {
    setIsDragging(false);
  };

  return (
    <FixedContainer>
      <CheckboxWrapper>
        {!isChecked && (
          <ul
            ref={ulRef} // ul 요소에 ref 연결
            style={{ transform: `rotate(${finalAngle}deg)` }}
            onTouchStart={handleTouchStart}
            onTouchMove={handleTouchMove}
            onTouchEnd={handleTouchEnd}
            onClick={handleCheckbox}
          >
            <li>
              <Link to="/">
                <div className="flex flex-col">
                  <img src="/img/Home.svg" alt="Home" className="p-2" />
                  <span>홈</span>
                </div>
              </Link>
            </li>
            <li>
              {isARMode ? (
                <div className="flex flex-col" onClick={handleARButtonClick}>
                  <img src="/img/AR.svg" alt="Home" className="p-2" />
                  <span>AR모드</span>
                </div>
              ) : (
                <div className="flex flex-col" onClick={handleARButtonClick}>
                  <img src="/img/AR.svg" alt="Home" className="p-2" />
                  <span>3D모드</span>
                </div>
              )}
            </li>
            <li>
              <Link to="/shop">
                <div className="flex flex-col">
                  <img src="/img/Shop.svg" alt="Home" className="p-2" />
                  <span>구매</span>
                </div>
              </Link>
            </li>
            <li>
              <Link to="/event">
                <div className="flex flex-col">
                  <img src="/img/Event.svg" alt="Home" className="p-2" />
                  <span>이벤트</span>
                </div>
              </Link>
            </li>
            <li>
              <Link to="/myStar/1">
                <div className="flex flex-col">
                  <img
                    src="/img/Constellation.svg"
                    alt="Home"
                    className="p-2"
                  />
                  <span>My별자리</span>
                </div>
              </Link>
            </li>
            <li>
              <Link to="/starMark/1">
                <div className="flex flex-col">
                  <img src="/img/Starmark.svg" alt="Home" className="p-2" />
                  <span>별마크</span>
                </div>
              </Link>
            </li>
          </ul>
        )}

        {isChecked && (
          <section className="m-5">
            <input id="nav_btn" type="checkbox" />
            <label htmlFor="nav_btn" onClick={handleCheckbox}></label>
          </section>
        )}
      </CheckboxWrapper>
    </FixedContainer>
  );
};

export default NavBar;
