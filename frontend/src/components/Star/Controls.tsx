import { OrbitControls } from '@react-three/drei';
import React from 'react';

const Controls = () => {
  return (
    <>
      <OrbitControls
        target={[0, 0, 0]}
        rotateSpeed={-0.25}
        zoomSpeed={10}
        // enableZoom={false}
      />
    </>
  );
};

export default Controls;
