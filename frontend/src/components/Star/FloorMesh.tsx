import React, { useEffect, useRef } from 'react';
import * as THREE from 'three';
import { useFrame, useThree } from '@react-three/fiber';
import { useTexture } from '@react-three/drei';

const FloorMesh = () => {
  const meshRef = useRef<THREE.Mesh>(null);
  const texture = useTexture('/img/wave.jpg');

  const uniforms = useRef({
    uTexture: { value: texture },
    uTime: { value: 0 },
    uAmplitude: { value: 20.0 },
    uWaveLength: { value: 0.5 },
    opacity: { value: 0.7 },
  });

  const vertex = `
    uniform float uAmplitude;
    uniform float uWaveLength;
    uniform float uTime;
    varying vec2 vUv;

    void main() {
        vUv = uv;
        vec3 newPosition = position;
        float wave = sin(position.x * uWaveLength + uTime) * uAmplitude;
        newPosition.z += wave;
        gl_Position = projectionMatrix * modelViewMatrix * vec4(newPosition, 2.0);
    }
  `;

  const fragment = `
  uniform sampler2D uTexture;
  uniform float opacity;
  varying vec2 vUv;

  void main() { 
    vec4 color = texture2D(uTexture, vUv);
    color.a *= opacity;
    gl_FragColor = color;
}
  `;

  useFrame(({ clock }) => {
    uniforms.current.uTime.value = clock.getElapsedTime() * 4.0;
  });

  return (
    <>
      <mesh ref={meshRef} position={[0, -50, 0]} rotation-x={-Math.PI / 2}>
        <planeGeometry args={[100000, 100000, 1000, 1000]} />
        <shaderMaterial
          uniforms={uniforms.current}
          // wireframe={true}
          vertexShader={vertex}
          fragmentShader={fragment}
          transparent
          polygonOffset={true}
          polygonOffsetFactor={0.1}
          polygonOffsetUnits={0.1}
        />
      </mesh>
    </>
  );
};

export default FloorMesh;
