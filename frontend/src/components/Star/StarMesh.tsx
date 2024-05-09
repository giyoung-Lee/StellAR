import { useRef } from 'react';
import * as THREE from 'three';
import { ThreeEvent } from '@react-three/fiber';
import useStarStore from '../../stores/starStore';

type Props = {
  position: THREE.Vector3;
  size: number;
  starId: string;
  spType: string | null;
};

const StarMesh = ({ position, size, starId, spType }: Props) => {
  const {
    setStarClicked,
    setStarId,
    setPlanetClicked,
    addStarToClicked,
    removeStarFromClicked,
    clickedStars,
    setZoomX,
    setZoomY,
    setZoomZ,
  } = useStarStore();

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

  const click = (event: ThreeEvent<MouseEvent>) => {
    event.stopPropagation();
    setStarId(starId);
    setStarClicked(true);
    setPlanetClicked(false);
    addStarToClicked(starId);

    const mesh = event.object as THREE.Mesh; // 타입 단언
    if (mesh.material && 'color' in mesh.material) {
      (mesh.material as THREE.MeshPhongMaterial).color.set('black');
    }

    const starPosition = event.object.position;

    console.log('별 클릭 지점' + starPosition);

    if (clickedStars.length < 1) {
      setZoomX(starPosition.x);
      setZoomY(starPosition.y);
      setZoomZ(starPosition.z);
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
