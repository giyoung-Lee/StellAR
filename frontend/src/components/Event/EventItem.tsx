import React from 'react';
import * as e from '../style/EventStyle';

type Props = {
  event: eventApitype;
};

const EventItem = ({ event }: Props) => {
  return (
    <>
      <e.Coner />
      <e.EventInfo>
        <e.EventDate>{`${event.localDate} (${event.astroTime})`}</e.EventDate>
        <e.EventTitle>{event.astroEvent}</e.EventTitle>
      </e.EventInfo>
    </>
  );
};

export default EventItem;
