import React from 'react';
import { getRandomInt } from '../../utils/random';
import * as THREE from 'three';
import StarMesh from './StarMesh';
import { Canvas } from '@react-three/fiber';
import Controls from './Controls';
import GLBModel from './GLBModel';
import Lights from './Lights';

type Props = {};

const MainCanvas = (props: Props) => {
  function genBackgroundStars() {
    const stars = [];
    for (let i = 0; i < 500; i++) {
      const size = getRandomInt(30, 40);
      const pos = new THREE.Vector3(
        getRandomInt(-50000, 50000),
        getRandomInt(-50000, 50000),
        getRandomInt(-50000, 50000),
      );
      stars.push(<StarMesh position={pos} size={size} />);
    }
    return stars;
  }
  return (
    <Canvas
      gl={{ antialias: true }}
      scene={{ background: new THREE.Color(0x000000) }}
      camera={{
        position: [10000, 10000, 10000],
        rotation: [-0.5, 0, 0],
        far: 100000,
      }}
    >
      <Controls />
      <Lights />
      {/* <GLBModel /> */}
      {genBackgroundStars()}
    </Canvas>
  );
};

export default MainCanvas;
