import styled from 'styled-components';

export const Wrapper = styled.div`
  height: 60vh;
  width: 100vw;
  position: relative;
  display: flex;
  align-self: center;
  justify-content: center;
  align-items: center;
  overflow: hidden;
`;

export const Carousel = styled.div`
  width: 100%;
  height: 90%;
  display: flex;
  align-items: center;
  justify-content: center;
  perspective: 1200px;
  transform-style: preserve-3d;
  transition: all 0.5s;
`;

export const CarouselItem = styled.div`
  background-color: #ffffff27;
  border-radius: 11px;
  border: 1px solid #ffffff74;
  width: 80%;
  height: 100%;
  position: absolute;
  transition: all 0.5s;
  display: flex;
  align-items: center;
  justify-content: center;
`;

export const CardWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 90%;
  width: 85%;
`;

export const CardTitle = styled.p`
  color: white;
  font-size: 150%;
  padding-bottom: 5%;
`;

export const CardImage = styled.img`
  width: 90%;
  height: 90%;
  object-fit: cover;
`;

export const CardHeader = styled.div`
  width: 100%;
  min-height: 10%;
  color: var(--color-orange);
  font-size: 150%;
`;

export const CardHeaderName = styled.p`
  color: white;
  font-size: 1.2rem;
  font-weight: 100;
  padding: 7px 0;
  border-bottom: 1px solid white;
`;

export const ScienceInfo = styled.div`
  width: 100%;
  /* height: 80%; */
  color: white;
  font-size: 110%;
  font-weight: 100;
  padding: 20px 0;
  align-self: start;
  p {
    margin-top: 20px;
    line-height: 1.5;
  }
`;

export const Mythology = styled.div`
  width: 100%;
  padding: 10px 5px;
  height: 87%;
  font-size: 110%;
  font-weight: 100;
  color: white;
  overflow-y: scroll;
  line-height: 1.5;
`;

