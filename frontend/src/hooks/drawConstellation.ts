import * as THREE from 'three';

const drawConstellation = (pointA: THREE.Vector3, pointB: THREE.Vector3) => {
  const scene = new THREE.Scene();

  const geometry = new THREE.SphereGeometry(5, 32, 32);
  const material = new THREE.MeshBasicMaterial({ color: 0xff0000 });
  const sphere = new THREE.Mesh(geometry, material);
  scene.add(sphere);

  let t = 0;
  let direction = 1;

  function animate() {
    requestAnimationFrame(animate);

    t += 0.01 * direction;
    if (t > 1 || t < 0) direction *= -1;

    sphere.position.lerpVectors(pointA, pointB, t);
  }

  animate();
};

export default drawConstellation;
