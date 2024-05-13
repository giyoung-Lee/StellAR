import { useEffect, useState } from 'react';
import * as THREE from 'three';
import useStarStore from '../stores/starStore';

const useCameraStream = () => {
  const starStore = useStarStore();
  const [videoTexture, setVideoTexture] = useState<THREE.VideoTexture | null>(
    null,
  );
  const [isLandscape, setIsLandscape] = useState<boolean>(false);
  const [width, setWidth] = useState<number>(window.innerWidth);
  const [height, setHeight] = useState<number>(window.innerHeight);

  useEffect(() => {
    if (!starStore.isARMode) {
      return;
    }
    
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
        console.error('Cannot access camera:', error);
      });

    // 디바이스 방향에 따라 해상도를 설정하는 함수
    const adjustVideoSettings = (_alpha: number, _beta: number, gamma: number) => {
      setIsLandscape(Math.abs(gamma) > 45); // gamma 각도가 45도를 넘으면 가로 모드로 간주
      setWidth(isLandscape ? window.innerHeight : window.innerWidth);
      setHeight(isLandscape ? window.innerWidth : window.innerHeight);
    };

    // 각도 변경 시 자이로 값 호출
    const handleOrientationChange = (event:DeviceOrientationEvent) => {
      const { alpha, beta, gamma } = event;
      adjustVideoSettings(alpha!, beta!, gamma!);
    };

    window.addEventListener('deviceorientation', handleOrientationChange);

    return () => {
      window.removeEventListener('deviceorientation', handleOrientationChange);
      if (videoTexture?.image instanceof HTMLVideoElement) {
        const video = videoTexture.image;
        const stream = video.srcObject as MediaStream;
        if (stream) {
          stream.getTracks().forEach((track) => track.stop());
        }
        video.srcObject = null;
      }
    };
  }, [starStore.isARMode, isLandscape]);

  return videoTexture;
};

export default useCameraStream;
