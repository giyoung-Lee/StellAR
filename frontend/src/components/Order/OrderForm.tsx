import Header from './Header';
import OrderInfo from './OrderInfo';
import ShippingInfo from './ShippingInfo';
import PayInfo from './PayInfo';

import * as o from '../../pages/style/OrderPageStyle';

const OrderForm = () => {
  return (
    <>
      <o.Container className="form-container">
        <Header />
        <OrderInfo />
        <ShippingInfo />
        <PayInfo />
      </o.Container>
    </>
  );
};

export default OrderForm;
