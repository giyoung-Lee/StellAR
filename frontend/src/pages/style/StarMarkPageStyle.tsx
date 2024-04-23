import styled from 'styled-components';

export const Wrapper = styled.div<{ $background: string }>`
  height: 100vh;
  background-image: url(${(props) => props.$background});
  background-size: cover;
  position: relative;
  display: flex;
  flex-direction: column;
`;
