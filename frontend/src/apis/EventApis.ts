import { publicRequest } from '../hooks/requestMethods';

export const GetEventApi = async () => {
  return await publicRequest.get('constellation/event');
};
