import { useEffect } from 'react';
import * as THREE from 'three';

const useDeviceOrientation = (camera: THREE.Camera | null) => {
  useEffect(() => {
    if (!camera) return;

    console.log('자이로 접근 확인')
    const handleOrientation = (event: DeviceOrientationEvent) => {
      const { alpha, beta, gamma } = event;
      camera.rotation.set(
        THREE.MathUtils.degToRad(beta ?? 0),
        THREE.MathUtils.degToRad(alpha ?? 0),
        THREE.MathUtils.degToRad(-gamma ?? 0)
      );
    };

    window.addEventListener('deviceorientation', handleOrientation, true);

    return () => {
      window.removeEventListener('deviceorientation', handleOrientation);
    };
  }, [camera]);
};


export default useDeviceOrientation;
