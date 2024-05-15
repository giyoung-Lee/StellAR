import { useEffect, useState } from 'react';
import * as THREE from 'three';
import useStarStore from '../stores/starStore';

const useCameraStream = () => {
  const starStore = useStarStore();
  const [videoTexture, setVideoTexture] = useState<THREE.VideoTexture | null>(
    null,
  );

  useEffect(() => {
    if (!starStore.isARMode) {
      return;
    }

    const constraints = {
      video: {
        facingMode: 'environment',
        width: { ideal: 1024 },
        height: { ideal: 1024 },
      },
    };

    navigator.mediaDevices
      .getUserMedia(constraints)
      .then((stream) => {
        const video = document.createElement('video');
        video.srcObject = stream;
        video.autoplay = true;
        video.play();

        const texture = new THREE.VideoTexture(video);
        texture.minFilter = THREE.LinearFilter;
        texture.magFilter = THREE.LinearFilter;
        texture.format = THREE.RGBFormat;

        setVideoTexture(texture);
      })
      .catch((error) => {
        console.error('카메라 접근 불가:', error);
      });
  }, [starStore.isARMode]);

  return videoTexture;
};

export default useCameraStream;
