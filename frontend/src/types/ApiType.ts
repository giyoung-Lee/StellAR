interface loginApiType {
  userId: string;
  password: string;
}

interface signupApiType {
  userId: string;
  password: string;
}

interface markApiType {
  userId: string;
  starId: string;
  bookmarkName: string;
}

interface deleteMarkApiType {
  userId: string;
  starId: string;
}

interface UserConstellationData {
  userId: string;
  name: string;
  description: string;
  links: string[][];
}

interface deleteMyConstellationApitype {
  userId: string;
  constellationId: number;
}

