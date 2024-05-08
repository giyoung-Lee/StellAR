import create from 'zustand';
import { persist } from 'zustand/middleware';

interface StarStoreType {
  starId: string;
  setStarId: (nowId: string) => void;
  // 별 클릭 상태 관리
  starClicked: boolean;
  setStarClicked: (clicked: boolean) => void;
  // 행성 클릭 상태 관리
  planetClicked: boolean;
  setPlanetClicked: (clicked: boolean) => void;
  // 카메라 모드 상태 관리
  isARMode: boolean;
  setARMode: (isAR: boolean) => void;
  
  clickedStars: string[]; // 클릭된 별의 ID들을 저장하는 배열
  addStarToClicked: (id: string) => void; // 별을 클릭된 목록에 추가
  removeStarFromClicked: (id: string) => void; // 별을 클릭된 목록에서 제거

  // 별마크 상태 관리
  markedStars: StarMarkType[];
  setMarkedStars: (markedStars: StarMarkType[]) => void;

  markSaveToggle: boolean;
  setMarkSaveToggle: (toggle: boolean) => void;

  // 별 초기화 하기
  resetClickedStars: () => void;
  zoomX: number;
  setZoomX: (zoomX: number) => void;
  zoomY: number;
  setZoomY: (zoomY: number) => void;
  zoomZ: number;
  setZoomZ: (zoomZ: number) => void;
}

const useStarStore = create<StarStoreType>(
  persist(
    (set, get) => ({
      starId: '',
      setStarId: (nowId: string) => set({ starId: nowId }),
      starClicked: false,
      setStarClicked: (clicked: boolean) => set({ starClicked: clicked }),
      planetClicked: false,
      setPlanetClicked: (clicked: boolean) => set({ planetClicked: clicked }),
      isARMode: false,
      setARMode: (isAR: boolean) => set({ isARMode: isAR }),
      clickedStars: [],
      addStarToClicked: (id: string) =>
        set((state) => {
          if (!state.clickedStars.includes(id)) {
            return { clickedStars: [...state.clickedStars, id] };
          }
          return state;
        }),
      removeStarFromClicked: (id: string) =>
        set((state) => ({
          clickedStars: state.clickedStars.filter(starId => starId !== id),
        })),
      markedStars: [],
      setMarkedStars: (markedStars: StarMarkType[]) => set({ markedStars: markedStars }),
      markSaveToggle: false,
      setMarkSaveToggle: (toggle: boolean) => set({ markSaveToggle: toggle }),
      resetClickedStars: () => set({ clickedStars: [] }),
      zoomX: 0,
      setZoomX: (zoomX: number) => set({ zoomX: zoomX }),
      zoomY: 0,
      setZoomY: (zoomY: number) => set({ zoomY: zoomY }),
      zoomZ: 0,
      setZoomZ: (zoomZ: number) => set({ zoomZ: zoomZ }),
    }),
    {
      name: 'StarStore',
      partialize: (state) => ({
        starId: state.starId,
        clickedStars: state.clickedStars,
      }),
    },
  ),
);

export default useStarStore;
