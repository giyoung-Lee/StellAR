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
  padding-bottom: 5vh;
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
  font-size: 25px;
`;

export const CardHeaderName = styled.p`
  color: white;
  font-size: 17px;
  font-weight: 100;
  padding-bottom: 5px;
  border-bottom: 1px solid white;
`;

export const ScienceInfo = styled.div`
  width: 100%;
  height: 80%;
  overflow-y: auto;
  color: white;
  font-size: 18px;
  font-weight: 100;
  align-self: start;
  margin: 15px 0;
  padding: 0 5px;
  p {
    margin-bottom: 20px;
  }
  p:nth-child(3) {
    margin-bottom: 0;
    line-height: 1.5;
  }
`;

export const Mythology = styled.div`
  width: 100%;
  margin: 10px 0;
  padding: 0 5px;
  height: 87%;
  font-size: 18px;
  font-weight: 100;
  color: white;
  overflow-y: auto;
  line-height: 1.5;
`;
