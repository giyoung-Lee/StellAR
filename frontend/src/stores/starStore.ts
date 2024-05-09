import create from 'zustand';
import { persist } from 'zustand/middleware';
import * as THREE from 'three';

interface StarStoreType {
  starId: string;
  setStarId: (nowId: string) => void;

  zoomStarId: string;
  setZoomStarId: (nowId: string) => void;

  starPosition: THREE.Vector3 | null;
  setStarPosition: (nowPosition: THREE.Vector3 | null) => void;

  // 별 클릭 상태 관리
  starClicked: boolean;
  setStarClicked: (clicked: boolean) => void;
  // 행성 클릭 상태 관리
  planetClicked: boolean;
  setPlanetClicked: (clicked: boolean) => void;
  // 카메라 모드 상태 관리
  isARMode: boolean;
  setARMode: (isAR: boolean) => void;

  linkedStars: string[][]; // 2차원 배열로 클릭된 별의 ID 그룹을 저장
  addStarToClicked: (ids: string[]) => void; // 별 그룹을 클릭된 목록에 추가
  removeStarFromClicked: (id: string[]) => void; // 별 그룹을 클릭된 목록에서 제거

  // 별마크 상태 관리
  markedStars: StarMarkType[];
  setMarkedStars: (markedStars: StarMarkType[]) => void;

  markSaveToggle: boolean;
  setMarkSaveToggle: (toggle: boolean) => void;

  // 별 초기화 하기
  resetLinkedStars: () => void;
  zoomX: number;
  setZoomX: (zoomX: number) => void;
  zoomY: number;
  setZoomY: (zoomY: number) => void;
  zoomZ: number;
  setZoomZ: (zoomZ: number) => void;

  // 다른 페이지에서 홈 이동하기
  zoomFromOther: boolean;
  setZoomFromOther: (zoomFromOther: boolean) => void;
}

const useStarStore = create<StarStoreType>(
  persist(
    (set, get) => ({
      starId: '',
      setStarId: (nowId: string) => set({ starId: nowId }),
      zoomStarId: '',
      setZoomStarId: (nowId: string) => set({ zoomStarId: nowId }),

      starPosition: null,
      setStarPosition: (nowPosition: THREE.Vector3 | null) =>
        set({ starPosition: nowPosition }),

      starClicked: false as boolean,
      setStarClicked: (clicked) => set({ starClicked: clicked }),

      planetClicked: false as boolean,
      setPlanetClicked: (clicked) => set({ planetClicked: clicked }),

      isARMode: false as boolean,
      setARMode: (isAR) => set({ isARMode: isAR }),

      linkedStars: [],
      addStarToClicked: (ids: string[]) =>
        set((state) => ({
          linkedStars: [...state.linkedStars, ids], // ids 배열 전체를 linkedStars에 추가
        })),

      removeStarFromClicked: (ids: string[]) =>
        set((state) => ({
          linkedStars: state.linkedStars.filter(
            (group) => !group.every((id) => ids.includes(id)),
          ),
        })),

      resetLinkedStars: () => set({ linkedStars: [] }),

      markedStars: [],
      setMarkedStars: (markedStars: StarMarkType[]) =>
        set({ markedStars: markedStars }),

      markSaveToggle: false,
      setMarkSaveToggle: (toggle: boolean) => set({ markSaveToggle: toggle }),

      zoomX: 0,
      setZoomX: (zoomX: number) => set({ zoomX: zoomX }),
      zoomY: 0,
      setZoomY: (zoomY: number) => set({ zoomY: zoomY }),
      zoomZ: 0,
      setZoomZ: (zoomZ: number) => set({ zoomZ: zoomZ }),
      zoomFromOther: false,
      setZoomFromOther: (zoomFromOther: boolean) =>
        set({ zoomFromOther: zoomFromOther }),
    }),
    {
      name: 'StarStore',
      partialize: (state) => ({
        starId: state.starId,
        zoomStarId: state.zoomStarId,
        starPosition: state.starPosition,

        linkedStars: state.linkedStars,
        markedStars: state.markedStars,
        zoomFromOther: state.zoomFromOther,
        zoomX: state.zoomX,
        zoomY: state.zoomY,
        zoomZ: state.zoomZ,
        starClicked: state.starClicked,
      }),
    },
  ),
);

export default useStarStore;
