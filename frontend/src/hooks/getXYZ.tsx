import * as Astronomy from 'astronomy-engine';

const getXYZ = (
  ra: number,
  dec: number,
  lattitude: number,
  longitude: number,
) => {
  const time = new Date();
  const observer = new Astronomy.Observer(lattitude, longitude, 0);
  const horizontal = Astronomy.Horizon(time, observer, ra, dec, 'normal');
  const radius = 1;
  const azimuthRad = (horizontal.azimuth * Math.PI) / 180;
  const altitudeRad = (horizontal.altitude * Math.PI) / 180;
  const x = radius * Math.cos(altitudeRad) * Math.sin(azimuthRad);
  const y = radius * Math.cos(altitudeRad) * Math.cos(azimuthRad);
  const z = radius * Math.sin(altitudeRad);
  return { x: x, y: y, z: z };
};

export default getXYZ;
