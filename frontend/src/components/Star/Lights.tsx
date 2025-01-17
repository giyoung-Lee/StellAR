import { useRef } from 'react';

const Lights = () => {
  const lightRef = useRef(null);
  return (
    <>
      <directionalLight
        color={'white'}
        position={[-0.75, -1, 0.8]}
        intensity={5}
      />
      <ambientLight
        color={'white'}
        ref={lightRef}
        args={[0xffffff, 3]}
        position={[-90, -10, 10]}
        castShadow={false}
      />
      {/* <axesHelper args={[20000]} /> */}
    </>
  );
};

export default Lights;

