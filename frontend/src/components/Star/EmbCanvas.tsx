import { getRandomInt } from '../../utils/random';
import * as THREE from 'three';
import StarMesh from './StarMesh';
import { Canvas, useFrame, useThree } from '@react-three/fiber';
import {
  DeviceOrientationControls,
  Instances,
  OrbitControls,
  PerspectiveCamera,
  Sparkles,
  Html,
  Stars,
} from '@react-three/drei';
import Lights from './Lights';
import FloorMesh from './FloorMesh';
import { GetConstellation, GetPlanets, GetStars } from '../../apis/StarApis';
import Loading from '../common/Loading/Loading';
import { useQuery } from '@tanstack/react-query';
import useStarStore from '../../stores/starStore';
import MakeConstellation from './MakeConstellation';
import PlanetMesh from './PlanetMesh';
import Background from './BackGround';
import { CameraAnimator } from '../../hooks/CameraAnimator';
import { useEffect, useRef, useState } from 'react';
import * as Astronomy from 'astronomy-engine';
import useUserStore from '../../stores/userStore';
import { GetStarMark } from '../../apis/StarMarkApis';
import { GetUserConstellationLinkApi } from '../../apis/MyConstApis';
import DateTimePicker from 'react-datetime-picker';
import 'react-datetime-picker/dist/DateTimePicker.css';
import 'react-calendar/dist/Calendar.css';
import 'react-clock/dist/Clock.css';
import '../../pages/style/Fontawsome';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

interface ConstellationData {
  [key: string]: string[][]; // 각 키는 문자열 배열의 배열을 값으로 가짐
}

