import { useEffect, useState, useRef } from 'react';
import * as THREE from 'three';
import useStarStore from '../stores/starStore';

const useCameraStream = () => {
    const [videoTexture, setVideoTexture] = useState<THREE.VideoTexture | null>(null);
    const [isLandscape, setIsLandscape] = useState(false);
    const starStore = useStarStore();
    const prevIsLandscape = useRef(isLandscape);

    const adjustVideoSettings = (width: number, height: number) => {
        console.log('해상도 조정:', width, height);

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
            })
            .catch((error) => {
                console.error('Cannot access camera:', error);
            });
    };

    useEffect(() => {
        if (!starStore.isARMode) {
            return;
        }

        const handleOrientationChange = (event: DeviceOrientationEvent) => {
            const gamma = event.gamma || 0; // gamma가 null일 경우 0으로 처리
            const currentIsLandscape = Math.abs(gamma) > 45;
            setIsLandscape(currentIsLandscape);
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

    useEffect(() => {
        if (prevIsLandscape.current !== isLandscape) {
            prevIsLandscape.current = isLandscape;
            const width = isLandscape ? window.innerWidth : window.innerHeight;
            const height = isLandscape ? window.innerHeight : window.innerWidth;
            adjustVideoSettings(width, height);
        }
    }, [isLandscape]);

    return videoTexture;
};

export default useCameraStream;
