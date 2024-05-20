import { useEffect, useState } from 'react';
import * as THREE from 'three';
import useStarStore from '../stores/starStore';
import useUserStore from '../stores/userStore';

const useCameraStream = () => {
  const starStore = useStarStore();
  const userStore = useUserStore();
  const [videoTexture, setVideoTexture] = useState<THREE.VideoTexture | null>(
    null,
  );

  useEffect(() => {
    if (!starStore.isARMode) {
      return;
    }
    const width = userStore.isLandscape ? window.innerWidth : window.innerHeight;
    const height = userStore.isLandscape ? window.innerHeight : window.innerWidth;
   
    const constraints = {
      video: {
        facingMode: 'environment',
        width: { ideal: width },
        height: { ideal: height },
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
  }, [starStore.isARMode, userStore.isLandscape, window.innerWidth, window.innerHeight]);

  return videoTexture;
};

export default useCameraStream;
