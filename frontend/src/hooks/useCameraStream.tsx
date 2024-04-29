import { useEffect } from 'react';
import * as THREE from 'three';

const useCameraStream = (): THREE.VideoTexture | null => {
  useEffect(() => {
    navigator.mediaDevices.getUserMedia({ video: true })
      .then((stream) => {
        const video = document.createElement('video');
        video.srcObject = stream;
        video.play();

        const texture = new THREE.VideoTexture(video);
        texture.minFilter = THREE.LinearFilter;
        texture.magFilter = THREE.LinearFilter;
        texture.format = THREE.RGBFormat;
        // Texture 설정을 완료
      })
      .catch((error) => {
        console.error('Unable to access camera:', error);
      });
  }, []);

  return null; // 실제 사용에서는 texture 객체를 반환할 수 있습니다.
};

export default useCameraStream;
