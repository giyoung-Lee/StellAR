import { Environment, Sphere } from '@react-three/drei';
import { Gradient, LayerMaterial } from 'lamina';
import * as THREE from 'three';

export default function Background() {
  //   const colorA = '#1b1b9e';
  const colorA = '#130e4c';
  const colorB = '#1b1b9e';
  const start = 0.8;
  const end = -0.8;

  return (
    <>
      <Sphere scale={[27000, 27000, 27000]}>
        <LayerMaterial
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
        </LayerMaterial>
      </Sphere>
      {/* <Environment resolution={256}>
        <Sphere
          scale={[100, 100, 100]}
          rotation-y={Math.PI / 2}
          rotation-x={Math.PI} // 구름 아래 부분이 어둡게 보여 자연스러움
        >
          <LayerMaterial color={'#ffffff'} side={THREE.BackSide}>
            <Gradient
              colorA={colorA}
              colorB={colorB}
              axes={'y'}
              start={start}
              end={end}
            />
          </LayerMaterial>
        </Sphere>
      </Environment> */}
    </>
  );
}
