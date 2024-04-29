import create from 'zustand';
import { persist } from 'zustand/middleware';

interface StarStoreType {
  starId: string;
  setStarId: (nowId: string) => void;
  starClicked: boolean;
  setStarClicked: (clicked: boolean) => void;
}

const useStarStore = create<StarStoreType>(
  persist(
    (set, get) => ({
      starId: '',
      setStarId: (nowId: string) => set({ starId: nowId }),
      starClicked: false,
      setStarClicked: (clicked: boolean) => set({ starClicked: clicked }),
    }),
    {
      name: 'StarStore',
    },
  ),
);

export default useStarStore;
