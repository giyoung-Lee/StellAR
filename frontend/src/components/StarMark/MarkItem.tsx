import { useState } from 'react';
import * as m from '../style/StarMarkStyle';
import { useMutation } from '@tanstack/react-query';
import { DeleteStarMark } from '../../apis/StarMarkApis';
import useUserStore from '../../stores/userStore';
import useStarStore from '../../stores/starStore';
import { useNavigate } from 'react-router-dom';
import getXYZ from '../../hooks/getXYZ';
import Swal from 'sweetalert2';

type Props = {
  starId: string;
  bookmarkName: string;
  createTime: string;
  RA: number;
  DEC: number;
  nomalizedMagV: number;
};

const MarkItem = ({
  starId,
  bookmarkName,
  createTime,
  RA,
  DEC,
  nomalizedMagV,
}: Props) => {
  const [isSaved] = useState(true);
  const { userId } = useUserStore();
  const starStore = useStarStore();
  const userStore = useUserStore();
  const formatDate = (dateString: string) => {
    const date = new Date(dateString);
    return date.toLocaleDateString('ko-KR', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
    });
  };

  const handleCheckBox = () => {
    Swal.fire({
      text: '별마크를 삭제할까요?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: '삭제',
      cancelButtonText: '취소',
      width: 300,
      customClass: {
        container: 'my-swal',
      },
    }).then((result) => {
      if (result.isConfirmed) {
        Swal.fire({ text: '삭제되었습니다!', icon: 'success', width: 300 });
        mutate({ userId, starId });
      }
    });
  };

  const { mutate } = useMutation({
    mutationFn: DeleteStarMark,
    onSuccess(result: string) {
      // console.log(result);
      starStore.setMarkSaveToggle(!starStore.markSaveToggle);
    },
  });

  const navigate = useNavigate();
  const findMyStar = () => {
    starStore.setZoomFromOther(true);
    const { x, y, z } = getXYZ(RA, DEC, userStore.userLat, userStore.userLng);
    starStore.setZoomX(-1 * x * nomalizedMagV);
    starStore.setZoomY(z * nomalizedMagV);
    starStore.setZoomZ(y * nomalizedMagV);
    starStore.setZoomStarId(starId);
    starStore.setStarClicked(true);
    navigate('/');
  };

  return (
    <>
      <m.StarInfo className="star-info">
        <m.NameBox>
          <m.BookMarkName className="bookmark-name" onClick={findMyStar}>
            {bookmarkName}
          </m.BookMarkName>
          <m.StarName className="star-name">{starId}</m.StarName>
        </m.NameBox>
        <m.Date className="date">{formatDate(createTime)}</m.Date>
        <m.Star>
          <label className="container">
            <input
              type="checkbox"
              checked={isSaved}
              onChange={handleCheckBox}
            />
            <svg
              id="Layer_1"
              version="1.2"
              viewBox="0 0 24 24"
              xmlns="http://www.w3.org/2000/svg"
            >
              <g>
                <g>
                  <path d="M9.362,9.158c0,0-3.16,0.35-5.268,0.584c-0.19,0.023-0.358,0.15-0.421,0.343s0,0.394,0.14,0.521    c1.566,1.429,3.919,3.569,3.919,3.569c-0.002,0-0.646,3.113-1.074,5.19c-0.036,0.188,0.032,0.387,0.196,0.506    c0.163,0.119,0.373,0.121,0.538,0.028c1.844-1.048,4.606-2.624,4.606-2.624s2.763,1.576,4.604,2.625    c0.168,0.092,0.378,0.09,0.541-0.029c0.164-0.119,0.232-0.318,0.195-0.505c-0.428-2.078-1.071-5.191-1.071-5.191    s2.353-2.14,3.919-3.566c0.14-0.131,0.202-0.332,0.14-0.524s-0.23-0.319-0.42-0.341c-2.108-0.236-5.269-0.586-5.269-0.586    s-1.31-2.898-2.183-4.83c-0.082-0.173-0.254-0.294-0.456-0.294s-0.375,0.122-0.453,0.294C10.671,6.26,9.362,9.158,9.362,9.158z"></path>
                </g>
              </g>
            </svg>
          </label>
        </m.Star>
      </m.StarInfo>
    </>
  );
};

export default MarkItem;

