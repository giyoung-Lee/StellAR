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
  const lineRef = useRef<Line2 | null>(null);

  const { scene } = useThree();

  const handleClick = () => {
    console.log('Line clicked:', constellation);
  };

  useEffect(() => {
    if (lineRef.current) {
      const lineGeometry = new LineGeometry();
      lineGeometry.setPositions([
        pointA.x,
        pointA.y,
        pointA.z,
        pointB.x,
        pointB.y,
        pointB.z,
      ]);

      const lineMaterial = new LineMaterial({
        color: 0xffffff,
        linewidth: 1.5,
        resolution: new THREE.Vector2(window.innerWidth, window.innerHeight),
      });

      lineRef.current.geometry = lineGeometry;
      lineRef.current.material = lineMaterial;
    }
  }, [pointA, pointB]);

  return (
    <>
      <primitive ref={lineRef} object={new Line2()} onClick={handleClick} />
    </>
  );
};

export default MakeConstellation;
