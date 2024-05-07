import React, { useRef } from 'react';

const Lights = () => {
  const lightRef = useRef(null);
  return (
    <>
      <directionalLight position={[0, -10, 0]} />
      {/* <ambientLight
        ref={lightRef}
        args={[0xffffff, 3]}
        position={[-90, -10, 10]}
        castShadow={false}
      /> */}
      <axesHelper args={[20000]} />
    </>
  );
};

export default Lights;

