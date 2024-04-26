import React, { useEffect, useRef, useState } from 'react';
import * as THREE from 'three';
import { getRandomInt } from '../../utils/random';
import { ThreeEvent, useFrame, useThree } from '@react-three/fiber';
import constructWithOptions from 'styled-components/dist/constructors/constructWithOptions';

type Props = {
  position: THREE.Vector3;
  size: number;
};

const StarMesh = ({ position, size }: Props) => {
  const meshRef = useRef<THREE.Mesh>(null!);
  const COLOR = ['#88beff', 'lightgreen', '#f9d397', '#fd6b6b', '#ffffac'];
  const colorIndex = getRandomInt(0, COLOR.length);
  const [clicked, setClicked] = useState(false);
  const { camera, gl, scene, controls } = useThree();
  const [lookAtPosition, setLookAtPosition] = useState<THREE.Vector3 | null>(
    null,
  );

  const click = (event: ThreeEvent<MouseEvent>) => {
    event.stopPropagation();
    setClicked(!clicked);
    event.object.material.color = new THREE.Color('purple');

    const mesh = meshRef.current;
    if (mesh) {
      setLookAtPosition(mesh.position.clone());
    }
  };

  // useFrame((state) => {
  //   if (clicked && lookAtPosition) {
  //     state.camera.lookAt(lookAtPosition);
  //     state.camera.updateMatrixWorld(); // 카메라의 변환을 강제로 업데이트
  //   } else {
  //     state.camera.lookAt(0, 0, 0);
  //     // console.log(state.camera);
  //   }
  // });

  useEffect(() => {
    if (clicked) {
      meshRef.current.material.color = new THREE.Color('purple');
      // 카메라의 변환을 업데이트하여 변경된 lookAt 적용
      if (lookAtPosition) {
        camera.lookAt(lookAtPosition);
        camera.updateMatrixWorld();
      }
    }
  }, [clicked, lookAtPosition]);

  return (
    <mesh
      ref={meshRef}
      position={position}
      castShadow={false}
      receiveShadow={false}
      onClick={click}
    >
      <sphereGeometry args={[size, 32, 16]} />
      <meshStandardMaterial color={COLOR[colorIndex]} />
    </mesh>
  );
};

export default StarMesh;
