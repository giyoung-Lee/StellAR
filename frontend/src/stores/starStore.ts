import create from 'zustand';
import { persist } from 'zustand/middleware';

interface StarStoreType {
  starId: string;
  setStarId: (nowId: string) => void;
  starClicked: boolean;
  setStarClicked: (clicked: boolean) => void;
  isARMode: boolean; // 카메라 모드 상태
  setARMode: (isAR: boolean) => void; // 카메라 모드 상태를 설정하는 함수
  clickedStars: string[]; // 클릭된 별의 ID들을 저장하는 배열
  addStarToClicked: (id: string) => void; // 별을 클릭된 목록에 추가
  removeStarFromClicked: (id: string) => void; // 별을 클릭된 목록에서 제거

  markedStars: StarMarkType[];
  setMarkedStars: (markedStars: StarMarkType[]) => void;

  markSaveToggle: boolean;
  setMarkSaveToggle: (toggle: boolean) => void;

  resetClickedStars: () => void; // 별 초기화 하기

}

const useStarStore = create<StarStoreType>(
  persist(
    (set, get) => ({
      starId: '',
      setStarId: (nowId: string) => set({ starId: nowId }),
      starClicked: false,
      setStarClicked: (clicked: boolean) => set({ starClicked: clicked }),
      isARMode: false, // 초기 상태는 비활성화
      setARMode: (isAR: boolean) => set({ isARMode: isAR }),
      clickedStars: [],
      // 별을 클릭하면 해당 별을 배열에 보관
      addStarToClicked: (id: string) =>
        set((state) => ({ clickedStars: [...state.clickedStars, id] })),
      // 별을 클릭 취소하면 해당 별을 배열에서 제거
      removeStarFromClicked: (id: string) =>
        set((state) => ({
          clickedStars: state.clickedStars.filter((starId) => starId !== id),
        })),

      markedStars: [],
      setMarkedStars: (markedStars: StarMarkType[]) =>
        set({ markedStars: markedStars }),
      markSaveToggle: false,
      setMarkSaveToggle: (toggle: boolean) => set({ markSaveToggle: toggle }),

      // 별 초기화 하기 함수
      resetClickedStars: () => set({ clickedStars: [] }),

    }),
    {
      name: 'StarStore',
      // 스토리지에는 starId랑 클릭된 별 배열만 저장
      partialize: (state) => ({
        starId: state.starId,
        clickedStars: state.clickedStars,

        markedStars: state.markedStars,

      }),
    },
  ),
);

export default useStarStore;
