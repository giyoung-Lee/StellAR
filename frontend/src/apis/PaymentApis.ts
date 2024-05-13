import { publicRequest } from '../hooks/requestMethods';

export const PostPaymentReady = async (orderData: orderApitype) => {
  return publicRequest.post(`payment/ready`, orderData).then((res) => {
    return res.data;
  });
};
