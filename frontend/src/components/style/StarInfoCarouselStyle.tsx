import styled from 'styled-components';

export const Wrapper = styled.div`
  height: 60vh;
  width: 100vw;
  position: absolute;
  z-index: 1000;
  /* position: relative; */
  display: flex;
  align-self: center;
  justify-content: center;
  align-items: center;
  overflow: hidden;

  .arrow {
    height: 100px;
    position: absolute;
    z-index: 1001;

    cursor: pointer;

    &.prev {
      left: 10%;
      transform: rotate(180deg);
    }

    &.next {
      right: 10%;
    }
  }

  @media (max-width: 576px) {
    .arrow {
      display: none;
    }
  }
  @media (min-width: 576px) {
    .arrow {
      display: inline-block;
    }
  }
`;

export const Carousel = styled.div`
  width: 400px;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  perspective: 1200px;
  transform-style: preserve-3d;
  transition: all 0.5s;
`;

export const CarouselItem = styled.div`
  background-color: #373737e7;
  border-radius: 11px;
  border: 1px solid #ffffff74;
  width: 80%;
  height: 100%;
  position: absolute;
  transition: all 0.5s;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
`;

export const CardWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 90%;
  width: 85%;
  /* padding-bottom: 5vh; */
`;

export const CardTitle = styled.p`
  color: white;
  font-size: 150%;
  padding-bottom: 5%;
`;

export const ImageWrapper = styled.div`
  width: 100%;
  height: 100%;
  overflow: hidden;
  border-radius: 11px;
`;

export const CardImage = styled.img`
  object-fit: cover;
  height: 100%;
  width: 100%;
  filter: grayscale(50%);
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
    line-height: 1.7;
  }
`;

export const QuizBox = styled.div`
  border: 1.5px dashed grey;
  padding: 20px 10px;
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  .q {
    text-align: center;
    padding-bottom: 10px;
  }
  .answer_box {
    display: flex;
    justify-content: space-around;

    button {
      width: 50px;
      height: 50px;
      position: relative;
      display: flex;
      align-items: center;
      justify-content: center;
      border-radius: 20px;
      cursor: pointer;
      transition-duration: 0.3s;
      box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.13);
      border: none;
      &.o {
        background-color: #6fae6f;
      }
      &.x {
        background-color: #c13f3f;
      }
    }

    svg {
      height: 25px !important;
    }

    button:active {
      transform: scale(0.8);
    }
  }
`;

export const Mythology = styled.div`
  width: 100%;
  margin: 10px 0;
  padding: 0 5px;
  font-size: 18px;
  font-weight: 100;
  color: white;
  overflow-y: auto;
  line-height: 1.7;
`;

