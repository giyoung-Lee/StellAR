import React from 'react';
import * as p from './style/CommonPageStyle';
import * as h from '../components/style/common/HeaderStyle';
import EventList from '../components/Event/EventList';
import Modal from '@mui/joy/Modal';
import ModalClose from '@mui/joy/ModalClose';
import Sheet from '@mui/joy/Sheet';
import HelpList from '../components/Event/HelpList';

const EventPage = () => {
  const [open, setOpen] = React.useState<boolean>(false);

  return (
    <p.Wrapper>
      <div className="flex justify-end">
        <img
          src="/img/help.png"
          alt="help"
          className="w-8 m-[1.5vh] cursor-pointer"
          onClick={() => setOpen(true)}
        />
      </div>

      <h.Header>
        <h.Title>천문 현상 소식</h.Title>
      </h.Header>
      <EventList />

      {/* 모달입니당 */}
      <Modal
        aria-labelledby="modal-title"
        aria-describedby="modal-desc"
        open={open}
        onClose={() => setOpen(false)}
        sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}
      >
        <Sheet
          variant="outlined"
          sx={{
            maxWidth: '95vw',
            minWidth: '90vw',
            borderRadius: 'md',
            p: 2,
            boxShadow: 'lg',
            backgroundColor: '#0a0d14',
          }}
        >
          <div className='inline-block'>
          <ModalClose variant="plain" sx={{ m: 0 }} />
          </div>
          <HelpList />
        </Sheet>
      </Modal>
    </p.Wrapper>
  );
};

export default EventPage;
