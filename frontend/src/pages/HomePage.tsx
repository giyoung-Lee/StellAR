import * as h from './style/HomePageStyle';
import MainCanvas from '../components/Star/MainCanvas';
import useStarStore from '../stores/starStore';
import StarName from '../components/Star/StarName';
import { useEffect, useState } from 'react';
import { useMutation, useQuery } from '@tanstack/react-query';
import useUserStore from '../stores/userStore';
import useConstellationStore from '../stores/constellationStore';
import StarInfoCarousel from '../components/StarInfoCarousel/StarInfoCarousel';
import MyConstInfoBox from '../components/StarInfoCarousel/MyConstInfoBox';
import { MakeMyConstellationApi } from '../apis/MyConstApis';
import FormControl from '@mui/joy/FormControl';
import FormLabel from '@mui/joy/FormLabel';
import Input from '@mui/joy/Input';
import Modal from '@mui/joy/Modal';
import DialogTitle from '@mui/joy/DialogTitle';
import DialogContent from '@mui/joy/DialogContent';
import Stack from '@mui/joy/Stack';
import Button from '@mui/joy/Button';
import Sheet from '@mui/joy/Sheet';
import Textarea from '@mui/joy/Textarea';
import { whereAmI } from '../apis/UserApis';
import '../pages/style/Fontawsome';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { useNavigate } from 'react-router-dom';

