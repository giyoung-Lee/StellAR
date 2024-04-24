import { OrbitControls } from '@react-three/drei';
import React from 'react';

const Controls = () => {
  return (
    <>
      <OrbitControls target={[0, 0, 0]} />
    </>
  );
};

export default Controls;

