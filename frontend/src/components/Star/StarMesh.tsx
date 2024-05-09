import { useRef } from 'react';
import * as THREE from 'three';
import { ThreeEvent, useThree } from '@react-three/fiber';
import useStarStore from '../../stores/starStore';
import MakeConstellation from './MakeConstellation';

type Props = {
  position: THREE.Vector3;
  size: number;
  propstarId: string;
  spType: string | null;
};

const StarMesh = ({ position, size, propstarId, spType }: Props) => {
  const starStore = useStarStore();

  const meshRef = useRef<THREE.Mesh>(null!);
  const touchAreaRef = useRef<THREE.Mesh>(null!); // 터치 영역 확장을 위한 투명 mesh입니다만

  const starColor: { [key: string]: string } = {
    O: '#3db8ff',
    B: '#6bffe1',
    A: '#ffffff',
    F: '#fff09c',
    G: '#ffd900',
    K: '#ff9100',
    M: '#ff6565',
  };

  // const makeConstellationLine = (
  //   positionA: THREE.Vector3,
  //   positionB: THREE.Vector3,
  // ) => {
  //   return;
  // };

  const { scene } = useThree();

  const click = (event: ThreeEvent<MouseEvent>) => {
    event.stopPropagation();
    const currentStarId = starStore.starId;
    const currentStarPosition = starStore.starPosition;

    if (starStore.linkedStars.some((link) => link.includes(propstarId))) return;

    if (currentStarId) {
      starStore.addStarToClicked([currentStarId, propstarId]);
    }

    if (currentStarPosition) {
      const path = new THREE.CatmullRomCurve3([currentStarPosition, position]);

      const geometry = new THREE.TubeGeometry(path, 20, 110, 6, false);
      const material = new THREE.MeshPhongMaterial({
        color: 0xffffff,
        specular: 0xffffff,
        opacity: 0.2,
        transparent: true,
        shininess: 100,
        flatShading: true,
      });

      const newLine = new THREE.Mesh(geometry, material);
      scene.add(newLine);
    }

    starStore.setStarPosition(position);
    starStore.setStarId(propstarId);
    starStore.setStarClicked(true);
    starStore.setPlanetClicked(false);
    starStore.setZoomFromOther(false);

    const mesh = event.object as THREE.Mesh; // 타입 단언
    if (mesh.material && 'color' in mesh.material) {
      (mesh.material as THREE.MeshPhongMaterial).color.set('black');
    }

    const starPosition = event.object.position;

    if (starStore.linkedStars.length < 1) {
      starStore.setZoomX(starPosition.x);
      starStore.setZoomY(starPosition.y);
      starStore.setZoomZ(starPosition.z);
    }
  };

  return (
    <>
      <mesh
        ref={meshRef}
        position={position}
        castShadow={false}
        receiveShadow={false}
        onClick={click}
      >
        <tetrahedronGeometry args={[size, 2]} />
        <meshPhongMaterial
          color={spType ? starColor[spType as string] : 'red'}
          specular={'white'}
          shininess={100}
          flatShading={true}
        />
      </mesh>

      {/* 터치 영역 확장을 위한 투명 mesh입니다만 */}
      <mesh ref={touchAreaRef} position={position} onClick={click}>
        <sphereGeometry args={[size * 3, 20, 20]} />
        <meshPhongMaterial
          transparent
          color={'#fff200'}
          opacity={0.15}
          emissiveIntensity={1}
          specular={'#ffffff'}
          shininess={100}
        />
      </mesh>
    </>
  );
};

export default StarMesh;
