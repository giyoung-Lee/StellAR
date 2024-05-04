import styled from 'styled-components';

export const Container = styled.div`
  display: flex;
  min-height: 100vh;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background-color: white;
`;

export const Wrapper = styled.div`
  /* background-color: #f4f4f4; */
  background-color: var(--color-dark);
  border-radius: 20px;
  width: 80vw;
  height: 90vh;
  box-shadow: 0 3px 3px rgba(0, 0, 0, 0.2);
  display: grid;
  grid-template-columns: 1fr 1fr;
`;

export const LeftSide = styled.div`
  margin: 20px;
  margin-right: 0;
`;

export const RightSide = styled.div`
  /* opacity: 0.5; */
  margin: 20px;
  margin-left: 0;
`;

