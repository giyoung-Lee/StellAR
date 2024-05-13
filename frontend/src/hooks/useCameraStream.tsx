import { useEffect, useState } from 'react';
import * as THREE from 'three';
import useStarStore from '../stores/starStore';
import { debounce } from 'lodash';

const useCameraStream = () => {
  const [videoTexture, setVideoTexture] = useState<THREE.VideoTexture | null>(null);
  const starStore = useStarStore();

  useEffect(() => {
    if (!starStore.isARMode) {
      return;
    }

    let video = document.createElement('video');
    const constraints = {
      video: {
        facingMode: 'environment'
      }
    };

    navigator.mediaDevices.getUserMedia(constraints)
      .then((stream) => {
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
        console.error('Cannot access camera:', error);
      });

    const handleOrientationChange = debounce((event) => {
      const { gamma } = event;
      const isLandscape = Math.abs(gamma) > 45;
      video.style.width = isLandscape ? '100vh' : '100vw';
      video.style.height = isLandscape ? '100vw' : '100vh';
    }, 250); // 250ms 딜레이로 debounce 처리

    window.addEventListener('deviceorientation', handleOrientationChange);

    return () => {
      window.removeEventListener('deviceorientation', handleOrientationChange);
      if (videoTexture?.image instanceof HTMLVideoElement) {
        const stream = video.srcObject as MediaStream;
        stream?.getTracks().forEach(track => track.stop());
        video.srcObject = null;
      }
      video.srcObject = null; // 비디오 요소 참조 제거
    };
  }, [starStore.isARMode]);

  return videoTexture;
};

export default useCameraStream;
