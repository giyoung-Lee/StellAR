import React, { useEffect, useRef } from 'react';
import * as THREE from 'three';
import { getRandomInt } from '../../utils/random';
import { useFrame, useThree } from '@react-three/fiber';
import { useTexture } from '@react-three/drei';
// import { fragment, vertex } from '../../utils/shader';

const FloorMesh = () => {
  const { camera } = useThree();
  const meshRef = useRef<THREE.Mesh>(null);
  const texture = useTexture('/img/wave.jpg');

  const uniforms = useRef({
    uTexture: { value: texture },
    uTime: { value: 0 },
    uAmplitude: { value: 10.0 },
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
        gl_Position = projectionMatrix * modelViewMatrix * vec4(newPosition, 1.0);
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
    // console.log(camera.position);
  });

  return (
    <>
      <mesh ref={meshRef} position={[0, -30, 0]} rotation-x={-Math.PI / 2}>
        {/* <circleGeometry args={[30000, 200]} /> */}
        <planeGeometry args={[15000, 15000, 50, 50]} />
        <shaderMaterial
          uniforms={uniforms.current}
          // wireframe={true}
          vertexShader={vertex}
          fragmentShader={fragment}
          transparent
        />
      </mesh>
      {/* <mesh position={[100, 100, 0]} castShadow={false} receiveShadow={false}>
        <sphereGeometry args={[50]} />
        <meshStandardMaterial />
      </mesh> */}
    </>
  );
};

export default FloorMesh;
