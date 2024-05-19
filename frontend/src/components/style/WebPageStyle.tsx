import styled from 'styled-components';

export const RightWrapper = styled.div`
  width: 100%;
  height: 100%;
  /* background-color: tomato; */
  display: flex;
  justify-content: center;
  align-items: center;
`;

export const CanvasContainer = styled.div`
  width: 35vw;
  height: 85vh;
  /* overflow: hidden; */
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  .card {
    overflow: visible;
    width: 100%;
    height: 100%;
  }

  .content {
    width: 100%;
    height: 100%;
    transform-style: preserve-3d;
    transition: transform 300ms;
    box-shadow: 0px 0px 10px 1px #000000ee;
    border-radius: 5px;
  }

  .back {
    background-color: #151515;
    position: absolute;
    width: 100%;
    height: 100%;
    backface-visibility: hidden;
    -webkit-backface-visibility: hidden;
    border-radius: 5px;
    overflow: hidden;
  }

  .back {
    width: 100%;
    height: 100%;
    justify-content: center;
    display: flex;
    align-items: center;
    overflow: hidden;
  }

  .back::before {
    position: absolute;
    content: ' ';
    display: block;
    width: 300px;
    height: 160%;
    background: linear-gradient(
      90deg,
      transparent,
      #ebb67b,
      #ebb67b,
      #ebb67b,
      #ebb67b,
      transparent
    );
    animation: rotation_481 8000ms infinite linear;
  }

  .back-content {
    position: absolute;
    width: 99%;
    height: 99%;
    background-color: #151515;
    border-radius: 5px;
    color: white;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    gap: 30px;
  }

  @keyframes rotation_481 {
    0% {
      transform: rotateZ(0deg);
    }

    0% {
      transform: rotateZ(360deg);
    }
  }

  @keyframes floating {
    0% {
      transform: translateY(0px);
    }

    50% {
      transform: translateY(10px);
    }

    100% {
      transform: translateY(0px);
    }
  }
`;

export const LeftWrapper = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  overflow: hidden;
  position: relative;
`;

export const LogoImg = styled.img`
  width: 50%;
  /* margin-bottom: 10%; */
`;

export const LogoMessage = styled.p`
  color: var(--color-navy);
  width: 40%;
  font-size: 0.85rem;
  margin-top: 5px;
  text-align: end;
`;

export const GoApp = styled.span`
  margin-top: 5%;
  width: 20%;
  aspect-ratio: 1;
  .light-button button.bt {
    position: relative;
    height: 200px;
    display: flex;
    align-items: flex-end;
    outline: none;
    background: none;
    border: none;
    cursor: pointer;
  }

  .light-button button.bt .button-holder {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 100px;
    width: 100px;
    border-radius: 5px;
    font-weight: 700;
    transition: 200ms;
    /* outline: #0f0f0f 2px solid; */
    outline-offset: 20;
  }

  .light-button button.bt .button-holder img {
    transition: 200ms;
    opacity: 0;
    border-radius: 5px;
  }

  .light-button button.bt .light-holder {
    position: absolute;
    height: 200px;
    width: 100px;
    display: flex;
    flex-direction: column;
    align-items: center;
    p {
      width: 100px;
      z-index: 10;
      top: -10px;
      background-color: var(--color-orange);
      box-shadow: 0 3px 3px rgba(0, 0, 0, 0.2);
      /* background-color: rgba(88, 101, 242, 1); */
      color: var(--color-light);
      padding: 3px;
      border-radius: 5px;
    }
  }

  .light-button button.bt .light-holder .dot {
    position: absolute;
    top: 0;
    width: 10px;
    height: 10px;
    opacity: 0;
    border-radius: 10px;
    z-index: 2;
  }

  .light-button button.bt .light-holder .light {
    position: absolute;
    top: 0;
    width: 200px;
    height: 140px;
    clip-path: polygon(50% 0%, 25% 100%, 75% 100%);
    background: transparent;
  }

  .light-button button.bt:hover .button-holder img {
    opacity: 1;
    border-radius: 5px;
    z-index: 0;
  }

  .light-button button.bt:hover .button-holder {
    outline: var(--color-lightorange) 2px solid;
    /* outline-offset: 2px; */
    overflow: hidden;
  }

  .light-button button.bt:hover .light-holder .light {
    background: rgb(255, 255, 255);
    z-index: 1;
    background: linear-gradient(
      180deg,
      #ebb77b90 0%,
      #ebb77b3c 75%,
      #ebb77b1a 100%
    );
  }
`;

export const Preview = styled.p`
  color: var(--color-navy);
  position: absolute;
  bottom: 0;
  left: 0;
  button {
    position: relative;
    display: inline-block;
    cursor: pointer;
    outline: none;
    border: 0;
    vertical-align: middle;
    text-decoration: none;
    background: transparent;
    padding: 0;
    font-size: inherit;
    font-family: inherit;
  }

  button.learn-more {
    width: 12rem;
    height: auto;
  }

  button.learn-more .circle {
    transition: all 0.45s cubic-bezier(0.65, 0, 0.076, 1);
    position: relative;
    display: block;
    margin: 0;
    width: 40px;
    height: 40px;
    background: #f2cea2cf;
    border-radius: 1.625rem;
  }

  button.learn-more .circle .icon {
    transition: all 0.45s cubic-bezier(0.65, 0, 0.076, 1);
    position: absolute;
    top: 0;
    bottom: 0;
    margin: auto;
    background: #fff;
  }

  button.learn-more .circle .icon.arrow {
    transition: all 0.45s cubic-bezier(0.65, 0, 0.076, 1);
    left: 0.25rem;
    width: 1.125rem;
    height: 0.125rem;
    background: none;
  }

  button.learn-more .circle .icon.arrow::before {
    position: absolute;
    content: '';
    top: -0.29rem;
    right: 0.025rem;
    width: 0.625rem;
    height: 0.625rem;
    border-top: 0.125rem solid #fff;
    border-right: 0.125rem solid #fff;
    transform: rotate(45deg);
  }

  button.learn-more .button-text {
    transition: all 0.45s cubic-bezier(0.65, 0, 0.076, 1);
    position: absolute;
    display: flex;

    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    padding: 0.75rem 0;
    margin: 0 0 0 1.85rem;
    color: var(--color-navy);
    line-height: 1.6;
    text-align: center;
    justify-content: center;
    align-self: center;
  }

  button:hover .circle {
    width: 110%;
    background: var(--color-lightorange);
  }

  button:hover .circle .icon.arrow {
    transform: translate(1rem, 0);
  }

  button:hover .button-text {
    color: #fff;
    font-weight: 700;
  }
`;
