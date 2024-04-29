import create from 'zustand';
import { persist } from 'zustand/middleware';

// UserType 인터페이스 정의
interface UserType {
  id: number;
  userId: string;
  setUser: (newData: Partial<UserType>) => void;
}

// 사용할 초기 상태 정의
const initialState: UserType = {
  id: 0,
  userId: "",
  setUser: () => {}, // 초기 상태에서는 빈 함수로 정의
};

const useUserStore = create<UserType>(
  persist(
    (set) => ({
      ...initialState, // 초기 상태 사용
      setUser: (newData: Partial<UserType>) => set((state) => ({
        ...state,
        ...newData,
      })),
    }),
    {
      name: 'userStore', // localStorage에 저장될 이름
    }
  )
);

export default useUserStore;
