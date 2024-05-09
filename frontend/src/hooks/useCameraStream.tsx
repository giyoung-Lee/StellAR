import { useEffect, useState } from 'react';
import * as THREE from 'three';

const useCameraStream = () => {
  const [videoTexture, setVideoTexture] = useState<THREE.VideoTexture | null>(null);

  useEffect(() => {
    let video: HTMLVideoElement;
    let stream: MediaStream;

    const updateVideoSize = () => {
      const width = window.innerWidth;
      const height = window.innerHeight;

      const constraints: MediaStreamConstraints = {
        video: {
          facingMode: 'environment',
          width: { ideal: width },
          height: { ideal: height },
        }
      };

      if (stream) {
        const track = stream.getVideoTracks()[0];
        track.applyConstraints(constraints.video as MediaTrackConstraints)
          .then(() => console.log("카메라 해상도 변경 성공"))
          .catch((error) => console.error("카메라 해상도 변경 실패:", error));
      }
    };

    const initCamera = () => {
      navigator.mediaDevices.getUserMedia({
        video: {
          facingMode: 'environment',
          width: { ideal: window.innerWidth },
          height: { ideal: window.innerHeight }
        }
      }).then((mediaStream) => {
        video = document.createElement('video');
        video.srcObject = mediaStream;
        video.autoplay = true;
        video.play();

        const texture = new THREE.VideoTexture(video);
        texture.minFilter = THREE.LinearFilter;
        texture.magFilter = THREE.LinearFilter;
        texture.format = THREE.RGBFormat;

        setVideoTexture(texture);
        stream = mediaStream;
        // console.log('카메라 스트림 성공');
      }).catch((error) => {
        console.error('카메라 접근 불가:', error);
      });
    };

    initCamera();
    window.addEventListener('resize', updateVideoSize);

    // 컴포넌트 언마운트 시 리소스 정리
    return () => {
      window.removeEventListener('resize', updateVideoSize);
      if (stream) {
        stream.getTracks().forEach(track => track.stop());
      }
      if (video) {
        video.pause();
        video.srcObject = null;
      }
    };
  }, []);

  return videoTexture;
};

export default useCameraStream;
