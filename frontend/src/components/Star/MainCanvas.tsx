import React, { useEffect, useState } from 'react';
import { getRandomInt } from '../../utils/random';
import * as THREE from 'three';
import StarMesh from './StarMesh';
import { Canvas, useThree, useFrame } from '@react-three/fiber';
import {
  OrbitControls,
  PerspectiveCamera,
  DeviceOrientationControls,
} from '@react-three/drei';
import Lights from './Lights';
import FloorMesh from './FloorMesh';
import { GetConstellation, GetPlanets, GetStars } from '../../apis/StarApis';
import Loading from '../common/Loading/Loading';
import { useQuery } from '@tanstack/react-query';
import useStarStore from '../../stores/starStore';
import useCameraStream from '../../hooks/useCameraStream';
import MakeConstellation from './MakeConstellation';
import PlanetMesh from './PlanetMesh';
import Background from './BackGround';
import * as Astronomy from 'astronomy-engine';
import useUserStore from '../../stores/userStore';

type Props = {};

interface BackgroundSetterProps {
  videoTexture: THREE.VideoTexture | null;
  isARMode: boolean;
}

const MainCanvas = (props: Props) => {
  // 스토어에서 필요한 요소 가져오기
  const {
    zoomX,
    zoomY,
    zoomZ,
    isARMode,
    starClicked,
    planetClicked,
    zoomFromOther,
    setZoomFromOther,
  } = useStarStore();

  const isFromOther = zoomFromOther;

  const videoTexture = useCameraStream();
  const userStore = useUserStore();

  // 광주시청을 기본값으로
  const [position, setPosition] = useState<Position>({
    lat: 35.1595,
    lng: 126.8526,
  });

  // 현재 위치 불러오기
  useEffect(() => {
    const getCurrentLocation = () => {
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
          (position) => {
            const { latitude, longitude } = position.coords;
            setPosition({ lat: latitude, lng: longitude });
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

  const { isLoading: isConstLoading, data: constData } = useQuery({
    queryKey: ['get-consts'],
    queryFn: () => {
      return GetConstellation('hwangdo13');
    },
  });

  // const { isLoading: isMyConstLoading, data: myConstData } = useQuery({
  //   queryKey: ['get-my-consts'],
  //   queryFn: () => {
  //     return GetUserConstellation('1');
  //   },
  // });

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

  if (isStarsLoading || isConstLoading || isPlanetLoading) {
    return <Loading />;
  }

  return (
    <Canvas gl={{ antialias: true, alpha: true }}>
      {/* 배경 설정 */}
      <BackgroundSetter videoTexture={videoTexture} isARMode={isARMode} />

      {!isARMode && <Background />}

      {/* 카메라 설정 */}
      {isARMode ? (
        <PerspectiveCamera
          makeDefault
          fov={80}
          near={1}
          far={100000}
          position={[0, 0, 0]}
        />
      ) : starClicked || isFromOther ? (
        <PerspectiveCamera
          makeDefault
          fov={80}
          near={1}
          far={100000}
          position={[zoomX * 0.5, zoomY * 0.5, zoomZ * 0.5]}
        />
      ) : planetClicked ? (
        <PerspectiveCamera
          makeDefault
          fov={80}
          near={1}
          far={100000}
          position={[zoomX * 0.85, zoomY * 0.85, zoomZ * 0.85]}
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
      {isARMode ? (
        <DeviceOrientationControls />
      ) : starClicked || isFromOther ? (
        <OrbitControls
          target={[zoomX, zoomY, zoomZ]}
          rotateSpeed={-0.25}
          zoomSpeed={5}
          minDistance={5000}
          maxDistance={30000}
          enableDamping
          dampingFactor={0.1}
          enableZoom={true}
        />
      ) : planetClicked ? (
        <OrbitControls
          target={[zoomX, zoomY, zoomZ]}
          rotateSpeed={-0.25}
          zoomSpeed={5}
          minDistance={1}
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
      {Object.values(starPositions).map((star: any) => (
        <StarMesh
          starId={star.starId}
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
          targetSize={800}
        />
      ))}

      {/* 별자리 호출 및 선긋기 */}
      {constData?.data &&
        starPositions &&
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
      {/* {myConstData?.data &&
        starData?.data &&
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

      {/* 나만의 별자리 호출 및 선긋기 */}
      {/* {myConstData?.data &&
        starData?.data &&
        Object.entries(myConstData.data as ConstellationData).map(
          ([constellation, connections]) =>
            (connections as string[][]).map((starArr, index) => (
              <MakeConstellation
                key={index}
                constellation={constellation}
                pointA={
                  new THREE.Vector3(
                    starData.data[starArr[0]]?.calX *
                      starData.data[starArr[0]]?.nomalizedMagV,
                    starData.data[starArr[0]]?.calY *
                      starData.data[starArr[0]]?.nomalizedMagV,
                    starData.data[starArr[0]]?.calZ *
                      starData.data[starArr[0]]?.nomalizedMagV,
                  )
                }
                pointB={
                  new THREE.Vector3(
                    starData.data[starArr[1]]?.calX *
                      starData.data[starArr[1]]?.nomalizedMagV,
                    starData.data[starArr[1]]?.calY *
                      starData.data[starArr[1]]?.nomalizedMagV,
                    starData.data[starArr[1]]?.calZ *
                      starData.data[starArr[1]]?.nomalizedMagV,
                  )
                }
              />
            )),
        )} */}

      {!isARMode && <FloorMesh />}
    </Canvas>
  );
};

const BackgroundSetter: React.FC<BackgroundSetterProps> = ({
  videoTexture,
  isARMode,
}) => {
  const { scene, camera } = useThree();

  useEffect(() => {
    if (isARMode && videoTexture) {
      scene.background = videoTexture;
    } else {
      scene.background = new THREE.Color('#000000');
    }
  }, [videoTexture, isARMode, scene, camera]);

  return null;
};

export default MainCanvas;
