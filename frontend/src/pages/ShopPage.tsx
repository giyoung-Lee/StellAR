import React, { useEffect } from 'react';
import * as s from './style/ShopPageStyle';
import GiftCard from '../components/Shop/GiftCard';
import usePaymentStore from '../stores/paymentStore';
import useOrderStore from '../stores/orderStore';

const ShopPage = () => {
  const { setQty, setRecipient, setAddressPost, setaddressDetail } =
    usePaymentStore();
  const { setAddress } = useOrderStore();

  useEffect(() => {
    setQty(0);
    setRecipient('');
    setAddressPost('');
    setaddressDetail('');
    setAddress({
      postcode: '',
      address: '',
      extraAddress: '',
    });
  }, []);

  return (
    <s.Wrapper>
      <GiftCard />
    </s.Wrapper>
  );
};

export default ShopPage;
