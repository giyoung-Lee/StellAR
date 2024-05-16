import { useEffect, useRef, useState } from 'react';
import * as THREE from 'three';
import { ThreeEvent } from '@react-three/fiber';
import useStarStore from '../../stores/starStore';
import { useGLTF } from '@react-three/drei';

type Props = {
  position: THREE.Vector3;
  targetSize: number;
  planetId: string;
  spType: string | null;
};

const PlanetMesh = ({ position, targetSize, planetId }: Props) => {
  const starStore = useStarStore();

  const meshRef = useRef<THREE.Mesh>(null!);

  const { scene } = useGLTF(`/img/${planetId}.glb`);

  type SizeRatio = {
    Sun: number;
    Moon: number;
    Jupiter: number;
    Saturn: number;
    Venus: number;
    Mars: number;
    Mercury: number;
    Uranus: number;
    Neptune: number;
  };

  const sizeRatio: SizeRatio = {
    Sun: 1,
    Moon: 1.5,
    Jupiter: 0.8, // 목성
    Saturn: 1.5, // 토성
    Venus: 0.7, // 금성
    Mars: 0.65, // 화성
    Mercury: 0.6, // 수성
    Uranus: 0.55, // 천왕성
    Neptune: 0.5, // 해왕성
  };

  const [scale, setScale] = useState(1);

  useEffect(() => {
    if (scene) {
      const box = new THREE.Box3().setFromObject(scene);
      const size = new THREE.Vector3();
      box.getSize(size);
      const maxDimension = Math.max(size.x, size.y, size.z);
      const scaleFactor = targetSize / maxDimension; // 타겟 크기에 맞게 스케일 팩터 계산
      setScale(scaleFactor * sizeRatio[planetId as keyof SizeRatio]);
    }
  }, [scene, targetSize]);

  const click = (event: ThreeEvent<MouseEvent>) => {
    event.stopPropagation();
    starStore.resetLinkedStars();
    starStore.setStarId(planetId);
    starStore.setPlanetClicked(true)
    starStore.setZoomFromOther(false);

    const starPosition = position;
    // console.log('행성 클릭 지점' + starPosition);

    starStore.setZoomX(starPosition.x);
    starStore.setZoomY(starPosition.y);
    starStore.setZoomZ(starPosition.z);
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
