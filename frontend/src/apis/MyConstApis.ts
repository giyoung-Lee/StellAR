import { publicRequest } from '../hooks/requestMethods';
import Swal from 'sweetalert2';

export const MakeMyConstellationApi = async (userConstellationData: UserConstellationData) => {
  return await publicRequest
    .post(`user-constellation/create`, userConstellationData)
    .then((res) => {
      Swal.fire({
        title: '성공!',
        text: '나만의 별자리가 성공적으로 생성되었습니다.',
        icon: 'success',
        confirmButtonText: '확인',
      });
      return res.data;
    })
    .catch((error) => {
      console.log(error);
      throw new Error('나만의 별자리 만들기 api 에러');
    });
};

export const GetUserConstellationApi = async (userId: string) => {
  return await publicRequest.get('user-constellation/all', {
    params: {
      userId: userId,
    },
  });
};

export const DeleteUserConstellationApi = async (
  deleteData: deleteMyConstellationApiype,
) => {
  return await publicRequest
    .delete('user-constellation/delete', {
      params: deleteData,
    })
    .then((res) => res.data);
};
