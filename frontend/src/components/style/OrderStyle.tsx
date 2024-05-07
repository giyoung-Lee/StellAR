import styled from 'styled-components';

export const HeaderContent = styled.div`
  border-bottom: 2px solid #b7b7b7;
  font-size: 1.15rem;
  font-weight: 700;
  padding-bottom: 10px;
  margin-bottom: 20px;
`;

export const TItle = styled.div`
  margin-bottom: 5px;
`;

export const Content = styled.div`
  background-color: #797979a6;
  padding: 10px;
  position: relative;
  font-size: 0.9rem;
  .ordered_item {
    font-size: 0.9rem;
  }
  .ordered_qt {
    display: flex;
    margin-top: 10px;
    height: 10%;
  }
  .set_qt {
    margin-left: 10px;
    display: flex;
    button {
      background-color: #a8a8a8;
      width: 20px;
      aspect-ratio: 1;
    }
    p {
      background-color: white;
      color: black;
      /* padding: 5px; */
      padding: 0 7px;
    }
  }
  .total_price {
    position: absolute;
    right: 10px;
    bottom: 10px;
  }

  input {
    color: black;
  }
  .address_info {
    input,
    textarea {
      width: 100%;
      font-size: 0.9rem;
      color: black;
      margin-left: 20px;
      background-color: transparent;
      border-width: 0 0 1px;
      &.name {
        width: 50%;
      }
      &:focus {
        outline: none;
      }
    }
    button {
      margin: 0 5px;
      background-color: var(--color-orange);
      /* padding: 3px; */
      width: 25px;
      border-radius: 50%;
      svg {
        height: 13px;
      }
    }
    .address {
      margin-top: 10px;
      label {
        min-width: max-content;
      }
      display: flex;
      /* justify-content: space-between; */
      .input_container {
        display: flex;
        flex-direction: column;
        width: 80%;
      }
    }
  }
`;

export const OrderInfoSec = styled.div`
  min-height: 20%;
`;

export const ShippingInfoSec = styled.div`
  margin-top: 10%;
  min-height: 20%;
`;

export const PayInfoSec = styled.div`
  margin-top: 10%;
  min-height: 15%;
`;

