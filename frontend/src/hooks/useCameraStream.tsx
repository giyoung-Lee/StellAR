import { useEffect, useState } from 'react';
import * as THREE from 'three';

const useCameraStream = () => {
  const [videoTexture, setVideoTexture] = useState<THREE.VideoTexture | null>(null);

  useEffect(() => {
    // 화면 방향에 따라 width와 height 결정
    const setDimensions = () => {
      const isLandscape = window.matchMedia("(orientation: landscape)").matches;
      const width = isLandscape ? window.innerHeight : window.innerWidth;
      const height = isLandscape ? window.innerWidth : window.innerHeight;

      const constraints = {
        video: {
          facingMode: 'environment',
          width: { ideal: width },
          height: { ideal: height },
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
          console.log('카메라 스트림 성공');
        })
        .catch((error) => {
          console.error('카메라 접근 불가:', error);
        });
    };

    setDimensions();  // 초기 카메라 스트림 설정

    // 화면 방향 변경 감지
    window.addEventListener('resize', setDimensions);

    // 컴포넌트 정리 시 리스너 제거
    return () => {
      window.removeEventListener('resize', setDimensions);
    };
  }, []);

  return videoTexture;
};

export default useCameraStream;
