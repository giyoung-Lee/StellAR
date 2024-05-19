import styled from 'styled-components';

export const Wrapper = styled.div`
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
  @media (min-width: 574px) {
    .form-container {
      width: 500px;
    }
  }
`;

export const Container = styled.div`
  width: 90%;
  min-height: max-content;
  /* height: 80%; */
  overflow-y: auto;
  padding: 20px;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  /* margin-top: 13%; */
  background-color: #72727261;
  border: 1px solid #b7b7b7;
`;
