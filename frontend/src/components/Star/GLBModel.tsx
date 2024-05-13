import { useAnimations, useGLTF } from '@react-three/drei';
import { useEffect, useRef } from 'react';
import { useFrame, useThree } from '@react-three/fiber';

const GLBModel = () => {
  const { scene, animations } = useGLTF('/img/galaxy.glb');
  const ref = useRef(null);
  const { actions } = useAnimations(animations, ref);
  console.log(actions);

  useFrame((state, delta) => {
    // console.log('state',state);
    // console.log('delta',delta);
    // ref.current.rotation.y += 0.02;
  });

  return <primitive ref={ref} scale={0.5} object={scene} position-y={0} />;
};

export default GLBModel;

