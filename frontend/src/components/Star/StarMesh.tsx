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

const StarMesh = ({ position, size, starId, spType }: Props) => {
  const {
    setStarClicked,
    starClicked,
    setStarId,
    addStarToClicked,
    removeStarFromClicked,
    setZoomX,
    setZoomY,
    setZoomZ,
  } = useStarStore();

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
  const { camera, gl, scene, controls } = useThree();

  const click = (event: ThreeEvent<MouseEvent>) => {
    event.stopPropagation();
    setStarClicked(!starClicked);
    
    const mesh = event.object as THREE.Mesh;  // 타입 단언
    if (mesh.material && 'color' in mesh.material) {
      (mesh.material as THREE.MeshStandardMaterial).color.set('purple');
    }

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

      addStarToClicked(starId);
      setZoomX(starPosition.x);
      setZoomY(starPosition.y);
      setZoomZ(starPosition.z);

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
    <mesh
      ref={meshRef}
      position={position}
      castShadow={false}
      receiveShadow={false}
      onClick={click}
    >
      <sphereGeometry args={[size, 32, 16]} />
      <meshStandardMaterial
        color={spType ? starColor[spType as string] : 'red'}
      />
    </mesh>
  );
};

export default StarMesh;
