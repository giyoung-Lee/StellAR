import * as s from '../style/ShopStyle';
import gift from '/img/gift.jpg';
import { useNavigate } from 'react-router-dom';
import useUserStore from '../../stores/userStore';
import { useQuery } from '@tanstack/react-query';
import { GetProductsApi } from '../../apis/PaymentApis';

const GiftCard = () => {
  const navigate = useNavigate();
  const userStore = useUserStore();

  const { data: eventsData } = useQuery({
    queryKey: ['get-products'],
    queryFn: () => GetProductsApi(),
  });

  return (
    <s.CardWrapper>
      <div className="card">
        <div className="frontBox">
          <p className="card_title">Galaxy</p>
          <img className="img" src={gift} />
          <p className="card_content">{eventsData?.data[0].productName}</p>
        </div>
        <div className="textBox">
          <p className="card_title">Galaxy</p>
          <ul className="info">
            <li>- {eventsData?.data[0].productName}</li>
            <li>- {eventsData?.data[0].desc}</li>
            <li>- 사이즈 {eventsData?.data[0].size}</li>
          </ul>
          <p className="text price">
            {eventsData?.data[0].taxFreeAmount.toLocaleString('ko-KR')} 원
          </p>
          <p
            className="btn"
            onClick={() => navigate(`/order/${userStore.userId}`)}
          >
            구매하기
          </p>
        </div>
      </div>
    </s.CardWrapper>
  );
};

export default GiftCard;
