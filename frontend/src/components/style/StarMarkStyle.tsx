import styled from 'styled-components';

export const StarInfo = styled.div`
  position: relative;
  /* width: 100%; */
  top: 20%;
  left: 5%;
  height: 60%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
`;

export const NameBox = styled.p``;

export const StarName = styled.span`
  font-size: 13px;
  margin-left: 10px;
`;

export const BookMarkName = styled.span`
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
  z-index: 1000;
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

export const BtnWrapper = styled.div`
  /* position: absolute;
  z-index: 1000;
  top: 20%;
  right: 0; */
`;

export const ToggleBtn = styled.button`
  color: var(--color-navy);
  font-size: 0.9rem;
  path {
    color: #ffd76a !important;
  }
`;

export const MarkNameInput = styled.input`
  background-color: var(--color-lightorange);
  color: var(--color-navy);
  font-size: 0.9rem;
  padding: 3px 5px;
  border-radius: 3px;
  box-shadow: inset 0 0 5px #a0774e68;
  text-align: center;
  &:focus-visible {
    outline: none;
  }
`;

export const BtnBox = styled.p`
  /* background-color: red; */
  position: relative;
  margin-top: 3px;
`;

export const BackBtn = styled.button`
  /* background-color: blue; */
  position: absolute;
  left: 0;
`;
export const SaveBtn = styled.button`
  /* background-color: gold; */
  font-size: 0.9rem;
  color: var(--color-dark);
`;
