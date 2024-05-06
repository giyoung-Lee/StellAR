import React from 'react';
import * as s from '../style/ShopStyle';
import image from '/icons/stella_logo.gif';

const GiftCard = () => {
  return (
    <s.CardWrapper>
      <div className="card">
        <div className="frontBox">
          <p className="card_title">Galaxy</p>
          <img className="img" src={image} />
          <p className="card_content">스마트 별자리 빔 프로젝터</p>
        </div>
        <div className="textBox">
          <p className="card_title">Galaxy</p>
          <ul className="info">
            <li>- 오로라 무드등</li>
            <li>- 나만의 별자리 만들기</li>
            <li>- 사이즈 x * y * z</li>
          </ul>
          <p className="text price">20,000,000 원</p>
          <p className="btn">구매하기</p>
        </div>
      </div>
    </s.CardWrapper>
  );
};

export default GiftCard;

