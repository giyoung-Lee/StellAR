import { publicRequest } from '../hooks/requestMethods';

export const GetLoadingMessageApi = async () => {
  return await publicRequest.get('piece/knowledge');
};
