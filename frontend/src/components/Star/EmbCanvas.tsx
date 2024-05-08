import React, { useEffect } from 'react';
import { getRandomInt } from '../../utils/random';
import * as THREE from 'three';
import StarMesh from './StarMesh';
import { Canvas, useThree } from '@react-three/fiber';
import { OrbitControls, PerspectiveCamera } from '@react-three/drei';
import Lights from './Lights';
import FloorMesh from './FloorMesh';
import { GetConstellation, GetPlanets, GetStars } from '../../apis/StarApis';
import Loading from '../common/Loading/Loading';
import { useQuery } from '@tanstack/react-query';
import useStarStore from '../../stores/starStore';
import useCameraStream from '../../hooks/useCameraStream';
import useDeviceOrientation from '../../hooks/useDeviceOrientation';
import MakeConstellation from './MakeConstellation';
import PlanetMesh from './PlanetMesh';
import Background from './BackGround';

type Props = {};

interface BackgroundSetterProps {
  videoTexture: THREE.VideoTexture | null;
  isARMode: boolean;
}

interface ConstellationData {
  [key: string]: string[][]; // 각 키는 문자열 배열의 배열을 값으로 가짐
}

const EmbCanvas = (props: Props) => {
  // 스토어에서 필요한 요소 가져오기
  const { zoomX, zoomY, zoomZ, isARMode, starClicked, planetClicked } =
    useStarStore();

  const videoTexture = useCameraStream();

  const { isLoading: isStarsLoading, data: starData } = useQuery({
    queryKey: ['get-stars'],
    queryFn: () => {
      return GetStars('4');
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

  if (isStarsLoading || isConstLoading || isPlanetLoading) {
    return <Loading />;
  }

  return (
    <Canvas gl={{ antialias: true, alpha: true }}>
      <Background />

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
            -0.5 / Math.sqrt(3),
            -0.5 / Math.sqrt(3),
            -0.5 / Math.sqrt(3),
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

      {/* 별 정보 불러오기 */}
      {Object.values(starData?.data).map((star: any) => (
        <StarMesh
          starId={star.starId}
          spType={star.spType}
          key={star.starId}
          position={
            new THREE.Vector3(
              star.calX * star.nomalizedMagV,
              star.calY * star.nomalizedMagV,
              star.calZ * star.nomalizedMagV,
            )
          }
          size={getRandomInt(150, 200)}
        />
      ))}

      {/* 행성 정보 불러오기 */}
      {planetData?.data.map((planet: any) => (
        <PlanetMesh
          planetId={planet.planetId}
          spType={null}
          key={planet.planetId}
          position={
            new THREE.Vector3(
              planet.calX * planet.nomalizedMagV,
              planet.calY * planet.nomalizedMagV,
              planet.calZ * planet.nomalizedMagV,
            )
          }
          targetSize={800}
        />
      ))}

      {/* 별자리 호출 및 선긋기 */}
      {constData?.data &&
        starData?.data &&
        Object.entries(constData.data as ConstellationData).map(
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
  const { scene } = useThree();
  const { camera } = useThree();

  useDeviceOrientation(camera);

  useEffect(() => {
    if (isARMode && videoTexture) {
      scene.background = videoTexture;
    } else {
      scene.background = new THREE.Color(0x000000);
    }
  }, [videoTexture, isARMode, scene]);

  return null;
};

export default EmbCanvas;