export const PayBtn = styled.div`
  height: 50px;
  margin-top: 5%;

  .container {
    background-color: #ffffff;
    display: flex;
    flex-direction: row;
    width: 100%;
    height: 100%;
    position: relative;
    border-radius: 6px;
    transition: 0.3s ease-in-out;
    .new {
      color: var(--color-navy);
      font-style: normal;
    }
  }

  .container:hover {
    transform: scale(1.03);
    width: 220px;
  }

  .container:hover .left-side {
    width: 110%;
  }

  .left-side {
    background-color: #5de2a3;
    width: 130px;
    height: 100%;
    border-radius: 4px;
    position: relative;
    display: flex;
    justify-content: center;
    align-items: center;
    cursor: pointer;
    transition: 0.3s;
    flex-shrink: 0;
    overflow: hidden;
  }

  .right-side {
    width: calc(100% - 130px);
    display: flex;
    align-items: center;
    overflow: hidden;
    cursor: pointer;
    justify-content: space-between;
    white-space: nowrap;
    transition: 0.3s;
  }

  .right-side:hover {
    background-color: #f9f7f9;
  }

  .arrow {
    width: 20px;
    height: 20px;
    margin-right: 20px;
  }

  .card {
    width: 60px;
    height: 40px;
    background-color: #c7ffbc;
    border-radius: 6px;
    position: absolute;
    display: flex;
    z-index: 10;
    flex-direction: column;
    align-items: center;
    -webkit-box-shadow: 9px 9px 9px -2px rgba(77, 200, 143, 0.72);
    -moz-box-shadow: 9px 9px 9px -2px rgba(77, 200, 143, 0.72);
    -webkit-box-shadow: 9px 9px 9px -2px rgba(77, 200, 143, 0.72);
  }

  .card-line {
    width: 55px;
    height: 10px;
    background-color: #80ea69;
    border-radius: 2px;
    margin-top: 7px;
  }

  @media only screen and (max-width: 480px) {
    .container {
      /* transform: scale(0.7); */
    }

    .container:hover {
      transform: scale(0.9);
    }

    .new {
      /* font-size: 18px; */
    }
  }

  .buttons {
    width: 6px;
    height: 6px;
    background-color: #379e1f;
    box-shadow:
      0 -10px 0 0 #26850e,
      0 10px 0 0 #56be3e;
    border-radius: 50%;
    margin-top: 5px;
    transform: rotate(90deg);
    margin: 10px 0 0 -30px;
  }

  .container:hover .card {
    animation: slide-top 1.2s cubic-bezier(0.645, 0.045, 0.355, 1) both;
  }

  .container:hover .post {
    animation: slide-post 1s cubic-bezier(0.165, 0.84, 0.44, 1) both;
  }

  @keyframes slide-top {
    0% {
      -webkit-transform: translateY(0);
      transform: translateY(0);
    }

    50% {
      -webkit-transform: translateY(-70px) rotate(90deg);
      transform: translateY(-70px) rotate(90deg);
    }

    60% {
      -webkit-transform: translateY(-70px) rotate(90deg);
      transform: translateY(-70px) rotate(90deg);
    }

    100% {
      -webkit-transform: translateY(-23px) rotate(90deg);
      transform: translateY(-23px) rotate(90deg);
    }
  }

  .post {
    width: 63px;
    height: 75px;
    background-color: #dddde0;
    position: absolute;
    z-index: 11;
    bottom: 10px;
    top: 120px;
    border-radius: 6px;
    overflow: hidden;
  }

  .post-line {
    width: 47px;
    height: 9px;
    background-color: #545354;
    position: absolute;
    border-radius: 0px 0px 3px 3px;
    right: 8px;
    top: 8px;
  }

  .post-line:before {
    content: '';
    position: absolute;
    width: 47px;
    height: 9px;
    background-color: #757375;
    top: -8px;
  }

  .screen {
    width: 47px;
    height: 23px;
    background-color: #ffffff;
    position: absolute;
    top: 22px;
    right: 8px;
    border-radius: 3px;
  }

  .numbers {
    width: 12px;
    height: 12px;
    background-color: #838183;
    box-shadow:
      0 -18px 0 0 #838183,
      0 18px 0 0 #838183;
    border-radius: 2px;
    position: absolute;
    transform: rotate(90deg);
    left: 25px;
    top: 52px;
  }

  .numbers-line2 {
    width: 12px;
    height: 12px;
    background-color: #aaa9ab;
    box-shadow:
      0 -18px 0 0 #aaa9ab,
      0 18px 0 0 #aaa9ab;
    border-radius: 2px;
    position: absolute;
    transform: rotate(90deg);
    left: 25px;
    top: 68px;
  }

  @keyframes slide-post {
    50% {
      -webkit-transform: translateY(0);
      transform: translateY(0);
    }

    100% {
      -webkit-transform: translateY(-93px);
      transform: translateY(-93px);
    }
  }

  .dollar {
    position: absolute;
    font-size: 16px;
    font-family: 'Lexend Deca', sans-serif;
    width: 100%;
    left: 0;
    top: 0;
    color: #4b953b;
    text-align: center;
  }

  .container:hover .dollar {
    animation: fade-in-fwd 0.3s 1s backwards;
  }

  @keyframes fade-in-fwd {
    0% {
      opacity: 0;
      transform: translateY(-5px);
    }

    100% {
      opacity: 1;
      transform: translateY(0);
    }
  }
`;
export const ModalWrapper = styled.div`
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 90%;
  min-height: 50%;
  background-color: var(--color-navy);
  padding: 20px 10px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

export const ModalHeader = styled.p`
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 10px;
  button {
    font-size: 0.9rem;
    border-radius: 10px;
    text-align: center;
    margin-right: 5px;
    svg {
      height: 1.2rem;
    }
    path {
      color: var(--color-orange);
    }
  }
`;
