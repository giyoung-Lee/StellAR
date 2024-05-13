interface MyConstellation {
  userConstellationId: number;
  name: string;
  description: string;
  createTime: string;
  links: {
    userConstellationId: number;
    startStar: string;
    endStar: string;
  }[];
  hourRA: number;
  degreeDEC: number;
  nomalizedMagV: number;
}
