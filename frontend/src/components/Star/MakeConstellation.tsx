import React, { useRef } from 'react';
import { useThree, extend } from '@react-three/fiber';
import * as THREE from 'three';
import { TubeGeometry } from 'three';
import { Line2 } from 'three/examples/jsm/lines/Line2.js';
import { LineGeometry } from 'three/examples/jsm/lines/LineGeometry.js';
import { LineMaterial } from 'three/examples/jsm/lines/LineMaterial.js';
import { Vector3, CatmullRomCurve3 } from 'three';
import useConstellationStore from '../../stores/constellationStore';

extend({ Line2, LineGeometry, LineMaterial });

type Props = {
  constellation: string;
  pointA: THREE.Vector3;
  pointB: THREE.Vector3;
};

const MakeConstellation = ({ constellation, pointA, pointB }: Props) => {
  const ConstellationStore = useConstellationStore();
  const lineRef = useRef<THREE.BufferGeometry | null>(null);
  const { scene } = useThree();

  const handleClick = () => {
    // console.log('Line clicked:', constellation);
    ConstellationStore.setConstellationClicked(true);
    ConstellationStore.setConstellationName(constellation);
  };

  const path = new CatmullRomCurve3([pointA, pointB]);

  const tubeGeometry = new TubeGeometry(path, 20, 110, 6, false);
  const tubeMaterial = new THREE.MeshPhongMaterial({
    color: 0xffffff,
    specular: 0xffffff,
    opacity: 0.2,
    transparent: true,
    shininess: 100,
    flatShading: true,
  });

  // 두꺼운 클릭 영역을 생성하기 위한 투명 튜브
  const clearTubeGeometry = new TubeGeometry(path, 50, 300, 4, false);
  const clearTubeMaterial = new THREE.MeshBasicMaterial({
    color: 0x000000,
    opacity: 0,
    transparent: true,
  });

  return (
    <>
      <mesh
        geometry={tubeGeometry}
        material={tubeMaterial}
        onClick={handleClick}
        castShadow={false}
        receiveShadow={false}
        renderOrder={1}
      ></mesh>

      <mesh
        geometry={clearTubeGeometry}
        material={clearTubeMaterial}
        onClick={handleClick}
        renderOrder={2}
      ></mesh>
    </>
  );
};

export default MakeConstellation;
