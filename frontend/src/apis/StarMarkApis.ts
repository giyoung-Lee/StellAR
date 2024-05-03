import { publicRequest } from '../hooks/requestMethods';
import useStarStore from '../stores/starStore';

export const PostStarMark = async (markData: markApiType) => {
  const formData = new FormData();
  formData.append('userId', markData.userId);
  formData.append('starId', markData.starId);
  formData.append('bookmarkName', markData.bookmarkName);

  return publicRequest
    .post(`bookmark`, formData)
    .then((res) => res.data)
    .catch((error) => {
      console.log(error);
      throw new Error('별마크 api 에러');
    });
};

export const GetStarMark = async (userId: string) => {
  return await publicRequest.get('bookmark', {
    params: {
      userId: userId,
    },
  });
  // .then((res) => useStarStore.getState().setMarkedStars(res.data));
};

export const DeleteStarMark = async (deleteData: deleteMarkApiType) => {
  return await publicRequest.delete('bookmark/delete', {
    params: deleteData,
  });
};
