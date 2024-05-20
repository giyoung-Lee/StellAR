import { publicRequest } from '../hooks/requestMethods';

export const GetProductsApi = async () => {
  return await publicRequest.get('payment/products');
};

export const PostPaymentReady = async (orderData: orderApitype) => {
  return publicRequest.post(`payment/ready`, orderData).then((res) => {
    return res.data;
  });
};

export const PostPaymentSuccess = async (paymentData: paymentApitype) => {
  return publicRequest.post(`payment/success`, paymentData).then((res) => {
    return res.data;
  });
};
