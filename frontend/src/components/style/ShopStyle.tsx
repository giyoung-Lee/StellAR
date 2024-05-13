import styled from 'styled-components';

export const CardWrapper = styled.div`
  /* background-color: lightskyblue; */
  width: 300px;
  height: 400px;
  @media (min-width: 574px) {
    width: 400px;
    height: 500px;
    .info {
      li {
        font-size: 20px;
      }
    }
    .btn {
      font-size: 20px;
    }
  }

  .card {
    width: 100%;
    height: 100%;
    background: #313131;
    border-radius: 20px;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    transition: 0.2s ease-in-out;
    position: relative;
    box-shadow: 0px 3px 10px rgba(0, 0, 0, 0.811);
    overflow: hidden;
  }

  .frontBox {
    position: absolute;
    transition: 0.2s ease-in-out;
    z-index: 1;
    display: flex;
    flex-direction: column;
    align-items: center;
    p {
      color: #cccccc;
    }
  }

  .card_title {
    font-size: 1.5rem;
    width: 90%;
  }

  .card_content {
    font-size: 1.2rem;
    width: 90%;
  }

  .img {
    aspect-ratio: 1.3;
    object-fit: cover;
    margin: 5% 0;
  }

  .textBox {
    opacity: 0;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    gap: 15px;
    transition: 0.2s ease-in-out;
    z-index: 2;
    height: 90%;
    width: 90%;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
  }

  .info {
    li {
      margin-bottom: 20px;
    }
  }

  .btn {
    display: flex;
    background-color: red;
    justify-content: center;
    align-items: center;
    width: 120%;
    height: 10%;
    position: absolute;
    bottom: 0;
    color: var(--color-navy);
    background-color: #eee;
    cursor: pointer;
    transition: all 300ms ease-in-out;
    &:hover {
      font-size: 21.5px;
    }
  }

  .textBox > .text {
    font-weight: bold;
  }

  .textBox > .head {
    font-size: 20px;
  }

  .textBox > .price {
    font-size: 17px;
    margin-bottom: 10%;
  }

  .textBox > span {
    font-size: 12px;
    color: lightgrey;
  }

  .card:hover > .textBox {
    opacity: 1;
  }

  .card:hover > .frontBox {
    /* height: 90%; */
    /* scale: 0.8; */
    filter: blur(10px);
    opacity: 0.4;
    animation: anim 3s infinite;
  }

  @keyframes anim {
    0% {
      transform: translateY(0);
    }

    50% {
      transform: translateY(-20px);
    }

    100% {
      transform: translateY(0);
    }
  }

  .card:hover {
    transform: scale(1.02);
  }
`;
