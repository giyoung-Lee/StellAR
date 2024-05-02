import React, { useEffect, useRef, useState } from 'react';
import * as THREE from 'three';
import { ThreeEvent, useFrame, useThree } from '@react-three/fiber';
import useStarStore from '../../stores/starStore';
import { useAnimations, useGLTF } from '@react-three/drei';

type Props = {
  position: THREE.Vector3;
  size: number;
  starId: string;
  spType: string | null;
};

const PlanetMesh = ({ position, size, starId, spType }: Props) => {
  const { setStarClicked, setStarId, addStarToClicked, removeStarFromClicked } =
    useStarStore();

  const meshRef = useRef<THREE.Mesh>(null!);

  const [clicked, setClicked] = useState(false);
  const { camera, gl, controls } = useThree();
  const { scene, animations } = useGLTF('/img/star1.glb');

  const click = (event: ThreeEvent<MouseEvent>) => {
    event.stopPropagation();
    setClicked(!clicked);

    const starPosition = position;
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

  return (
    <>
      <primitive
        ref={meshRef}
        position={position}
        castShadow={false}
        receiveShadow={false}
        onClick={click}
        scale={50}
        object={scene}
      />
    </>
  );
};

export default PlanetMesh;
