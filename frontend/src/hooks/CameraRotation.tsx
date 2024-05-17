import { useRef } from 'react';
import { useFrame } from '@react-three/fiber';
import { PerspectiveCamera } from '@react-three/drei';
import * as THREE from 'three';
import useUserStore from '../stores/userStore';

export function CameraRotation() {
  const cameraRef = useRef<THREE.PerspectiveCamera>(null);
  const userStore = useUserStore(); // userStore를 사용하여 상태 가져오기

  useFrame(() => {
    if (cameraRef.current && userStore.isForward) { // userStore.isForward가 true일 때만 회전
      // 카메라 회전 각도 업데이트
      cameraRef.current.rotation.y += 0.01;
    }
  });

  return (
    <PerspectiveCamera
      ref={cameraRef}
      position={[0, 0, 5]} // 카메라 위치
      fov={75} // 시야(Field of View)
      aspect={window.innerWidth / window.innerHeight} // 종횡비
      near={0.1} // 가까운 클리핑 평면
      far={1000} // 먼 클리핑 평면
    />
  );
}
