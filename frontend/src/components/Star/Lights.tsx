import React, { useRef } from 'react';

const Lights = () => {
  const lightRef = useRef(null);
  return (
    <>
      <directionalLight
        ref={lightRef}
        args={[0xffffff, 5]}
        position={[-90, -10, 10]}
        castShadow={false}
      />
      <axesHelper args={[20000]} />
    </>
  );
};

export default Lights;
