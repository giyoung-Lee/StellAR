import { useEffect, useState } from 'react';
import * as THREE from 'three';

const useCameraStream = () => {
  const [videoTexture, setVideoTexture] = useState<THREE.VideoTexture | null>(
    null,
  );

  useEffect(() => {
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
        console.log('카메라 스트림 성공');
      })
      .catch((error) => {
        console.error('카메라 접근 불가:', error);
      });
  }, []);

  return videoTexture;
};

export default useCameraStream;