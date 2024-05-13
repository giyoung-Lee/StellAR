import React from 'react';
import * as o from '../style/OrderStyle';
import { useNavigate } from 'react-router-dom';
import Lottie from 'lottie-react';
import errorLottie from '../../assets/lottie/error.json';
import successLottie from '../../assets/lottie/success.json';

const OrderResult = () => {
  const navigate = useNavigate();
  return (
    <>
      <o.ResultWrapper>
        <div className="card">
          <h2>Error</h2>
          <o.LottieGif>
            <Lottie animationData={errorLottie} />
          </o.LottieGif>
          <button onClick={() => navigate(-1)}>‹ 뒤로가기</button>

          {/* <h2>주문 완료</h2>
          <o.LottieGif>
            <Lottie animationData={successLottie} />
          </o.LottieGif>
          <button onClick={() => navigate(-1)}>‹ 뒤로가기</button> */}
        </div>
      </o.ResultWrapper>
    </>
  );
};

export default OrderResult;
