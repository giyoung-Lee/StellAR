import React, { useEffect, useRef } from 'react';
import { getRandomInt } from '../../utils/random';
import * as THREE from 'three';
import StarMesh from './StarMesh';
import { Canvas, useThree } from '@react-three/fiber';
import Controls from './Controls';
import GLBModel from './GLBModel';
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

const MainCanvas = (props: Props) => {
  const { isARMode } = useStarStore();
  const cameraRef = useRef<THREE.PerspectiveCamera | null>(null);

  useEffect(() => {
    if (isARMode && cameraRef.current) {
      useCameraStream();
      useDeviceOrientation({ camera: cameraRef.current });
    }
  }, [cameraRef.current]);

  const {
    isLoading: isStarsLoading,
    data: starData,
    isError: isStarsError,
    refetch: getStarsRefetch,
  } = useQuery({
    queryKey: ['get-stars'],
    queryFn: GetStars,
  });

  const {
    isLoading: isConstLoading,
    data: constData,
    isError: isConstError,
    refetch: getConstRefetch,
  } = useQuery({
    queryKey: ['get-consts'],
    queryFn: () => {
      return GetConstellation('hwangdo13');
    },
  });

  if (isStarsLoading || isConstLoading) {
    return <Loading />;
  }

  return (
    <Canvas
      gl={{ antialias: true }}
      scene={{ background: new THREE.Color(0x000000) }}
      camera={
        isARMode && cameraRef.current
          ? cameraRef.current
          : {
              fov: 100,
              position: [
                -0.5 / Math.sqrt(3),
                -0.5 / Math.sqrt(3),
                -0.5 / Math.sqrt(3),
              ],
              far: 100000,
            }
      }
    >
      <Controls />
      <Lights />
      {Object.values(starData?.data).map((star: any) => (
        <StarMesh
          starId={star.starId}
          spType={star.spType}
          key={star.starId}
          position={
            new THREE.Vector3(
              star.calX * 20000,
              star.calY * 20000,
              star.calZ * 20000,
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
                  starData?.data[starArr[0]].calX * 20000,
                  starData?.data[starArr[0]].calY * 20000,
                  starData?.data[starArr[0]].calZ * 20000,
                )
              }
              pointB={
                new THREE.Vector3(
                  starData?.data[starArr[1]].calX * 20000,
                  starData?.data[starArr[1]].calY * 20000,
                  starData?.data[starArr[1]].calZ * 20000,
                )
              }
            />
          )),
        )}
      <FloorMesh />
    </Canvas>
  );
};

export default MainCanvas;

