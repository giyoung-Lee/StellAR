import React from 'react';
import * as o from './style/OrderPageStyle';
import Header from '../components/Order/Header';
import OrderInfo from '../components/Order/OrderInfo';
import ShippingInfo from '../components/Order/ShippingInfo';
import PayInfo from '../components/Order/PayInfo';
import ShippingModal from '../components/Order/ShippingModal';
import useOrderStore from '../stores/orderStore';
import OrderForm from '../components/Order/OrderForm';
import { Route, Routes } from 'react-router-dom';
import OrderResult from '../components/Order/OrderResult';

const OrderPage = () => {
  const orderStore = useOrderStore();
  return (
    <o.Wrapper>
      <Routes>
        <Route path="/" element={<OrderForm />} />
      </Routes>
      <Routes>
        <Route path="error/*" element={<OrderResult />} />
      </Routes>
      <Routes>
        <Route path="success/*" element={<OrderResult />} />
      </Routes>
      {orderStore.isModalOpen && <ShippingModal />}
    </o.Wrapper>
  );
};

export default OrderPage;
