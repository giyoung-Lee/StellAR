import React, { useEffect, useState } from 'react';
import * as o from '../style/OrderStyle';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import Lottie from 'lottie-react';
import errorLottie from '../../assets/lottie/error.json';
import successLottie from '../../assets/lottie/success.json';
import usePaymentStore from '../../stores/paymentStore';
import { PostPaymentSuccess } from '../../apis/PaymentApis';
import useUserStore from '../../stores/userStore';
import { useMutation, useQuery } from '@tanstack/react-query';

const OrderResult = () => {
  const paymentStore = usePaymentStore();
  const userStore = useUserStore();
  const navigate = useNavigate();
  const { result } = useParams();
  const location = useLocation();
  const pgToken = location.search.split('=')[1];

  useEffect(() => {
    if (pgToken) {
      paymentStore.setPgToken(pgToken);
    }
  }, [pgToken]);

  const handleSuccess = () => {
    mutate({
      tid: paymentStore.tid,
      pgToken: pgToken,
      partnerOrderId: '1001',
      partnerUserId: userStore.userId,
    });
    paymentStore.setTid('');
    paymentStore.setPgToken('');
    navigate('/');
  };

  const { mutate } = useMutation({
    mutationFn: PostPaymentSuccess,
    onSuccess(result: any) {
      console.log(result);
    },
  });

  return (
    <>
      <o.ResultWrapper>
        {result == 'success' ? (
          <div className="card">
            <h2>주문 완료 !</h2>
            <o.LottieGif>
              <Lottie animationData={successLottie} />
            </o.LottieGif>
            <button onClick={handleSuccess}>홈으로 가기</button>
          </div>
        ) : (
          <div className="card">
            <h2>Error</h2>
            <o.LottieGif>
              <Lottie animationData={errorLottie} />
            </o.LottieGif>
            <button onClick={() => navigate(-1)}>‹ 뒤로가기</button>
          </div>
        )}
      </o.ResultWrapper>
    </>
  );
};

export default OrderResult;
