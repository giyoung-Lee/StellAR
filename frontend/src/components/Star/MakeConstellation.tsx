import React, { useEffect, useRef } from 'react';
import * as THREE from 'three';

type Props = {
  pointA: THREE.Vector3;
  pointB: THREE.Vector3;
};

const MakeConstellation = ({ pointA, pointB }: Props) => {
  const meshRef = useRef<THREE.Mesh>(null);
  // let t = 0;
  // let direction = 1;

  // function animate() {
  //   requestAnimationFrame(animate);

  //   t += 0.01 * direction;
  //   if (t > 1 || t < 0) direction *= -1;
  //   if (meshRef.current) {
  //     meshRef.current.position.lerpVectors(pointA, pointB, t);
  //   }
  // }

  // useEffect(() => {
  //   console.log(pointA, pointB);
  //   animate();
  // }, []);

  return (
    <line
    //   ref={meshRef}
    // position={pointA}
    // castShadow={false}
    // receiveShadow={false}
    >
      <bufferGeometry
        attach="geometry"
        ref={(geometry) => {
          if (geometry && pointA && pointB) {
            const vectorA = new THREE.Vector3(pointA.x, pointA.y, pointA.z);
            const vectorB = new THREE.Vector3(pointB.x, pointB.y, pointB.z);
            const points = [vectorA, vectorB];
            geometry.setFromPoints(points);
            geometry.setAttribute(
              'lineWidth',
              new THREE.Float32BufferAttribute([1000, 1000], 1),
            );
          }
        }}
      />

      <lineBasicMaterial color={'white'} />
    </line>
  );
};

export default MakeConstellation;

