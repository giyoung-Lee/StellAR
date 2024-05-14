import create from 'zustand';
import { persist } from 'zustand/middleware';

interface PaymentStoreType {
  tid: string;
  setTid: (tid: string) => void;
  pgToken: string;
  setPgToken: (token: string) => void;

  qty: number;
  setQty: (qty: number) => void;

  recipient: string;
  setRecipient: (recipient: string) => void;

  addressPost: string;
  setAddressPost: (addressPost: string) => void;

  addressDetail: string;
  setaddressDetail: (addressDetail: string) => void;
}

const usePaymentStore = create<PaymentStoreType>(
  persist(
    (set, get) => ({
      tid: '',
      setTid: (tid: string) => set({ tid: tid }),
      pgToken: '',
      setPgToken: (token: string) => set({ pgToken: token }),

      qty: 0,
      setQty: (qty: number) => set({ qty: qty }),

      recipient: '',
      setRecipient: (recipient: string) => set({ recipient: recipient }),

      addressPost: '',
      setAddressPost: (addressPost: string) =>
        set({ addressPost: addressPost }),

      addressDetail: '',
      setaddressDetail: (addressDetail: string) =>
        set({ addressDetail: addressDetail }),
    }),
    {
      name: 'paymentStore',
    },
  ),
);

export default usePaymentStore;
