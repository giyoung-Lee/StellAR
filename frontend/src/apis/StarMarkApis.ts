import { publicRequest } from '../hooks/requestMethods';
import Swal from 'sweetalert2';

export const PostStarMark = async (markData: markApiType) => {
  return publicRequest
    .post(`bookmark/create`, markData)
    .then((res) => {
      Swal.fire({
        title: '성공!',
        text: '별마크가 성공적으로 생성되었습니다.',
        icon: 'success',
        confirmButtonText: '확인'
      });
      return res.data;
    })
    .catch((error) => {
      console.log(error);
      throw new Error('별마크 API 에러');
    });
};

export const GetStarMark = async (userId: string) => {
  return await publicRequest.get('bookmark/all', {
    params: {
      userId: userId,
    },
  });
  // .then((res) => useStarStore.getState().setMarkedStars(res.data));
};

export const DeleteStarMark = async (deleteData: deleteMarkApiType) => {
  return await publicRequest
    .delete('bookmark/delete', {
      params: deleteData,
    })
    .then((res) => res.data);
};
