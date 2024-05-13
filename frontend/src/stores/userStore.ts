import create from 'zustand';
import { persist } from 'zustand/middleware';

// UserType 인터페이스 정의
interface UserType {
  id: number;
  userId: string;
  setUser: (newData: Partial<UserType>) => void;

  isLogin: boolean;
  setIsLogin: (isLogin: boolean) => void;

  userLat: number;
  userLng: number;
  setUserLat: (lat: number) => void;
  setUserLng: (lng: number) => void;

  isGyro: boolean;
  setIsGyro: (isAR: boolean) => void;
}

// 사용할 초기 상태 정의
const initialState: UserType = {
  id: 0,
  userId: '',
  setUser: () => {}, // 초기 상태에서는 빈 함수로 정의
  isLogin: false,
  setIsLogin: () => {},

  userLat: 35.1595,
  userLng: 126.8526,
  setUserLat: () => {},
  setUserLng: () => {},

  isGyro: false,
  setIsGyro: () => {},
};

const useUserStore = create<UserType>(
  persist(
    (set) => ({
      ...initialState, // 초기 상태 사용
      setUser: (newData: Partial<UserType>) =>
        set((state) => ({
          ...state,
          ...newData,
        })),
      setIsLogin: (isLogin: boolean) => set({ isLogin: isLogin }),
      setUserLat: (lat: number) => set({ userLat: lat }),
      setUserLng: (lng: number) => set({ userLng: lng }),
      isGyro: false as boolean,
      setIsGyro: (isGyro) => set({ isGyro: isGyro }),
    }),
    {
      name: 'userStore', // localStorage에 저장될 이름
    },
  ),
);

export default useUserStore;
