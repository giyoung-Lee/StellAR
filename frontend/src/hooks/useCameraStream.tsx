import { useEffect, useState, useRef } from 'react';
import * as THREE from 'three';
import useStarStore from '../stores/starStore';

const useCameraStream = () => {
  const [videoTexture, setVideoTexture] = useState<THREE.VideoTexture | null>(null);
  const starStore = useStarStore();
  const lastOrientation = useRef<string | null>(null);

  useEffect(() => {
    if (!starStore.isARMode) {
      return;
    }

    // 비디오 스트림 초기화
    const initVideoStream = (isLandscape: boolean) => {
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
          video.play().then(() => {
            const texture = new THREE.VideoTexture(video);
            texture.minFilter = THREE.LinearFilter;
            texture.magFilter = THREE.LinearFilter;
            texture.format = THREE.RGBFormat;
            setVideoTexture(texture);
          });
        })
        .catch((error) => {
          console.error('Cannot access camera:', error);
        });
    };

    const handleOrientationChange = (event: DeviceOrientationEvent) => {
      const { gamma } = event;
      const myGamma = gamma || 0
      const currentOrientation = Math.abs(myGamma) > 45 ? 'landscape' : 'portrait';

      if (lastOrientation.current !== currentOrientation) {
        lastOrientation.current = currentOrientation;
        initVideoStream(currentOrientation === 'landscape');
      }
    };

    window.addEventListener('deviceorientation', handleOrientationChange);

    return () => {
      window.removeEventListener('deviceorientation', handleOrientationChange);
      if (videoTexture?.image instanceof HTMLVideoElement) {
        const video = videoTexture.image;
        const stream = video.srcObject as MediaStream;
        if (stream) {
          stream.getTracks().forEach(track => track.stop());
        }
        video.srcObject = null;
      }
    };
  }, [starStore.isARMode]);

  return videoTexture;
};

export default useCameraStream;
