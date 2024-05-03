import React, { useCallback, useEffect, useRef } from 'react';
import * as THREE from 'three';
import { Line2 } from 'three/examples/jsm/lines/Line2.js';
import { LineGeometry } from 'three/examples/jsm/lines/LineGeometry.js';
import { LineMaterial } from 'three/examples/jsm/lines/LineMaterial.js';

import { useThree } from '@react-three/fiber';

type Props = {
  constellation: string;
  pointA: THREE.Vector3;
  pointB: THREE.Vector3;
};

const MakeConstellation = ({ constellation, pointA, pointB }: Props) => {
  const lineRef = useRef<Line2>(null);

  const { scene } = useThree();

  const handleClick = () => {
    console.log('Line clicked:', constellation);
  };

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
