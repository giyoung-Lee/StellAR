import React, { useRef } from 'react';

const Lights = () => {
  const lightRef = useRef(null);
  return (
    <>
      <directionalLight
        color={'0x11E8BB'}
        position={[0.75, 1, 0.5]}
        intensity={1}
      />
      <directionalLight
        color={'0x8200C9'}
        position={[-0.75, -1, 0.5]}
        intensity={1}
      />
      <ambientLight
        color={'0x999999'}
        ref={lightRef}
        args={[0xffffff, 3]}
        position={[-90, -10, 10]}
        castShadow={false}
      />
      <axesHelper args={[20000]} />
    </>
  );
};

export default Lights;
