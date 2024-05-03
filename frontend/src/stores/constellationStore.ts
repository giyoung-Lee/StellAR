import create from 'zustand';
import { persist } from 'zustand/middleware';

interface ConstellationStoreType {
  constellationClicked: boolean;
  setConstellationClicked: (clicked: boolean) => void;
  constellationName: string;
  setConstellationName: (name: string) => void;
}

const useConstellationStore = create<ConstellationStoreType>(
  persist(
    (set, get) => ({
      constellationClicked: false,
      setConstellationClicked: (clicked: boolean) =>
        set({ constellationClicked: clicked }),
      constellationName: '',
      setConstellationName: (name: string) => set({ constellationName: name }),
    }),
    {
      name: 'ConstellationStore',
    },
  ),
);

export default useConstellationStore;
