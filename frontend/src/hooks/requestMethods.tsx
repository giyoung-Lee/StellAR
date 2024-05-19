import axios from 'axios';

const BASE_URL = 'https://k10c105.p.ssafy.io/api/';

export const publicRequest = axios.create({
  baseURL: BASE_URL,
});
