import React, { ChangeEvent, useEffect, useRef, useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import * as m from '../style/StarMarkStyle';
import useUserStore from '../../stores/userStore';
import { useMutation } from '@tanstack/react-query';
import { PostStarMark } from '../../apis/StarMarkApis';

type Props = {
  starName: string;
};

const MarkBtn = ({ starName }: Props) => {
  const userStore = useUserStore();

  const [isInput, setIsInput] = useState(false);
  const [markName, setMarkName] = useState('');
  const [markData, setMarkData] = useState({
    userId: '',
    starId: '',
    bookmarkName: '',
  });

  const handleSaveBtnClick = (event: React.MouseEvent<HTMLButtonElement>) => {
    event.stopPropagation();
    console.log('저장');
    setIsInput(!isInput);
    mutate(markData);
  };

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setMarkName(event.target.value);
    setMarkData({
      userId: userStore.userId,
      starId: starName,
      bookmarkName: event.target.value,
    });
  };

  const { mutate } = useMutation({
    mutationFn: PostStarMark,
    onSuccess(result: string) {
      console.log(result);
    },
    onError(error) {
      console.log(error);
    },
  });

  useEffect(() => {
    setMarkName('');
    console.log(starName);
  }, [isInput]);

  return (
    <m.BtnWrapper>
      {!isInput && (
        <m.ToggleBtn onClick={() => setIsInput(true)}>
          별마크 등록하기
        </m.ToggleBtn>
      )}
      {isInput && (
        <>
          <m.MarkNameInput
            type="text"
            value={markName}
            onChange={handleChange}
            maxLength={12}
          />
          <m.BtnBox>
            <m.BackBtn onClick={() => setIsInput(false)}>
              <FontAwesomeIcon icon="arrow-left" color="black" />
            </m.BackBtn>
            <m.SaveBtn onClick={handleSaveBtnClick}>저장하기</m.SaveBtn>
          </m.BtnBox>
        </>
      )}
    </m.BtnWrapper>
  );
};

export default MarkBtn;
