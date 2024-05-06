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

export const GetUserConstellation = async (userId: string) => {
  return await publicRequest.get('user-constellation/all', {
    params: {
      userId: userId,
    },
  })
};

export const MakeUserConstellation = async (userConstellationData: userConstellationApiType) => {
  const formData = new FormData();
  formData.append('userId', userConstellationData.userId);
  formData.append('name', userConstellationData.name);
  formData.append('description', userConstellationData.description);
  // 'links'가 배열이기 때문에 JSON 문자열로 변환하여 추가
  if (userConstellationData.links) {
    formData.append('links', JSON.stringify(userConstellationData.links));
  }

  return await publicRequest
    .post(`user-constellation/create`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      }
    })
    .then((res) => res.data)
    .catch((error) => {
      console.log(error);
      throw new Error('나만의 별자리 만들기 api 에러');
    });
};
