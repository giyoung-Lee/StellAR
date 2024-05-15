import { useAnimations, useGLTF } from '@react-three/drei';
import { useRef } from 'react';

const GLBModel = () => {
  const { scene, animations } = useGLTF('/img/galaxy.glb');
  const ref = useRef(null);
  const { actions } = useAnimations(animations, ref);
  console.log(actions);

  return <primitive ref={ref} scale={0.5} object={scene} position-y={0} />;
};

export default GLBModel;

