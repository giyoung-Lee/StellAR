import React from 'react';
import * as e from '../style/EventStyle';

const EventItem = () => {
  return (
    <>
      <e.Coner />
      <e.EventInfo>
        <e.EventDate>2024. 05. 05. (10:00)</e.EventDate>
        <e.EventTitle>화성과 토성의 근접</e.EventTitle>
      </e.EventInfo>
    </>
  );
};

export default EventItem;
