import create from 'zustand';

interface PaymentStoreType {
  tid: string;
  setTid: (tid: string) => void;
}

const usePaymentStore = create<PaymentStoreType>((set, get) => ({
  tid: '',
  setTid: (tid: string) => set({ tid: tid }),
}));

export default usePaymentStore;
