import { Environment, Sphere } from '@react-three/drei';
import { Gradient, LayerMaterial } from 'lamina';
import { useRef } from 'react';
import * as THREE from 'three';

const vertexShader = `
  varying vec2 vUv;
  void main() {
    vUv = uv;
    gl_Position = projectionMatrix * modelViewMatrix * vec4(position, 1.0);
  }
`;

const fragmentShader = `
  uniform float time;
  varying vec2 vUv;
  void main() {
    vec3 colorA = vec3(0.75, 0.315, 1.298); // 진한 파랑
    vec3 colorB = vec3(0.075, 0.015, 0.298);
    vec3 colorC = vec3(0.075, 0.015, 0.298);
    float mixFactor = smoothstep(0.0, 1.5, vUv.y);
    vec3 color = mix(colorA, colorB, mixFactor);
    color = mix(color, colorC, mixFactor * mixFactor); // 두 번째 mix로 중간 색상과 부드럽게 연결
    gl_FragColor = vec4(color, 1.0);
  }
`;

export default function Background() {
  //   const colorA = '#1b1b9e';
  const colorA = '#2c185f';
  const colorB = '#8a2fcf';
  // const colorB = '#00229e';
  const start = 0.5;
  const end = -0.5;

  return (
    <>
      <Sphere args={[27000, 100, 100]}>
        <shaderMaterial
          vertexShader={vertexShader}
          fragmentShader={fragmentShader}
          uniforms={{
            time: { value: 0 },
          }}
          side={THREE.BackSide}
          polygonOffset={true}
          polygonOffsetFactor={1}
          polygonOffsetUnits={-0.1}
        />
        {/* <LayerMaterial
          color={'#ffffff'}
          side={THREE.BackSide}
          polygonOffset={true}
          polygonOffsetFactor={-1}
          polygonOffsetUnits={-1}
        >
          <Gradient
            colorA={colorA}
            colorB={colorB}
            axes={'y'}
            start={start}
            end={end}
          />
        </LayerMaterial> */}
      </Sphere>
    </>
  );
}
