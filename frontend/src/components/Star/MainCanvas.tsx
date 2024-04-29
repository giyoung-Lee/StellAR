import React, { useEffect, useRef } from 'react';
import { getRandomInt } from '../../utils/random';
import * as THREE from 'three';
import StarMesh from './StarMesh';
import { Canvas, useThree } from '@react-three/fiber';
import Controls from './Controls';
import GLBModel from './GLBModel';
import Lights from './Lights';
import FloorMesh from './FloorMesh';
import { GetStars } from '../../apis/StarApis';
import Loading from '../common/Loading/Loading';
import { useQuery } from '@tanstack/react-query';

type Props = {};

const MainCanvas = (props: Props) => {
  const {
    isLoading: isStarsLoading,
    data: starData,
    isError: isStarsError,
    refetch: getStarsRefetch,
  } = useQuery({
    queryKey: ['get-stars'],
    queryFn: GetStars,
  });

  if (isStarsLoading) {
    return (
      <>
        <Loading />
      </>
    );
  }

  return (
    <Canvas
      gl={{ antialias: true }}
      scene={{ background: new THREE.Color(0x000000) }}
      camera={{
        fov: 80,
        position: [
          -0.5 / Math.sqrt(3),
          -0.5 / Math.sqrt(3),
          -0.5 / Math.sqrt(3),
        ],
        // rotation: [0, 0, 0],
        far: 100000,
      }}
    >
      <Controls />
      <Lights />
      {starData?.data.map((star: any) => (
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
      {/* {genBackgroundStars()} */}
      <FloorMesh />
      {/* <GLBModel /> */}
    </Canvas>
  );
};

export default MainCanvas;
