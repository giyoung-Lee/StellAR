import { getRandomInt } from '../../utils/random';
import * as THREE from 'three';
import StarMesh from './StarMesh';
import { Canvas, useFrame, useThree } from '@react-three/fiber';
import {
  DeviceOrientationControls,
  OrbitControls,
  PerspectiveCamera,
  Sparkles,
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
import useCameraStream from '../../hooks/useCameraStream';
import { GetStarMark } from '../../apis/StarMarkApis';
import { GetUserConstellationLinkApi } from '../../apis/MyConstApis';

type Props = {};

interface ConstellationData {
  [key: string]: string[][]; // 각 키는 문자열 배열의 배열을 값으로 가짐
}

const EmbCanvas = (props: Props) => {
  const starStore = useStarStore();
  const userStore = useUserStore();

  const isFromOther = starStore.zoomFromOther;

  const videoTexture = useCameraStream();

  // 광주시청을 기본값으로
  const [position, setPosition] = useState<Position>({
    lat: 35.1595,
    lng: 126.8526,
  });

  // 현재 위치 불러오기

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

  // 천체의 방위각과 고도를 계산한 후, 카르테시안 좌표로 변환하는 함수
  const calculateStarPositions = (data: StarDataMap) => {
    const time = new Date();
    const observer = new Astronomy.Observer(position.lat, position.lng, 0);
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

  const calculatePlanetPositions = (data: PlanetData[]) => {
    const time = new Date();
    const observer = new Astronomy.Observer(35.1595, 126.8526, 0);
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
  }, [planetData, starData]);

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
      {/* 배경 별 및 스파클 */}
      <BackgroundStars />

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

      {/* <Sparkles count={100} scale={18} size={10} speed={1} /> */}

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