const HomePage = () => {
  const starStore = useStarStore();
  const userStore = useUserStore();
  const constellationStore = useConstellationStore();
  const navigate = useNavigate();

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
    userId: userStore.userId,
    name: '',
    description: '',
    links: starStore.linkedStars,
  });

  // linkedStars가 변할 때마다 업데이트
  useEffect(() => {
    setUserConstellationData((prevData) => ({
      ...prevData,
      links: starStore.linkedStars,
    }));
  }, [starStore.linkedStars]);

  const { mutate } = useMutation({
    mutationFn: MakeMyConstellationApi,
    onSuccess() {
      setOpen(false);
      navigate(`/myStar/${userStore.userId}`);
    },
    onError() {
      setOpen(false);
    },
  });

  // 현재 위치 불러오기
  useEffect(() => {
    const getCurrentLocation = () => {
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
          (position) => {
            const { latitude, longitude } = position.coords;
            userStore.setUserLat(latitude);
            userStore.setUserLng(longitude);
          },
          (error) => {
            console.error('Geolocation 에러: ', error);
          },
        );
      } else {
        console.error('위치 허용을 지원하지 않는 브라우저일 수 있습니다.');
      }
    };

    getCurrentLocation();
  }, []);

  // 현재 주소 보여주기
  const { data: MyLocationData } = useQuery({
    queryKey: ['get-my-location'],
    queryFn: () => {
      return whereAmI(userStore.userLat, userStore.userLng);
    },
  });

  // 나만의 별자리 만들기 모달 관련
  const [open, setOpen] = useState<boolean>(false);

  const handleInputChange = (
    field: keyof UserConstellationData,
    value: string,
  ) => {
    setUserConstellationData((prev) => ({
      ...prev,
      [field]: value,
    }));
  };

  const handleSubmit = (event: any) => {
    event.preventDefault();
    mutate(userConstellationData);
  };

  const toggleForward = () => {
    userStore.setIsForward(!userStore.isForward);
  };

  const toggleGyro = () => {
    userStore.setIsGyro(!userStore.isGyro);
  };

  const hwangdo13info = [
    'Aquarius',
    'Aries',
    'Bukdu',
    'Cancer',
    'Capricornus',
    'Gemini',
    'Leo',
    'Libra',
    'Ophiuchus',
    'Pisces',
    'Sagittarius',
    'Scorpius',
    'Taurus',
    'Virgo',
  ];

  // 자이로 센서 관련 코드입니당
  useEffect(() => {
    const handleOrientation = (event: DeviceOrientationEvent) => {
      const { gamma } = event;
      const safeGamma = gamma || 0;
      // gamma의 절대값이 45를 초과하면 landscape 모드로 인식ㄱㄱ
      if (Math.abs(safeGamma) > 45 && !userStore.isLandscape) {
        userStore.setIsLandscape(true);
      } else if (Math.abs(safeGamma) <= 45 && userStore.isLandscape) {
        userStore.setIsLandscape(false);
      }
    };

    if (starStore.isARMode) {
      // 자이로 센서 이벤트 리스너 등록
      window.addEventListener('deviceorientation', handleOrientation);
    }

    // 컴포넌트가 언마운트되거나 isARMode가 변경될 때 리스너 제거
    return () => {
      window.removeEventListener('deviceorientation', handleOrientation);
    };
  }, [starStore.isARMode, userStore.isLandscape]);

  useEffect(() => {
    // 마운트시 자이로 AR 모드 해제
    if (starStore.isARMode) {
      starStore.setARMode(false);
    }
    if (userStore.isGyro) {
      userStore.setIsGyro(false);
    }
  }, []);

  return (
    <>
      {/* 현재 위치 보여주기 */}
      {MyLocationData && (
        <div className="fixed bottom-3 left-10 z-[1000]">
          <span>{MyLocationData.address.country} </span>
          <span>{MyLocationData.address.city} </span>
          <span>{MyLocationData.address.county}</span>
        </div>
      )}

      <div className="fixed flex top-3 right-4 z-[16777272]">
        <div onClick={toggleForward} className="cursor-pointer">
          {userStore.isForward ? (
            <FontAwesomeIcon icon="stop" size="xl" className="mx-2" />
          ) : (
            <FontAwesomeIcon icon="forward" size="xl" className="mx-2" />
          )}
        </div>
        <div onClick={toggleGyro} className="cursor-pointer">
          <FontAwesomeIcon icon="location-crosshairs" size="xl" />
        </div>
      </div>

      {/* 별 이름 보여주기 */}
      <h.Wrapper>
        {(starStore.starClicked && starStore.linkedStars.length < 1) ||
        (starStore.planetClicked && starStore.linkedStars.length < 1) ||
        (starStore.zoomFromOther && starStore.linkedStars.length < 1) ? (
          <StarName />
        ) : null}

        {starStore.linkedStars.length > 0 ? (
          <div className="absolute flex flex-col z-[1000] top-[55%] justify-center items-center">
            <button
              className="p-3 m-1 bg-white bg-opacity-25 cursor-pointer rounded-xl shadow-custom border-opacity-18 backdrop-blur-sm"
              onClick={() => setOpen(true)}
            >
              나만의 별자리 생성
            </button>

            {/* 모달 내용 */}
            <Modal
              open={open}
              onClose={() => setOpen(false)}
              sx={{
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
              }}
            >
              <Sheet
                sx={{
                  background: 'rgba(255, 255, 255, 0.25)',
                  boxShadow: '0 8px 32px 0 rgba(31, 38, 135, 0.37)',
                  backdropFilter: 'blur(4px)',
                  WebkitBackdropFilter: 'blur(4px)',
                  border: '1px solid rgba(255, 255, 255, 0.18)',
                  borderRadius: '10px',
                  width: '90%',
                  minWidth: '270px',
                  color: '#cba3ff',
                  p: 3,
                  m: 2,
                }}
              >
                <DialogTitle>나만의 별자리</DialogTitle>
                <DialogContent sx={{ color: '#ffffff' }}>
                  별자리 이름을 짓고, 별자리에 대한 설명이나 일기를 적어보세요!
                </DialogContent>
                <form onSubmit={handleSubmit}>
                  <Stack spacing={2}>
                    <FormControl>
                      <FormLabel sx={{ color: '#ffffff', marginTop: '10px' }}>
                        별자리 이름
                      </FormLabel>
                      <Input
                        autoFocus
                        required
                        value={userConstellationData.name}
                        onChange={(e) =>
                          handleInputChange('name', e.target.value)
                        }
                      />
                    </FormControl>
                    <FormControl>
                      <FormLabel sx={{ color: '#ffffff' }}>설명</FormLabel>
                      <Textarea
                        minRows={2}
                        value={userConstellationData.description}
                        onChange={(e) =>
                          handleInputChange('description', e.target.value)
                        }
                      />
                    </FormControl>
                    <Button
                      type="submit"
                      sx={{
                        backgroundColor: '#aa6bfc',
                        color: 'white',
                        '&:hover': {
                          backgroundColor: '#7649af',
                        },
                      }}
                    >
                      등록하기
                    </Button>
                  </Stack>
                </form>
              </Sheet>
            </Modal>

            {/* 새로고침 버튼 */}
            <img
              className="w-10 h-10 m-2 top-5 left-5"
              src="/img/reload.png"
              alt="reload"
              onClick={windeowReload}
              style={{
                cursor: 'pointer',
              }}
            />
          </div>
        ) : null}

        {constellationStore.constellationClicked &&
        hwangdo13info.includes(constellationStore.constellationName) ? (
          <StarInfoCarousel active={0} />
        ) : constellationStore.constellationClicked &&
          !hwangdo13info.includes(constellationStore.constellationName) ? (
          <MyConstInfoBox />
        ) : null}

        <MainCanvas />
      </h.Wrapper>
    </>
  );
};

export default HomePage;
