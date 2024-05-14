import { useMutation } from '@tanstack/react-query';
import * as o from '../style/OrderStyle';
import kakaopay from '/img/kakaopay.png';
import { PostPaymentReady } from '../../apis/PaymentApis';
import useUserStore from '../../stores/userStore';
import { useState } from 'react';
import usePaymentStore from '../../stores/paymentStore';
import useOrderStore from '../../stores/orderStore';
import Swal from 'sweetalert2';

const PayInfo = () => {
  const userStore = useUserStore();
  const paymentStore = usePaymentStore();
  const orderStore = useOrderStore();

  const readyPayment = () => {
    console.log('결제해야징');
    if (
      !paymentStore.qty ||
      !paymentStore.recipient ||
      !paymentStore.addressPost ||
      !paymentStore.addressDetail ||
      !orderStore.address.postcode
    ) {
      Swal.fire({
        text: '주문 내역을 확인하세요!',
        icon: 'error',
        confirmButtonText: '확인',
        width: 300,
      });
      return;
    }
    mutate({
      userId: userStore.userId,
      amount: paymentStore.qty,
      productId: 1001,
    });
  };

  const { mutate } = useMutation({
    mutationFn: PostPaymentReady,
    onSuccess(result: any) {
      console.log(result.tid);
      window.open(result.next_redirect_pc_url);
      paymentStore.setTid(result.tid);
    },
  });
  return (
    <o.PayInfoSec>
      <o.TItle>결제수단</o.TItle>
      <o.Content>
        <div className="payment">
          카카오페이
          <img src={kakaopay} alt="kakaopay" />
        </div>
      </o.Content>
      <a href=""></a>
      <o.PayBtn onClick={readyPayment}>
        <div className="container">
          <div className="left-side">
            <div className="card">
              <div className="card-line"></div>
              <div className="buttons"></div>
            </div>
            <div className="post">
              <div className="post-line"></div>
              <div className="screen">
                <div className="dollar">$</div>
              </div>
              <div className="numbers"></div>
              <div className="numbers-line2"></div>
            </div>
          </div>
          <div className="right-side">
            <div className="new">결제하기</div>
            <svg
              viewBox="0 0 451.846 451.847"
              height="512"
              width="512"
              xmlns="http://www.w3.org/2000/svg"
              className="arrow"
            >
              <path
                fill="#cfcfcf"
                data-old_color="#000000"
                className="active-path"
                data-original="#000000"
                d="M345.441 248.292L151.154 442.573c-12.359 12.365-32.397 12.365-44.75 0-12.354-12.354-12.354-32.391 0-44.744L278.318 225.92 106.409 54.017c-12.354-12.359-12.354-32.394 0-44.748 12.354-12.359 32.391-12.359 44.75 0l194.287 194.284c6.177 6.18 9.262 14.271 9.262 22.366 0 8.099-3.091 16.196-9.267 22.373z"
              ></path>
            </svg>
          </div>
        </div>
      </o.PayBtn>
    </o.PayInfoSec>
  );
};

export default PayInfo;
