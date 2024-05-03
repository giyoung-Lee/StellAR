import React, { useEffect, useRef, useState } from 'react';
import * as THREE from 'three';
import { ThreeEvent, useFrame, useThree } from '@react-three/fiber';
import useStarStore from '../../stores/starStore';
import { useAnimations, useGLTF } from '@react-three/drei';

type Props = {
  position: THREE.Vector3;
  targetSize: number;
  planetId: string;
  spType: string | null;
};

const PlanetMesh = ({ position, targetSize, planetId }: Props) => {
  const {
    starClicked,
    setStarClicked,
    setStarId,
    setZoomX,
    setZoomY,
    setZoomZ,
  } = useStarStore();

  const meshRef = useRef<THREE.Mesh>(null!);

  const { camera, gl, controls } = useThree();
  const { scene, animations } = useGLTF(`/img/${planetId}.glb`);

  const [scale, setScale] = useState(1);
  useEffect(() => {
    if (scene) {
      const box = new THREE.Box3().setFromObject(scene);
      const size = new THREE.Vector3();
      box.getSize(size);
      const maxDimension = Math.max(size.x, size.y, size.z);
      const scaleFactor = targetSize / maxDimension; // 타겟 크기에 맞게 스케일 팩터 계산
      setScale(scaleFactor);
    }
  }, [scene, targetSize]);

  const click = (event: ThreeEvent<MouseEvent>) => {
    event.stopPropagation();
    setStarClicked(!starClicked);

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

    // 클릭하면 클릭 배열에 추가하는 코드, 클릭 해제하면 배열에서 삭제
    if (!starClicked) {
      setZoomX(starPosition.x);
      setZoomY(starPosition.y);
      setZoomZ(starPosition.z);
    } else {
      setZoomX(0);
      setZoomY(0);
      setZoomZ(0);
    }

    console.log(newCameraPosition);
    setStarClicked(true);
    setStarId(planetId);

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
        scale={[scale, scale, scale]}
        object={scene}
      />
    </>
  );
};

export default PlanetMesh;
