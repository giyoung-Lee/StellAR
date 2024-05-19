import create from 'zustand';
import { persist } from 'zustand/middleware';

interface LoadingStoreType {
  loadingMessage: loadingApitype[];
  setLoadingMessage: (loadingmessage: loadingApitype[]) => void;
}

const useLoadingStore = create<LoadingStoreType>(
  persist(
    (set, get) => ({
      loadingMessage: [],
      setLoadingMessage: (message: loadingApitype[]) =>
        set({ loadingMessage: message }),
    }),
    {
      name: 'loading',
    },
  ),
);

export default useLoadingStore;
