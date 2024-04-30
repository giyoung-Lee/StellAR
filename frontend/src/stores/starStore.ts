import create from 'zustand';
import { persist } from 'zustand/middleware';

interface StarStoreType {
  starId: string;
  setStarId: (nowId: string) => void;
  starClicked: boolean;
  setStarClicked: (clicked: boolean) => void;
  isARMode: boolean;  // 카메라 모드 상태
  setARMode: (isAR: boolean) => void;  // 카메라 모드 상태를 설정하는 함수
}

const useStarStore = create<StarStoreType>(
  persist(
    (set, get) => ({
      starId: '',
      setStarId: (nowId: string) => set({ starId: nowId }),
      starClicked: false,
      setStarClicked: (clicked: boolean) => set({ starClicked: clicked }),
      isARMode: false,  // 초기 상태는 비활성화
      setARMode: (isAR: boolean) => set({ isARMode: isAR }),
    }),
    {
      name: 'StarStore',
    },
  ),
);


export default useStarStore;
