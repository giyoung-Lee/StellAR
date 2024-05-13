import styled from 'styled-components';

export const ConstellationInfo = styled.div`
  position: relative;
  display: flex;
  flex-direction: column;
  @media (min-width: 574px) {
    .constellation-name {
      font-size: 22px;
    }
    .constellation-des {
      font-size: 20px;
    }
    .date {
      font-size: 18px;
    }
    .delete {
      width: 40px;
    }
  }
`;

export const ConstellationName = styled.p`
  color: var(--color-orange);
  font-size: 1.1rem;
  margin-top: 5px;
  cursor: pointer;
  &:hover {
    color: #ebc798;
  }
`;

export const ConstellationDes = styled.p`
  margin: 13px 0;
`;

export const Date = styled.p`
  color: var(--color-light);
  font-size: 15px;
  text-align: end;
`;

export const Delete = styled.span`
  position: absolute;
  z-index: 1000;
  right: -3px;
  top: -3px;
  width: 35px;
  aspect-ratio: 1;

  .bin-button {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    width: 100%;
    height: 100%;
    border-radius: 50%;
    background-color: rgba(255, 255, 255, 0.142);
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
