import styled from 'styled-components';

export const Coner = styled.p`
  position: absolute;
  top: -30px;
  right: -30px;
  width: 60px;
  background-color: #ebb77b78;
  aspect-ratio: 1;
  border-radius: 50%;
`;

export const EventInfo = styled.div`
  position: absolute;
  width: 100%;
  top: 20%;
  left: 5%;
  height: 55%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
`;

export const EventDate = styled.p`
  color: var(--color-light);
  font-size: 14px;
`;

export const EventTitle = styled.p`
  color: lightgrey;
`;
