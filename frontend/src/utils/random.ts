export function getRandomInt(min: number, max: number) {
  const minCeil = Math.ceil(min);
  const maxFloor = Math.floor(max);
  return Math.floor(Math.random() * (maxFloor - minCeil)) + minCeil;
}

export function getRandomFloat(min: number, max: number) {
  return Math.random() * (max - min) + min;
}
