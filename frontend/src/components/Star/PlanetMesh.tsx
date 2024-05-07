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
  const {
    setStarClicked,
    setPlanetClicked,
    setStarId,
    setZoomX,
    setZoomY,
    setZoomZ,
  } = useStarStore();

  const meshRef = useRef<THREE.Mesh>(null!);

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
    setStarId(planetId);
    setStarClicked(false);
    setPlanetClicked(true)

    const starPosition = position;
    console.log("행성 클릭 지점"+starPosition);

    setZoomX(starPosition.x);
    setZoomY(starPosition.y);
    setZoomZ(starPosition.z);
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
