import create from 'zustand';

interface ConstellationStoreType {
  constellationClicked: boolean;
  setConstellationClicked: (clicked: boolean) => void;
  constellationName: string;
  setConstellationName: (name: string) => void;
}

const useConstellationStore = create<ConstellationStoreType>((set, get) => ({
  constellationClicked: false,
  setConstellationClicked: (clicked: boolean) =>
    set({ constellationClicked: clicked }),
  constellationName: '',
  setConstellationName: (name: string) => set({ constellationName: name }),
}));

export default useConstellationStore;
