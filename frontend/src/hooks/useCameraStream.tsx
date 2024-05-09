import { useEffect, useState } from 'react';
import * as THREE from 'three';

const useCameraStream = () => {
  const [videoTexture, setVideoTexture] = useState<THREE.VideoTexture | null>(null);

  useEffect(() => {
    // 디바이스 방향에 따라 해상도를 설정하는 함수
    const adjustVideoSettings = (alpha, beta, gamma) => {
      const isLandscape = Math.abs(gamma) > 45; // gamma 각도가 45도를 넘으면 가로 모드로 간주
      const width = isLandscape ? window.innerHeight : window.innerWidth;
      const height = isLandscape ? window.innerWidth : window.innerHeight;

      const constraints = {
        video: {
          facingMode: 'environment',
          width: { ideal: width },
          height: { ideal: height }
        }
      };

      navigator.mediaDevices.getUserMedia(constraints)
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
          console.log('Camera stream adjusted for orientation.');
        })
        .catch((error) => {
          console.error('Cannot access camera:', error);
        });
    };

    const handleOrientationChange = (event:DeviceOrientationEvent) => {
      const { alpha, beta, gamma } = event;
      adjustVideoSettings(alpha, beta, gamma);
    };

    window.addEventListener('deviceorientation', handleOrientationChange);

    return () => {
      window.removeEventListener('deviceorientation', handleOrientationChange);
    };
  }, []);

  return videoTexture;
};

export default useCameraStream;
