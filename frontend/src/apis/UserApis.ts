import { publicRequest } from '../hooks/requestMethods';

// POST 요청
// 로그인
export const loginApi = async (loginData: loginApiType) => {
  const formData = new FormData();
  formData.append('userId', loginData.userId);
  formData.append('password', loginData.password);

  return publicRequest
    .post(`user/login`, formData)
    .then((res) => res.data)
    .catch((error) => {
      console.log(error);
      throw new Error('로그인 api 에러');
    });
};


// 회원가입
export const signupApi = async (signupData: signupApiType) => {
  const formData = new FormData();
  formData.append('userId', signupData.userId);
  formData.append('password', signupData.password);

  return publicRequest
    .post(`user/signup`, formData)
    .then((res) => res.data)
    .catch((error) => {
      console.log(error);
      throw new Error('회원가입 api 에러');
    });
};
