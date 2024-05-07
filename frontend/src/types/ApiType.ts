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

interface userConstellationApiType {
  userId: string;
  name: string;
  description: string;
  links: [];
}

interface deleteuserApiType {
  userId: string;
  constellationId: Number;
}