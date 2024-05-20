import { useFrame, useThree } from '@react-three/fiber';
import * as THREE from 'three';
import useStarStore from '../stores/starStore';

export function CameraAnimator() {
  const { camera } = useThree();
  const starStore = useStarStore();

  useFrame(() => {
    if (starStore.starClicked && starStore.linkedStars.length < 1) {
      const targetPosition = new THREE.Vector3(
        starStore.zoomX,
        starStore.zoomY,
        starStore.zoomZ,
      );
      camera.position.lerp(targetPosition, 0.02);
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
      const targetPosition = new THREE.Vector3(
        starStore.zoomX,
        starStore.zoomY,
        starStore.zoomZ,
      );
      camera.position.lerp(targetPosition, 0.02);
    }
  });

  return null;
}
