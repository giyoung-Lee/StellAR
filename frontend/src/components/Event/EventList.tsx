import React from 'react';
import * as l from '../common/ListCardStyle';
import EventItem from './EventItem';

const EventList = () => {
  const myStarList = new Array(5).fill(0);
  return (
    <l.Wrapper>
      {myStarList.map((item, index) => (
        <l.EventCard>
          <EventItem />
        </l.EventCard>
      ))}
    </l.Wrapper>
  );
};

export default EventList;
