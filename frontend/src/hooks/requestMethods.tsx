import axios from 'axios';

const BASE_URL = 'http://k10c105.p.ssafy.io:8080/api/';

export const publicRequest = axios.create({
  baseURL: BASE_URL,
});
