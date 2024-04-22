import styled from 'styled-components';

export const Wrapper = styled.div`
  height: 50vh;
  width: 100vw;
  position: relative;
  display: flex;
  align-self: center;
  justify-content: center;
  align-items: center;
  overflow: hidden;
`;

export const BtnWrapper = styled.div`
  position: absolute;
  top: 0;
`;

export const Btn = styled.button``;

export const Carousel = styled.div`
  width: 400px;
  height: 400px;
  display: flex;
  align-items: center;
  justify-content: center;
  perspective: 1200px;
  transform-style: preserve-3d;
  transition: all 0.5s;
  /* background-color: blue; */
`;

export const CarouselItem = styled.div`
  background-color: lightslategray;
  border: 1px solid black;
  width: 80%;
  height: 100%;
  position: absolute;
  transition: all 0.5s;
  display: flex;
  align-items: center;
  justify-content: center;
`;
