import React from 'react';
import * as p from './style/CommonPageStyle';
import * as h from '../components/common/HeaderStyle';
import EventList from '../components/Event/EventList';

const EventPage = () => {
  return (
    <p.Wrapper>
      <h.Header>
        <h.Title>천문 현상 소식</h.Title>
      </h.Header>
      <EventList />
    </p.Wrapper>
  );
};

export default EventPage;
