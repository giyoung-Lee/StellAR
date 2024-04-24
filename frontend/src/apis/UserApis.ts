import { publicRequest } from '../hooks/requestMethods';

// POST 요청
// 로그인
export const loginApi = async (loginData: loginApiType) => {
  return publicRequest
    .post(`user/login`, loginData)
    .then((res) => res.data)
    .catch((error) => {
      console.log(error);
      throw new Error('로그인 api 에러');
    });
};

// 회원가입
export const signupApi = async (signupData: signupApiType) => {
  return publicRequest
    .post(`user/signup`, signupData)
    .then((res) => res.data)
    .catch((error) => {
      console.log(error);
      throw new Error('회원가입 api 에러');
    });
};
