import React, { useRef } from 'react';
import * as THREE from 'three';
import { getRandomInt } from '../../utils/random';
import { useFrame } from '@react-three/fiber';

const FloorMesh = () => {
  const meshRef = useRef<THREE.Mesh>(null);

  return (
    <mesh ref={meshRef} position={[0, 0, 0]} rotation-x={-Math.PI / 2}>
      <circleGeometry args={[100000]} />
      <meshStandardMaterial color={0xffffff} />
    </mesh>
  );
};

export default FloorMesh;

