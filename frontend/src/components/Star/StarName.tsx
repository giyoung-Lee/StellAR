import { useRef } from 'react';
import useStarStore from '../../stores/starStore';
import * as n from '../style/StarNameStyle';
import MarkBtn from '../StarMark/MarkBtn';
import '../../pages/style/Fontawsome';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

const StarName = () => {
  const starStore = useStarStore();
  const starNameRef = useRef<HTMLDivElement>(null);

  const checkStarIdExists = (starId: string) => {
    const star = starStore.markedStars.find((star) => star.starId === starId);
    return star ? star.bookmarkName : null;
  };

  const handleReset = () => {
    starStore.setStarClicked(false);
    starStore.setPlanetClicked(false);
    starStore.setZoomFromOther(false);
    starStore.setZoomStarId('');
    starStore.setStarId('');
    starStore.setStarPosition(null);
  };

  return (
    <div
      ref={starNameRef}
      className="text-center absolute z-[1000] top-[60%] p-3 bg-white bg-opacity-25 rounded-xl shadow-custom border-opacity-18 backdrop-blur-sm"
    >
      {/* <n.Wrapper ref={starNameRef}> */}
      <div className="flex justify-end">
        <div onClick={handleReset}>
          <FontAwesomeIcon icon="xmark" />
        </div>
      </div>
      <span className="">
        {starStore.zoomFromOther
          ? starStore.zoomStarId
          : starStore.starId
            ? starStore.starId
            : starStore.planetId}
      </span>
      {starStore.zoomFromOther ? (
        <p>
          <FontAwesomeIcon icon={['fas', 'star']} />
          <n.BookMarkName>
            {checkStarIdExists(starStore.zoomStarId)}
          </n.BookMarkName>
        </p>
      ) : checkStarIdExists(starStore.starId) ? (
        <p>
          <FontAwesomeIcon icon={['fas', 'star']} />
          <n.BookMarkName>{checkStarIdExists(starStore.starId)}</n.BookMarkName>
        </p>
      ) : (
        <MarkBtn starName={starStore.starId} />
      )}
      {/* </n.Wrapper> */}
    </div>
  );
};

export default StarName;
