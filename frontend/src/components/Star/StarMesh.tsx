import React, { useEffect, useRef, useState } from 'react';
import * as THREE from 'three';
import { getRandomInt } from '../../utils/random';
import { ThreeEvent, useFrame, useThree } from '@react-three/fiber';
import useStarStore from '../../stores/starStore';

type Props = {
  position: THREE.Vector3;
  size: number;
  starId: string;
  spType: string;
};

const StarMesh = ({ position, size, starId, spType }: Props) => {
  const { setStarClicked, setStarId, addStarToClicked, removeStarFromClicked, clickedStars } = useStarStore();

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

    // 별 클릭하면 클릭 배열에 추가하는 코드, 클릭 해제하면 배열에서 삭제
    if (!clicked) {
      addStarToClicked(starId);
    } else {
      removeStarFromClicked(starId);
    }

    console.log(newCameraPosition);
    setStarClicked(true);
    setStarId(starId);

    camera.position.set(
      newCameraPosition.x,
      newCameraPosition.y,
      newCameraPosition.z,
    );
    camera.updateMatrixWorld();
  };


  useEffect(() => {
    if (clicked) {
      meshRef.current.material.color = new THREE.Color('purple');
    } else {
      meshRef.current.material.color = new THREE.Color(starColor[spType]);
    }
  }, [clicked, spType, starColor]);

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
