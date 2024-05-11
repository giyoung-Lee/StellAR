import styled from 'styled-components';

export const ConstellationInfo = styled.div`
  position: relative;
  display: flex;
  flex-direction: column;
`;

export const ConstellationName = styled.p`
  color: var(--color-orange);
  font-size: 1.1rem;
  margin-top: 5px;
  margin-bottom: 13px;
`;

export const ConstellationDes = styled.p``;

export const Date = styled.p`
  color: var(--color-light);
  font-size: 13px;
  text-align: end;
`;

export const Delete = styled.span`
  position: absolute;
  z-index: 1000;
  right: -3px;
  top: -3px;
  width: 10vw;
  aspect-ratio: 1;

  .bin-button {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    width: 100%;
    height: 100%;
    border-radius: 50%;
    background-color: rgba(255, 255, 255, 0.112);
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .bin-bottom {
    width: 12px;
    z-index: 2;
  }
  .bin-top {
    width: 14px;
    transform-origin: right;
    transition-duration: 0.3s;
    z-index: 2;
  }
  .bin-button:active {
    transform: scale(0.9);
  }
  .garbage {
    position: absolute;
    width: 11px;
    height: auto;
    z-index: 1;
    opacity: 0;
    transition: all 0.3s;
  }
`;

