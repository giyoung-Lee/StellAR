import { requestPermission } from '../firebase';
import { publicRequest } from '../hooks/requestMethods';
import axios from 'axios';

// POST 요청
// 로그인
export const loginApi = async (loginData: loginApiType) => {
  const formData = new FormData();
  formData.append('userId', loginData.userId);
  formData.append('password', loginData.password);
  const token = await requestPermission();
  if (token) {
    formData.append('deviceToken', token)
  }
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

// 주소찾기
export const whereAmI = async (lat: number, lng: number) => {
  try {
    const response = await axios.get(
      `https://us1.locationiq.com/v1/reverse?key=pk.79b88e9424b3de5037b3608c0cea8016&lat=${lat}&lon=${lng}&format=json&`,
    );
    // console.log(response.data);
    return response.data;
  } catch (error) {
    console.error('Location fetching error:', error);
    return null;
  }
};