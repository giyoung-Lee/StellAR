import * as h from './style/HomePageStyle';
import MainCanvas from '../components/Star/MainCanvas';
import useStarStore from '../stores/starStore';
import StarName from '../components/Star/StarName';
import React, { useEffect, useState } from 'react';
import { useMutation, useQuery } from '@tanstack/react-query';
import useUserStore from '../stores/userStore';
import useConstellationStore from '../stores/constellationStore';
import StarInfoCarousel from '../components/StarInfoCarousel/StarInfoCarousel';
import { MakeMyConstellationApi } from '../apis/MyConstApis';
import Swal from 'sweetalert2';
import Modal from '@mui/joy/Modal';
import ModalClose from '@mui/joy/ModalClose';
import Typography from '@mui/joy/Typography';
import Sheet from '@mui/joy/Sheet';

const HomePage = () => {
  const starStore = useStarStore();
  const userStore = useUserStore();
  const constellationStore = useConstellationStore();

  useEffect(() => {
    starStore.setStarClicked(false);
    starStore.setStarId('');
    starStore.setStarPosition(null);
    constellationStore.setConstellationClicked(false);
  }, []);

  const windeowReload = () => {
    window.location.reload();
  };

  // 별자리 생성 POST
  const [userConstellationData, setUserConstellationData] = useState({
    userId: '',
    name: '',
    description: '',
    links: [],
  });

  const { mutate } = useMutation({
    mutationFn: MakeMyConstellationApi,
    onSuccess(result: string) {
      console.log(result);
    },
    onError(error) {
      Swal.fire({
        icon: 'error',
        title: '오류',
        text: '별자리 생성 중 오류가 발생했습니다.',
      });
    },
  });

  const [open, setOpen] = useState<boolean>(false);

  return (
    <>
      <h.Wrapper>
        {(starStore.starClicked && starStore.linkedStars.length < 1) ||
        (starStore.planetClicked && starStore.linkedStars.length < 1) ||
        (starStore.zoomFromOther && starStore.linkedStars.length < 1) ? (
          <StarName />
        ) : null}

        {starStore.linkedStars.length > 0 ? (
          <div className="absolute flex flex-col z-[1000] top-[55%] justify-center items-center">
            <button
              className="p-3 m-1 bg-white bg-opacity-25 rounded-xl shadow-custom border-opacity-18 backdrop-blur-sm"
              onClick={() => setOpen(true)}
            >
              나만의 별자리 생성
            </button>

            {/* 모달 내용 */}
            <Modal
              aria-labelledby="modal-title"
              aria-describedby="modal-desc"
              open={open}
              onClose={() => setOpen(false)}
              sx={{
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
              }}
            >
              <Sheet
                variant="outlined"
                sx={{
                  maxWidth: 500,
                  borderRadius: 'md',
                  p: 3,
                  boxShadow: 'lg',
                }}
              >
                <ModalClose variant="plain" sx={{ m: 1 }} />
                <Typography
                  component="h2"
                  id="modal-title"
                  level="h4"
                  textColor="inherit"
                  fontWeight="lg"
                  mb={1}
                >
                  This is the modal title
                </Typography>
                <Typography id="modal-desc" textColor="text.tertiary">
                  Make sure to use <code>aria-labelledby</code> on the modal
                  dialog with an optional <code>aria-describedby</code>{' '}
                  attribute.
                </Typography>
              </Sheet>
            </Modal>

            {/* 새로고침 버튼 */}
            <img
              className="w-10 h-10 m-2 top-5 left-5"
              src="/img/reload.png"
              alt="reload"
              onClick={windeowReload}
            />
          </div>
        ) : null}

        {constellationStore.constellationClicked ? (
          <StarInfoCarousel active={0} />
        ) : null}
        <MainCanvas />
      </h.Wrapper>
    </>
  );
};

export default HomePage;
