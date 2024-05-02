import { publicRequest } from '../hooks/requestMethods';

export const GetStars = async () => {
  return await publicRequest.get('star/all');
};

export const GetConstellation = async (type: string) => {
  return await publicRequest.get('constellation/link', {
    params: {
      constellationType: type,
    },
  });
};

export const GetPlanets = async () => {
  return await publicRequest.get('star/planet');
};
