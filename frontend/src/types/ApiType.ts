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

interface MyConstellationApiType {
  userId: string;
  constellationId:Number;
  name: string;
  description: string;
  links: [];
}

interface deleteMyConstellationApiype {
  userId: string;
  constellationId: Number;
}