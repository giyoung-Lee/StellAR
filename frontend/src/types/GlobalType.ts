interface ConstellationData {
  [key: string]: string[][]; // 각 키는 문자열 배열의 배열을 값으로 가짐
}

interface StarData {
  starId: string;
  starType: string;
  calX: number;
  calY: number;
  calZ: number;
  constellation: string;
  parallax: string;
  spType: string;
  hd: string;
  magV: string;
  nomalizedMagV: number;
  RA: string;
  Declination: string;
  hourRA: number;
  degreeDEC: number;
  PMRA: string;
  PMDEC: string;
}

interface StarDataMap {
  [key: string]: StarData;
}

interface PlanetData {
  planetId: string;
  planetDEC: string;
  planetMagV: string;
  planetRA: string;
  hourRA: number;
  degreeDEC: number;
  calX: number;
  calY: number;
  calZ: number;
  nomalizedMagV: number;
}
interface PlanetPosition {
  planetId: string;
  calX: number;
  calY: number;
  calZ: number;
  nomalizedMagV: number;
}

interface Position {
  lat: number;
  lng: number;
}
