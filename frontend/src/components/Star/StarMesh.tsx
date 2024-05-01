import React, { useEffect, useRef, useState } from 'react';        
import * as THREE from 'three';
import { getRandomInt } from '../../utils/random';
import { ThreeEvent, useFrame, useThree } from '@react-three/fiber';
import constructWithOptions from 'styled-components/dist/constructors/constructWithOptions';
import useStarStore from '../../stores/starStore';

type Props = {
  position: THREE.Vector3;
  size: number;
  starId: string;
  spType: string;
};

const StarMesh = ({ position, size, starId, spType }: Props) => {
  const starStore = useStarStore();

  const meshRef = useRef<THREE.Mesh>(null!);
  const starColor: { [key: string]: string } = {
    O: '#3db8ff',
    B: '#6bffe1',
    A: '#ffffff',
    F: '#fff09c',
    G: '#ffd900',
    K: '#ff9100',
    M: '#ff6565',
  };
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

    const starPosition = event.object.position;
    console.log(starPosition);

    const alpha = Math.sqrt(
      starPosition.x * starPosition.x +
        starPosition.y * starPosition.y +
        starPosition.z * starPosition.z,
    );

    const newCameraPosition = new THREE.Vector3(
      starPosition.x / (-0.5 * alpha),
      starPosition.y / (-0.5 * alpha),
      starPosition.z / (-0.5 * alpha),
    );

    console.log(newCameraPosition);
    starStore.setStarClicked(true);
    starStore.setStarId(starId);

    camera.position.set(
      newCameraPosition.x,
      newCameraPosition.y,
      newCameraPosition.z,
    );
    camera.updateMatrixWorld();
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
      <meshStandardMaterial color={starColor[spType]} />
    </mesh>
  );
};

export default StarMesh;
