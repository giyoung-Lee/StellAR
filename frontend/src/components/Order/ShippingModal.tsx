import * as o from '../style/OrderStyle';
import DaumPostcode from 'react-daum-postcode';
import useOrderStore from '../../stores/orderStore';

import '../../pages/style/Fontawsome';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import usePaymentStore from '../../stores/paymentStore';

const ShippingModal = () => {
  const orderStore = useOrderStore();
  const { setAddressPost } = usePaymentStore();

  const handleComplete = (data: any) => {
    // console.log(data);
    orderStore.setIsModalOpen(false);
    orderStore.setAddress({
      postcode: data.zonecode,
      address: data.address,
      extraAddress: data.buildingName,
    });
    setAddressPost(data.zonecode);
  };
  return (
    <o.ModalWrapper>
      <o.ModalHeader>
        <p className="modal_title">주소 검색</p>
        <button onClick={() => orderStore.setIsModalOpen(false)}>
          <FontAwesomeIcon icon="xmark" />
        </button>
      </o.ModalHeader>
      <DaumPostcode onComplete={handleComplete} />
    </o.ModalWrapper>
  );
};

export default ShippingModal;
