import { useEffect } from 'react';
import * as THREE from 'three';

interface DeviceOrientationProps {
  camera: THREE.Camera | null;
}

const useDeviceOrientation = ({ camera }: DeviceOrientationProps): void => {
  useEffect(() => {
    const handleOrientation = (event: DeviceOrientationEvent) => {
      const alpha = event.alpha ?? 0; 
      const beta = event.beta ?? 0;   
      const gamma = event.gamma ?? 0; 

      if (camera) {
        camera.rotation.set(
          THREE.MathUtils.degToRad(beta),
          THREE.MathUtils.degToRad(alpha),
          THREE.MathUtils.degToRad(-gamma)
        );
      }
    };

    window.addEventListener('deviceorientation', handleOrientation, true);

    return () => {
      window.removeEventListener('deviceorientation', handleOrientation);
    };
  }, [camera]);
};

export default useDeviceOrientation;
