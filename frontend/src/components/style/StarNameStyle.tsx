import styled from 'styled-components';

export const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  position: absolute;
  top: 60%;
  left: 50%;
  background-color: #ebb77bd9;
  padding: 5px 12px;
  z-index: 1000;
  transform: translate(-50%, -60%);
  text-align: center;
  p {
    path {
      color: #ffd43b !important;
    }
  }
`;

export const BookMarkName = styled.span`
  color: var(--color-navy);
  /* font-weight: 700; */
  font-size: 0.9rem;
  margin-left: 5px;
`;
