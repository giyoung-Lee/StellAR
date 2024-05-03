import React, { useRef } from 'react';
import { useThree, extend } from '@react-three/fiber';
import * as THREE from 'three';
import { TubeGeometry } from 'three';
import { Line2 } from 'three/examples/jsm/lines/Line2.js';
import { LineGeometry } from 'three/examples/jsm/lines/LineGeometry.js';
import { LineMaterial } from 'three/examples/jsm/lines/LineMaterial.js';
import { Vector3, CatmullRomCurve3 } from 'three';

extend({ Line2, LineGeometry, LineMaterial });

type Props = {
  constellation: string;
  pointA: THREE.Vector3;
  pointB: THREE.Vector3;
};

const MakeConstellation = ({ constellation, pointA, pointB }: Props) => {
  const lineRef = useRef<THREE.BufferGeometry | null>(null);
  const { scene } = useThree();

  const handleClick = () => {
    console.log('Line clicked:', constellation);
  };

  // 두꺼운 클릭 영역을 생성하기 위한 투명 튜브
  const path = new CatmullRomCurve3([pointA, pointB]);
  const tubeGeometry = new TubeGeometry(path, 20, 130, 8, false);
  const tubeMaterial = new THREE.MeshBasicMaterial({
    color: 0xffffff,
    opacity: 0.2,
    transparent: true,
  });

  return (
    <mesh geometry={tubeGeometry} material={tubeMaterial} onClick={handleClick}>
      <line>
        <bufferGeometry attach="geometry" ref={lineRef} />
        <lineBasicMaterial color={'white'} />
      </line>
    </mesh>
  );
};

export default MakeConstellation;
