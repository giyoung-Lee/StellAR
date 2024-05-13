import React, { useEffect, useState } from 'react';
import * as o from '../style/OrderStyle';

const OrderInfo = () => {
  const [qt, setQt] = useState(0);
  const up = () => setQt(qt + 1);
  const down = () => setQt(qt - 1);
  useEffect(() => {
    if (qt < 0) {
      setQt(0);
      //   alert('수량이 0보다 작을 수 없습니다.');
    }
  }, [qt]);
  return (
    <o.OrderInfoSec>
      <o.TItle>주문 상품</o.TItle>
      <o.Content>
        <p className="ordered_item">Galaxy 스마트 별자리 빔프로젝터</p>
        <div className="ordered_qt">
          <span>수량</span>
          <span className="set_qt">
            <button onClick={down}>-</button>
            <p>{qt}</p>
            <button onClick={up}>+</button>
          </span>
        </div>
        <span className="total_price">
          {(2000000 * qt).toLocaleString('ko-KR')}
        </span>
      </o.Content>
    </o.OrderInfoSec>
  );
};

export default OrderInfo;
