import React, { useEffect, useState } from 'react';
import * as o from '../style/OrderStyle';
import usePaymentStore from '../../stores/paymentStore';

const OrderInfo = () => {
  const { qty, setQty } = usePaymentStore();
  const up = () => setQty(qty + 1);
  const down = () => setQty(qty - 1);
  useEffect(() => {
    if (qty < 0) {
      setQty(0);
    }
  }, [qty]);
  return (
    <o.OrderInfoSec>
      <o.TItle>주문 상품</o.TItle>
      <o.Content>
        <p className="ordered_item">Galaxy 스마트 별자리 빔프로젝터</p>
        <div className="ordered_qt">
          <span>수량</span>
          <span className="set_qt">
            <button onClick={down}>-</button>
            <p>{qty}</p>
            <button onClick={up}>+</button>
          </span>
        </div>
        <span className="total_price">
          {(2000000 * qty).toLocaleString('ko-KR')}
        </span>
      </o.Content>
    </o.OrderInfoSec>
  );
};

export default OrderInfo;
