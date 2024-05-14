import * as o from './style/OrderPageStyle';
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
        <Route path="/:result/*" element={<OrderResult />} />
      </Routes>
      {orderStore.isModalOpen && <ShippingModal />}
    </o.Wrapper>
  );
};

export default OrderPage;
