import { Html } from '@react-three/drei';
import { useState } from 'react';
import * as d from '../style/DrawCallCounter';
import { useFrame, useThree } from '@react-three/fiber';

const DrawCallCounter = () => {
  const { gl } = useThree();
  const [count, setCount] = useState(0);
  gl.info.autoReset = false;

  useFrame(() => {
    setCount(gl.info.render.calls);
    console.log(gl.info.render.calls);
    gl.info.reset();
  });
  return (
    <Html>
      <d.Calls className="calls">Calls: {count}</d.Calls>
    </Html>
  );
};

export default DrawCallCounter;

