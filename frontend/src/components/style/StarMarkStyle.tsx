import styled from 'styled-components';

export const StarInfo = styled.p`
  position: absolute;
  width: 100%;
  top: 20%;
  left: 5%;
  height: 55%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
`;

export const StarName = styled.p`
  color: var(--color-orange);
  font-size: 17px;
`;

export const Date = styled.p`
  color: var(--color-light);
  font-size: 13px;
`;

export const HelpContent = styled.p`
  color: var(--color-light);
  font-size: 17px;
`;

export const Star = styled.span`
  position: absolute;
  right: 10%;
  .container input {
    position: absolute;
    opacity: 0;
    cursor: pointer;
    height: 0;
    width: 0;
  }

  .container {
    display: block;
    position: relative;
    cursor: pointer;
    user-select: none;
  }

  .container svg {
    position: relative;
    top: 0;
    left: 0;
    height: 30px;
    width: 30px;
    transition: all 0.3s;
    fill: #666;
  }

  .container svg:hover {
    transform: scale(1.1);
  }

  .container input:checked ~ svg {
    fill: #ffeb49;
  }
`;
