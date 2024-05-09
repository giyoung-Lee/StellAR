import { useEffect, useState } from 'react';
import * as THREE from 'three';

const useCameraStream = () => {
  const [videoTexture, setVideoTexture] = useState<THREE.VideoTexture | null>(
    null,
  );
  const [dimensions, setDimensions] = useState({
    width: window.innerWidth,
    height: window.innerHeight,
  });

  useEffect(() => {
    let video: HTMLVideoElement;
    let stream: MediaStream;

    const updateVideoSize = () => {
      const { width, height } = dimensions;
      const constraints: MediaStreamConstraints = {
        video: {
          facingMode: 'environment',
          width: { ideal: width },
          height: { ideal: height },
        },
      };

      if (stream) {
        const track = stream.getVideoTracks()[0];
        track
          .applyConstraints(constraints.video as MediaTrackConstraints)
          .then(() => console.log('카메라 해상도 변경 성공'))
          .catch((error) => console.error('카메라 해상도 변경 실패:', error));
      }
    };

    const handleOrientation = (event: DeviceOrientationEvent) => {
      const { alpha, beta, gamma } = event;
      console.log(
        `Device orientation: alpha ${alpha}, beta ${beta}, gamma ${gamma}`,
      );

      if (
        (beta > -180 && beta < -150) ||
        (beta > -30 && beta < 30) ||
        (beta > 150 && beta < 180)
      ) {
        setDimensions({ width: window.innerHeight, height: window.innerWidth });
      } else {
        setDimensions({ width: window.innerWidth, height: window.innerHeight });
      }
    };

    const initCamera = () => {
      navigator.mediaDevices
        .getUserMedia({
          video: {
            facingMode: 'environment',
            width: { ideal: dimensions.width },
            height: { ideal: dimensions.height },
          },
        })
        .then((mediaStream) => {
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
        })
        .catch((error) => {
          console.error('카메라 접근 불가:', error);
        });
    };

    initCamera();
    window.addEventListener('resize', updateVideoSize);
    window.addEventListener('deviceorientation', handleOrientation);

    // 컴포넌트 언마운트 시 리소스 정리
    return () => {
      window.removeEventListener('resize', updateVideoSize);
      window.removeEventListener('deviceorientation', handleOrientation);
      if (stream) {
        stream.getTracks().forEach((track) => track.stop());
      }
      if (video) {
        video.pause();
        video.srcObject = null;
      }
    };
  }, [dimensions]);

  return videoTexture;
};

export default useCameraStream;
