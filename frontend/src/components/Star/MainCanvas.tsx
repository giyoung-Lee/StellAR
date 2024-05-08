import React, { useEffect, useState, useRef } from 'react';
import { getRandomInt } from '../../utils/random';
import * as THREE from 'three';
import StarMesh from './StarMesh';
import { Canvas, useThree, useFrame } from '@react-three/fiber';
import { OrbitControls, PerspectiveCamera } from '@react-three/drei';
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
import { Euler, Quaternion } from 'three';

type Props = {};

interface BackgroundSetterProps {
  videoTexture: THREE.VideoTexture | null;
  isARMode: boolean;
}

interface ConstellationData {
  [key: string]: string[][]; // 각 키는 문자열 배열의 배열을 값으로 가짐
}

interface StarData {
  starId: string;
  starType: string;
  calX: number;
  calY: number;
  calZ: number;
  constellation: string;
  parallax: string;
  spType: string;
  hd: string;
  magV: string;
  nomalizedMagV: number;
  RA: string;
  Declination: string;
  hourRA: number;
  degreeDEC: number;
  PMRA: string;
  PMDEC: string;
}

interface StarDataMap {
  [key: string]: StarData;
}

interface PlanetData {
  planetId: string;
  planetDEC: string;
  planetMagV: string;
  planetRA: string;
  hourRA: number;
  degreeDEC: number;
  calX: number;
  calY: number;
  calZ: number;
  nomalizedMagV: number;
}
interface PlanetPosition {
  planetId: string;
  calX: number;
  calY: number;
  calZ: number;
  nomalizedMagV: number;
}

const MainCanvas = (props: Props) => {
  // 스토어에서 필요한 요소 가져오기
  const { zoomX, zoomY, zoomZ, isARMode, starClicked, planetClicked } =
    useStarStore();

  const videoTexture = useCameraStream();

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
    const observer = new Astronomy.Observer(35.1595, 126.8526, 0);
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
      {starClicked ? (
        <PerspectiveCamera
          makeDefault
          fov={80}
          near={0.1}
          far={100000}
          position={[zoomX * 0.5, zoomY * 0.5, zoomZ * 0.5]}
        />
      ) : planetClicked ? (
        <PerspectiveCamera
          makeDefault
          fov={80}
          near={0.1}
          far={100000}
          position={[zoomX * 0.85, zoomY * 0.85, zoomZ * 0.85]}
        />
      ) : (
        <PerspectiveCamera
          makeDefault
          fov={80}
          near={0.1}
          far={100000}
          position={[
            0,
            -0.5 / Math.sqrt(3),
            0,
          ]}
        />
      )}

      {/* 카메라 시점 관련 설정 */}
      {starClicked ? (
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
          // maxDistance={20000}
          maxDistance={100000}
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

      <FloorMesh />
    </Canvas>
  );
};

const BackgroundSetter: React.FC<BackgroundSetterProps> = ({
  videoTexture,
  isARMode,
}) => {
  const { scene, camera } = useThree();

  // 자이로센서 데이터를 저장할 상태
  const gyroData = useRef({ alpha: 0, beta: 0, gamma: 0 });

  useEffect(() => {
    if (isARMode && videoTexture) {
      scene.background = videoTexture;
    } else {
      scene.background = new THREE.Color('#000000');
    }
  }, [videoTexture, isARMode, scene, camera]);

  useEffect(() => {
    const handleOrientation = (event: DeviceOrientationEvent) => {
      const alpha = event.alpha ?? 0; // alpha가 null일 경우 0을 사용
      const beta = event.beta ?? 0;   // beta가 null일 경우 0을 사용
      const gamma = event.gamma ?? 0; // gamma가 null일 경우 0을 사용
      gyroData.current = { alpha, beta, gamma };
    };

    if (isARMode) {
      window.addEventListener('deviceorientation', handleOrientation, true);
      return () => {
        window.removeEventListener('deviceorientation', handleOrientation);
      };
    }
  }, [isARMode]);

  // useFrame 훅은 여기에서 직접 호출합니다.
  useFrame(() => {
    if (isARMode) {
      const scaleFactor = 2;
      // 자이로센서 데이터를 바탕으로 Euler 객체 생성
      const { alpha, beta, gamma } = gyroData.current;
      const euler = new Euler(
        THREE.MathUtils.degToRad(beta*scaleFactor),
        THREE.MathUtils.degToRad(gamma*scaleFactor),
        THREE.MathUtils.degToRad(-alpha*scaleFactor),
        'YXZ',
      );
      // Euler 객체를 쿼터니언으로 변환
      const quaternion = new Quaternion().setFromEuler(euler);

      // 카메라의 쿼터니언을 업데이트
      camera.quaternion.slerp(quaternion, 0.1); // slerp를 사용하여 부드러운 전환 적용
    }
  });

  return null;
};

export default MainCanvas;
