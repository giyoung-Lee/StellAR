import { useEffect, useRef, useState } from 'react';
import * as THREE from 'three';
import { ThreeEvent, useThree } from '@react-three/fiber';
import useStarStore from '../../stores/starStore';
import Swal from 'sweetalert2';
import { Instance } from '@react-three/drei';

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

  const [materialColor, setMaterialColor] = useState('#ffffff');

  useEffect(() => {
    if (!starStore.starClicked) {
      // isStarClicked가 false일 때 mesh 색상을 초기 색상으로 복구
      if (touchAreaRef.current) {
        const touchArea = touchAreaRef.current;
        (touchArea.material as THREE.MeshPhongMaterial).color.set('#ffffff');
      }
    }
  }, [starStore.starClicked, materialColor]);

  const { scene } = useThree();

  const click = (event: ThreeEvent<MouseEvent>) => {
    event.stopPropagation();
    const currentStarId = starStore.starId;
    const currentStarPosition = starStore.starPosition;

    setMaterialColor('black'); // 클릭 시 색상 변경

    // if (starStore.linkedStars.some((link) => link[1] === propstarId)) return;
    if (currentStarId !== '' && starStore.starClicked) {
      starStore.addStarToClicked([currentStarId, propstarId]);
    }

    if (
      starStore.starId === 'Jupiter' ||
      starStore.starId === 'Mars' ||
      starStore.starId === 'Mercury' ||
      starStore.starId === 'Moon' ||
      starStore.starId === 'Neptune' ||
      starStore.starId === 'Saturn' ||
      starStore.starId === 'Sun' ||
      starStore.starId === 'Uranus' ||
      starStore.starId === 'Venus'
    ) {
      Swal.fire({
        title: '클릭 실패',
        text: '행성은 별자리로 쓸 수 없어요!',
        icon: 'error',
        confirmButtonText: '확인',
      }).then(() => {
        window.location.reload();
      });
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

    const starPosition = event.object.position;
    if (!starStore.starId) {
      starStore.setZoomX(starPosition.x);
      starStore.setZoomY(starPosition.y);
      starStore.setZoomZ(starPosition.z);
    }
  };

  return (
    <>
      {/* <mesh
        ref={meshRef}
        position={position}
        castShadow={false}
        receiveShadow={false}
        onClick={click}
      > */}
      <tetrahedronGeometry args={[size, 2]} />
      <meshPhongMaterial
        specular={'white'}
        shininess={100}
        flatShading={true}
      />
      <Instance
        color={spType ? starColor[spType as string] : 'red'}
        ref={meshRef}
        position={position}
        castShadow={false}
        receiveShadow={false}
        onClick={click}
      />
      {/* </mesh> */}
      {/* 터치 영역 확장을 위한 투명 mesh입니다만 */}
      <mesh ref={touchAreaRef} position={position} onClick={click}>
        <sphereGeometry args={[size * 3, 20, 20]} />
        <meshPhongMaterial
          color={spType ? starColor[spType as string] : 'red'}
          transparent
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

