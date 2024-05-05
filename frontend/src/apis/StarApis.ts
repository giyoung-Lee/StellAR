import { publicRequest } from '../hooks/requestMethods';

export const GetStars = async (maxMagv: string) => {
  return await publicRequest.get('star/all', {
    params: {
      maxMagv: maxMagv,
    },
  });
};

export const GetConstellation = async (type: string) => {
  return await publicRequest.get('constellation/link', {
    params: {
      constellationType: type,
    },
  });
};

export const GetConstellationDetail = async (constellationId: string) => {
  return await publicRequest.get('/constellation/find', {
    params: {
      constellationId: constellationId,
    },
  });
};

export const GetPlanets = async () => {
  return await publicRequest.get('star/planet');
};

