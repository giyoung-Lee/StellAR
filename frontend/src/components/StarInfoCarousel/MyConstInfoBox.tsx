import { useQuery } from '@tanstack/react-query';
import { GetMyconstDetail } from '../../apis/MyConstApis';
import useUserStore from '../../stores/userStore';
import useConstellationStore from '../../stores/constellationStore';
import '../../pages/style/Fontawsome';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { useEffect, useRef } from 'react';

const MyConstInfoBox = () => {
  const userStore = useUserStore();
  const constellationStore = useConstellationStore();
  const Ref = useRef<HTMLDivElement>(null);

  const { isLoading: MyConstDetailLoading, data: MyConstDetailData } = useQuery(
    {
      queryKey: ['get-my-const-detail'],
      queryFn: () =>
        GetMyconstDetail(
          userStore.userId,
          constellationStore.constellationName,
        ),
    },
  );

  useEffect(() => {
    const handleClick = (e: MouseEvent) => {
      if (Ref.current && !Ref.current.contains(e.target as Node)) {
        constellationStore.setConstellationClicked(false);
      }
    };
    window.addEventListener('mousedown', handleClick);
    return () => window.removeEventListener('mousedown', handleClick);
  }, [Ref]);

  const cancelFunc = () => {
    constellationStore.setConstellationClicked(false);
  };

  if (MyConstDetailLoading) {
    return null;
  }

  return (
    <div
      ref={Ref}
      className="absolute z-[16777272] p-3 bg-white bg-opacity-25 rounded-xl shadow-custom border-opacity-18 backdrop-blur-sm"
    >
      <div className="flex flex-col items-center justify-center max-w-[70vw] h-full">
        <p className="max-w-full text-lightorange text-xl font-bold text-center py-2">
          {MyConstDetailData?.data.name}
        </p>
        <p className="max-w-full mt-1 max-h-[300px] overflow-auto break-words flex-grow text-left">
          {MyConstDetailData?.data.description}
        </p>
      </div>
    </div>
  );
};

export default MyConstInfoBox;
