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
  width: 30vw;
  height: 85vh;
  overflow: hidden;
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
  margin-bottom: 10%;
`;

export const ServiceInfo = styled.div`
  /* background-color: pink; */
  min-width: 40%;
  p {
    color: var(--color-orange);
  }

  .cards {
    display: flex;
    flex-direction: column;
    gap: 15px;
  }

  .cards .card {
    display: flex;
    align-items: center;
    /* justify-content: center; */
    flex-direction: column;
    /* text-align: center; */
    height: 10%;
    width: 100%;
    /* border-radius: 10px; */
    color: white;
    cursor: pointer;
    transition: 400ms;
  }

  .cards .card p.tip {
    font-size: 1em;
    /* font-weight: 700; */
  }

  .cards .card p.second-text {
    font-size: 0.7em;
  }

  .cards .card:hover {
    transform: scale(1.1, 1.1);
  }

  .cards:hover > .card:not(:hover) {
    filter: blur(10px);
    transform: scale(0.9, 0.9);
  }
`;

export const GoApp = styled.span`
  margin-top: 2%;
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
    background-color: #0a0a0a;
    border-radius: 5px;
    color: #0f0f0f;
    font-weight: 700;
    transition: 300ms;
    outline: #0f0f0f 2px solid;
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
      background-color: rgba(88, 101, 242, 1);
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
    background-color: #0a0a0a;
    border-radius: 10px;
    z-index: 2;
  }

  .light-button button.bt .light-holder .light {
    position: absolute;
    top: 0;
    width: 200px;
    height: 200px;
    clip-path: polygon(50% 0%, 25% 100%, 75% 100%);
    background: transparent;
  }

  .light-button button.bt:hover .button-holder img {
    opacity: 1;
    border-radius: 5px;
  }

  .light-button button.bt:hover .button-holder {
    color: rgba(88, 101, 242, 1);
    outline: rgba(88, 101, 242, 1) 2px solid;
    /* outline-offset: 2px; */
    overflow: hidden;
  }

  .light-button button.bt:hover .light-holder .light {
    background: rgb(255, 255, 255);
    background: linear-gradient(
      180deg,
      rgba(88, 101, 242, 1) 0%,
      rgba(255, 255, 255, 0) 75%,
      rgba(255, 255, 255, 0) 100%
    );
  }
`;

