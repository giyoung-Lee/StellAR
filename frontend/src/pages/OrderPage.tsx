import React from 'react';
import * as o from './style/OrderPageStyle';
import Header from '../components/Order/Header';
import OrderInfo from '../components/Order/OrderInfo';
import ShippingInfo from '../components/Order/ShippingInfo';
import PayInfo from '../components/Order/PayInfo';
import ShippingModal from '../components/Order/ShippingModal';
import useOrderStore from '../stores/orderStore';

const OrderPage = () => {
  const orderStore = useOrderStore();
  return (
    <o.Wrapper>
      <o.Container className={orderStore.isModalOpen ? 'blur' : ''}>
        <Header />
        <OrderInfo />
        <ShippingInfo />
        <PayInfo />
      </o.Container>
      {orderStore.isModalOpen && <ShippingModal />}
    </o.Wrapper>
  );
};

export default OrderPage;
