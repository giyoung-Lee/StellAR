import React, { useEffect } from 'react';
import { getRandomInt } from '../../utils/random';
import * as THREE from 'three';
import StarMesh from './StarMesh';
import { Canvas, useThree } from '@react-three/fiber';
import { OrbitControls, PerspectiveCamera } from '@react-three/drei';
import Lights from './Lights';
import FloorMesh from './FloorMesh';
import { GetConstellation, GetStars } from '../../apis/StarApis';
import Loading from '../common/Loading/Loading';
import { useQuery } from '@tanstack/react-query';
import useStarStore from '../../stores/starStore';
import useCameraStream from '../../hooks/useCameraStream';
import useDeviceOrientation from '../../hooks/useDeviceOrientation';
import MakeConstellation from './MakeConstellation';

type Props = {};

interface BackgroundSetterProps {
  videoTexture: THREE.VideoTexture | null;
  isARMode: boolean;
}

const MainCanvas = (props: Props) => {
  const { isARMode } = useStarStore();

  const videoTexture = useCameraStream();

  const {
    isLoading: isStarsLoading,
    data: starData,
  } = useQuery({
    queryKey: ['get-stars'],
    queryFn: GetStars,
    refetchInterval: false,
  });

  const {
    isLoading: isConstLoading,
    data: constData,
  } = useQuery({
    queryKey: ['get-consts'],
    queryFn: () => {
      return GetConstellation('hwangdo13');
    },
    refetchInterval: false,
  });

  if (isStarsLoading || isConstLoading) {
    return <Loading />;
  }

  return (
    <Canvas gl={{ antialias: true }}>
      <BackgroundSetter videoTexture={videoTexture} isARMode={isARMode} />
      <PerspectiveCamera
        makeDefault
        fov={70}
        near={1}
        far={100000}
        position={[
          -0.5 / Math.sqrt(3),
          -0.5 / Math.sqrt(3),
          -0.5 / Math.sqrt(3),
        ]}
      />
      <OrbitControls
        target={[0, 0, 0]}
        rotateSpeed={-0.25}
        zoomSpeed={10}
        minDistance={1}
        maxDistance={100000}
        enableDamping
        dampingFactor={0.1}
        // enableZoom={false}
      />

      <Lights />
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
          size={getRandomInt(80, 90)}
        />
      ))}

      {constData?.data &&
        starData?.data &&
        Object.values(constData?.data).map((constellation: any) =>
          constellation.map((starArr: string[]) => (
            <MakeConstellation
              pointA={
                new THREE.Vector3(
                  starData?.data[starArr[0]].calX * starData?.data[starArr[0]].nomalizedMagV,
                  starData?.data[starArr[0]].calY * starData?.data[starArr[0]].nomalizedMagV,
                  starData?.data[starArr[0]].calZ * starData?.data[starArr[0]].nomalizedMagV,
                )
              }
              pointB={
                new THREE.Vector3(
                  starData?.data[starArr[1]].calX * starData?.data[starArr[1]].nomalizedMagV,
                  starData?.data[starArr[1]].calY * starData?.data[starArr[1]].nomalizedMagV,
                  starData?.data[starArr[1]].calZ * starData?.data[starArr[1]].nomalizedMagV,
                )
              }
            />
          )),
        )}
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

export default MainCanvas;
