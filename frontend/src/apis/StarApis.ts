import { publicRequest } from '../hooks/requestMethods';

export const GetStars = async () => {
  return await publicRequest.get('star/all');
};
