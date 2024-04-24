import React, { useRef } from 'react';
import * as THREE from 'three';
import { getRandomInt } from '../../utils/random';
import { useFrame } from '@react-three/fiber';

type Props = {
  position: THREE.Vector3;
  size: number;
};

const StarMeshes = ({ position, size }: Props) => {
  const meshRef = useRef<THREE.Mesh>(null!);
  const COLOR = ['#88beff', 'white', '#f9d397', '#fd6b6b', '#ffffac'];
  const colorIndex = getRandomInt(0, COLOR.length);
  const Y_AXIS = new THREE.Vector3(0, 1, 0);
  const DIST_LIMIT = 10000;

  useFrame((state, delta) => {
    // const pos = meshRef.current.position.applyAxisAngle(Y_AXIS, delta / 100);
    // const dist = state.camera.position.distanceTo(meshRef.current.position);
    // if (dist > DIST_LIMIT)
    //   meshRef.current.scale.set(
    //     dist / DIST_LIMIT,
    //     dist / DIST_LIMIT,
    //     dist / DIST_LIMIT,
    //   );
    // meshRef.current.position.x = pos.x;
    // meshRef.current.position.y = pos.y;
    // meshRef.current.position.z = pos.z;
  });

  return (
    <mesh ref={meshRef} position={position}>
      <sphereGeometry args={[size, 32, 16]} />
      <meshStandardMaterial color={COLOR[colorIndex]} />
    </mesh>
  );
};

export default StarMeshes;

