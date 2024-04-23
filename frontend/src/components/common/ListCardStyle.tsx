import styled from 'styled-components';

export const Wrapper = styled.div`
  width: 100%;
  margin-bottom: 5vh;
  padding-top: 2vh;
  position: relative;
  overflow-y: auto;
  display: flex;
  flex-direction: column;
  align-items: center;
`;

export const Card = styled.div`
  position: relative;
  min-height: 12vh;
  width: 90%;
  margin: 10px 0;
  background-color: #0a05159f;
  border: 1px solid #f1f4f4bd;
  border-radius: 10px;
`;

export const EventCard = styled.div`
  position: relative;
  min-height: 12vh;
  width: 90%;
  margin: 10px 0;
  background-color: #0a05159f;
  border: 1px solid #ebb77bbd;
  border-radius: 10px;
  overflow: hidden;
`;