const EmbCanvas = () => {
  const starStore = useStarStore();
  const userStore = useUserStore();

  const isFromOther = starStore.zoomFromOther;

  const { isLoading: isStarsLoading, data: starData } = useQuery({
    queryKey: ['get-stars'],
    queryFn: () => {
      return GetStars('4.5');
    },
  });

  const { isLoading: isPlanetLoading, data: planetData } = useQuery({
    queryKey: ['get-planets'],
    queryFn: GetPlanets,
  });

  const {
    isLoading: isStarMarkLoading,
    data: starMarkData,
    refetch: getStarMarkRefetch,
  } = useQuery({
    queryKey: ['get-starMarks'],
    queryFn: () => GetStarMark('embId'),
    // enabled: !!userStore.userId, // userId가 유효한 경우에만 실행
  });

  useEffect(() => {
    if (starMarkData) {
      starStore.setMarkedStars(starMarkData.data);
    }
  }, [starMarkData]);

  useEffect(() => {
    getStarMarkRefetch();
  }, [starStore.markSaveToggle]);

  const { isLoading: isConstLoading, data: constData } = useQuery({
    queryKey: ['get-consts'],
    queryFn: () => {
      return GetConstellation('hwangdo13');
    },
  });

  const { isLoading: isMyConstLoading, data: myConstData } = useQuery({
    queryKey: ['get-my-consts'],
    queryFn: () => {
      return GetUserConstellationLinkApi('embId');
    },
  });

  // 상태 관리를 위해 useState 사용
  const [planetPositions, setPlanetPositions] = useState<PlanetData[]>([]);

  // Define the state for holding star data as a StarDataMap
  const [starPositions, setStarPositions] = useState<StarDataMap>({});

  // 계산을 위한 시간 불러오기
  const [time, setTime] = useState<Date>(new Date());

  const handleDateChange = (date: Date | null) => {
    if (date) {
      setTime(date);
    } else {
      setTime(new Date());
    }
  };

  // 화면 자동 재생을 위한 코드(5분 단위)
  useEffect(() => {
    let timer: ReturnType<typeof setInterval> | null = null;
    if (userStore.isForward) {
      timer = setInterval(() => {
        setTime((prevTime) => new Date(prevTime.getTime() + 300000));
      }, 1000);
    } else {
      setTime(new Date());
    }

    return () => {
      if (timer) clearInterval(timer);
    };
  }, [userStore.isForward]);

  // 시간 초기화 버튼 만들기 위한 현재 시간 구하기
  const currentDate = new Date();
  const currentYear = currentDate.getFullYear();
  const currentMonth = currentDate.getMonth();
  const currentDay = currentDate.getDate();
  const currentHour = currentDate.getHours();
  const currentMinute = currentDate.getMinutes();

  // 시간 초기화 버튼 만들기 위한 등록 시간 구하기
  const selectedYear = time.getFullYear();
  const selectedMonth = time.getMonth();
  const selectedDay = time.getDate();
  const selectedHour = time.getHours();
  const selectedMinute = time.getMinutes();

  // 등록 시간이랑 현재 시간 비교
  const isCurrentTimeSelected =
    currentYear === selectedYear &&
    currentMonth === selectedMonth &&
    currentDay === selectedDay &&
    currentHour === selectedHour &&
    currentMinute === selectedMinute;

  const timeReload = () => {
    userStore.setIsForward(false);
    setTime(currentDate);
  };

  // 천체의 방위각과 고도를 계산한 후, 카르테시안 좌표로 변환하는 함수(별)
  const calculateStarPositions = (data: StarDataMap) => {
    const observer = new Astronomy.Observer(
      userStore.userLat,
      userStore.userLng,
      0,
    );
    const result: StarDataMap = {};

    Object.keys(data).forEach((key) => {
      const star = data[key];
      const horizontal = Astronomy.Horizon(
        time,
        observer,
        star.hourRA,
        star.degreeDEC,
        'normal',
      );
      const radius = 1;
      const azimuthRad = (horizontal.azimuth * Math.PI) / 180;
      const altitudeRad = (horizontal.altitude * Math.PI) / 180;
      const x = radius * Math.cos(altitudeRad) * Math.sin(azimuthRad);
      const y = radius * Math.cos(altitudeRad) * Math.cos(azimuthRad);
      const z = radius * Math.sin(altitudeRad);
      result[key] = { ...star, calX: x, calY: y, calZ: z };
    });

    return result;
  };

  // 천체의 방위각과 고도를 계산한 후, 카르테시안 좌표로 변환하는 함수(행성)
  const calculatePlanetPositions = (data: PlanetData[]) => {
    const observer = new Astronomy.Observer(
      userStore.userLat,
      userStore.userLng,
      0,
    );
    return data.map((planet) => {
      const horizontal = Astronomy.Horizon(
        time,
        observer,
        planet.hourRA,
        planet.degreeDEC,
        'normal',
      );
      const radius = 1;
      const azimuthRad = (horizontal.azimuth * Math.PI) / 180;
      const altitudeRad = (horizontal.altitude * Math.PI) / 180;
      const x = radius * Math.cos(altitudeRad) * Math.sin(azimuthRad);
      const y = radius * Math.cos(altitudeRad) * Math.cos(azimuthRad);
      const z = radius * Math.sin(altitudeRad);
      return { ...planet, calX: x, calY: y, calZ: z };
    });
  };

  useEffect(() => {
    if (planetData?.data) {
      const newPlanetPositions = calculatePlanetPositions(planetData.data);
      setPlanetPositions(newPlanetPositions);
    }
    if (starData?.data) {
      const newStarPositions = calculateStarPositions(starData.data);
      setStarPositions(newStarPositions);
    }
  }, [planetData, starData, time]);

  if (
    isStarsLoading ||
    isConstLoading ||
    isPlanetLoading ||
    isMyConstLoading ||
    isStarMarkLoading
  ) {
    return <Loading />;
  }

  const BackgroundStars = () => {
    const { camera } = useThree();
    const ref = useRef<any>();

    useFrame(() => {
      if (ref.current) {
        ref.current.position.copy(camera.position);
        ref.current.position.z -= 1000;
      }
    });

    return (
      <>
        <Sparkles ref={ref} count={100} scale={15} size={4} />
        <Stars
          ref={ref}
          radius={500}
          depth={500}
          count={2000}
          factor={20}
          speed={1}
        />
      </>
    );
  };

  return (
    <Canvas gl={{ antialias: true, alpha: true }}>
      {/* 시간 조작 부분 */}
      {!starStore.starClicked &&
        !starStore.planetClicked &&
        !starStore.isARMode &&
        !userStore.isGyro && (
          <Html fullscreen>
            <div className="fixed w-[80vw] m-2">
              <DateTimePicker
                onChange={handleDateChange}
                value={time}
                clearIcon={null}
                format="y년 MM월 dd일 HH시 mm분"
              />
              {!isCurrentTimeSelected && (
                <FontAwesomeIcon
                  icon="rotate-right"
                  size="xl"
                  className="mx-2 cursor-pointer"
                  onClick={timeReload}
                />
              )}
            </div>
          </Html>
        )}

      {/* 배경 별 및 스파클 */}
      {!userStore.isForward && !starStore.isARMode && <BackgroundStars />}

      {!starStore.isARMode && <Background />}

      {/* 카메라 이동 설정 */}
      <CameraAnimator />

      {/* 카메라 설정 */}
      {starStore.isARMode ? (
        <PerspectiveCamera
          makeDefault
          fov={80}
          near={1}
          far={100000}
          position={[0, 0, 0]}
        />
      ) : starStore.starClicked || isFromOther ? (
        <PerspectiveCamera
          makeDefault
          fov={80}
          near={1}
          far={100000}
          position={[
            starStore.zoomX * 0.5,
            starStore.zoomY * 0.5,
            starStore.zoomZ * 0.5,
          ]}
        />
      ) : starStore.planetClicked ? (
        <PerspectiveCamera
          makeDefault
          fov={80}
          near={1}
          far={100000}
          position={[
            starStore.zoomX * 0.85,
            starStore.zoomY * 0.85,
            starStore.zoomZ * 0.85,
          ]}
        />
      ) : (
        <PerspectiveCamera
          makeDefault
          fov={80}
          near={1}
          far={100000}
          position={[
            -0.5 / Math.sqrt(3),
            -0.5 / Math.sqrt(3),
            -0.5 / Math.sqrt(3),
          ]}
        />
      )}

      {/* 카메라 시점 관련 설정 */}
      {starStore.isARMode ? (
        <DeviceOrientationControls />
      ) : starStore.starClicked || isFromOther ? (
        <OrbitControls
          target={[starStore.zoomX, starStore.zoomY, starStore.zoomZ]}
          rotateSpeed={-0.25}
          zoomSpeed={5}
          minDistance={5000}
          maxDistance={30000}
          enableDamping
          dampingFactor={0.1}
          enableZoom={true}
        />
      ) : starStore.planetClicked ? (
        <OrbitControls
          target={[starStore.zoomX, starStore.zoomY, starStore.zoomZ]}
          rotateSpeed={-0.25}
          zoomSpeed={5}
          minDistance={1000}
          maxDistance={30000}
          enableDamping
          dampingFactor={0.1}
          enableZoom={true}
        />
      ) : (
        <OrbitControls
          target={[0, 0, 0]}
          rotateSpeed={-0.25}
          zoomSpeed={5}
          minDistance={1}
          // 지구 밖으로 나가지 않는 정도
          maxDistance={20000}
          // maxDistance={100000}
          enableDamping
          dampingFactor={0.1}
          enableZoom={true}
        />
      )}

      {/* 조명 설정 */}
      <Lights />

      {/* 별 */}
      {/* <Instances limit={2000} range={2000}> */}
      {Object.values(starPositions).map((star: any) => (
        <StarMesh
          propstarId={star.starId}
          spType={star.spType}
          key={star.starId}
          position={
            new THREE.Vector3(
              -star.calX * star.nomalizedMagV,
              star.calZ * star.nomalizedMagV,
              star.calY * star.nomalizedMagV,
            )
          }
          size={getRandomInt(100, 110)}
        />
      ))}
      {/* </Instances> */}

      {planetPositions.map((planet: any) => (
        <PlanetMesh
          planetId={planet.planetId}
          spType={null}
          key={planet.planetId}
          position={
            new THREE.Vector3(
              -planet.calX * planet.nomalizedMagV,
              planet.calZ * planet.nomalizedMagV,
              planet.calY * planet.nomalizedMagV,
            )
          }
          targetSize={1000}
        />
      ))}

      {/* 별자리 호출 및 선긋기 */}
      {constData?.data &&
        Object.entries(constData.data as ConstellationData).map(
          ([constellation, connections]) =>
            (connections as string[][]).map((starArr, index) => (
              <MakeConstellation
                key={index}
                constellation={constellation}
                pointA={
                  new THREE.Vector3(
                    -starPositions[starArr[0]]?.calX *
                      starPositions[starArr[0]]?.nomalizedMagV,
                    starPositions[starArr[0]]?.calZ *
                      starPositions[starArr[0]]?.nomalizedMagV,
                    starPositions[starArr[0]]?.calY *
                      starPositions[starArr[0]]?.nomalizedMagV,
                  )
                }
                pointB={
                  new THREE.Vector3(
                    -starPositions[starArr[1]]?.calX *
                      starPositions[starArr[1]]?.nomalizedMagV,
                    starPositions[starArr[1]]?.calZ *
                      starPositions[starArr[1]]?.nomalizedMagV,
                    starPositions[starArr[1]]?.calY *
                      starPositions[starArr[1]]?.nomalizedMagV,
                  )
                }
              />
            )),
        )}

      {/* 나만의 별자리 호출 및 선긋기 */}
      {myConstData?.data &&
        Object.entries(myConstData.data as ConstellationData).map(
          ([constellation, connections]) =>
            (connections as string[][]).map((starArr, index) => (
              <MakeConstellation
                key={index}
                constellation={constellation}
                pointA={
                  new THREE.Vector3(
                    -starPositions[starArr[0]]?.calX *
                      starPositions[starArr[0]]?.nomalizedMagV,
                    starPositions[starArr[0]]?.calZ *
                      starPositions[starArr[0]]?.nomalizedMagV,
                    starPositions[starArr[0]]?.calY *
                      starPositions[starArr[0]]?.nomalizedMagV,
                  )
                }
                pointB={
                  new THREE.Vector3(
                    -starPositions[starArr[1]]?.calX *
                      starPositions[starArr[1]]?.nomalizedMagV,
                    starPositions[starArr[1]]?.calZ *
                      starPositions[starArr[1]]?.nomalizedMagV,
                    starPositions[starArr[1]]?.calY *
                      starPositions[starArr[1]]?.nomalizedMagV,
                  )
                }
              />
            )),
        )}

      {!starStore.isARMode && <FloorMesh />}
    </Canvas>
  );
};

export default EmbCanvas;

