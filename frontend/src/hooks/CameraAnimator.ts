import { useFrame, useThree } from '@react-three/fiber';
import * as THREE from 'three';
import useStarStore from '../stores/starStore';

export function CameraAnimator() {
  const { camera } = useThree();
  const starStore = useStarStore();

  useFrame(() => {
    if (starStore.starClicked && starStore.linkedStars.length < 1) {
        const targetPosition = new THREE.Vector3(starStore.zoomX, starStore.zoomY, starStore.zoomZ);
        camera.position.lerp(targetPosition, 0.02);
    }
  });

  return null;
}
