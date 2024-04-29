import styled from 'styled-components';

export const Header = styled.div`
  width: 100vw;
  padding-top: 1vh;
  position: sticky;
  top: 0;
`;

export const Title = styled.div`
  height: 9vh;
  display: flex;
  align-items: center;
  justify-content: center;
  color: var(--color-orange);
  font-size: 23px;
  background: linear-gradient(
    90deg,
    rgba(255, 255, 255, 0) 0%,
    rgba(102, 224, 255, 0.20220588235294112) 40%,
    rgba(102, 224, 255, 0.20220588235294112) 60%,
    rgba(255, 255, 255, 0) 100%
  );
`;
