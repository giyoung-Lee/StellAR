import React, { useEffect, useRef, useState } from 'react';
import * as o from '../style/OrderStyle';
import useOrderStore from '../../stores/orderStore';
import '../../pages/style/Fontawsome';
import usePaymentStore from '../../stores/paymentStore';

const ShippingInfo = () => {
  const orderStore = useOrderStore();
  const {
    recipient,
    setRecipient,
    setAddressPost,
    addressDetail,
    setaddressDetail,
  } = usePaymentStore();

  const textareaRef = useRef<HTMLTextAreaElement | null>(null);

  const handleName = (e: React.ChangeEvent<HTMLInputElement>) => {
    setRecipient(e.target.value);
  };

  const handleDetialAddress = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setaddressDetail(e.target.value);
    // adjustTextareaHeight();
  };

  useEffect(() => {
    setaddressDetail(orderStore.address.extraAddress);
  }, [orderStore]);

  return (
    <o.ShippingInfoSec>
      <o.TItle>배송 정보</o.TItle>
      <o.Content>
        <form action="" className="address_info">
          <div className="name">
            <label htmlFor="">받는 사람</label>
            <input
              className="name"
              type="text"
              defaultValue={recipient}
              onChange={handleName}
            />
          </div>
          <div className="address">
            <label htmlFor="">주소</label>
            <div className="input_container">
              <input
                className="search-address"
                onClick={() => orderStore.setIsModalOpen(true)}
                type="text"
                defaultValue={orderStore.address.postcode}
              />
              <input type="text" defaultValue={orderStore.address.address} />
              <textarea
                ref={textareaRef}
                value={addressDetail}
                onChange={handleDetialAddress}
                rows={2}
              />
            </div>
          </div>
        </form>
      </o.Content>
    </o.ShippingInfoSec>
  );
};

export default ShippingInfo;
