import React, { ChangeEvent, useRef, useState } from 'react';

import * as m from '../style/StarMarkStyle';

const MarkBtn = () => {
  const [isInput, setIsInput] = useState(false);
  const [markName, setMarkName] = useState('');

  const hadleBtnClick = (event: React.MouseEvent<HTMLButtonElement>) => {
    event.stopPropagation();
    setIsInput(!isInput);
  };

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setMarkName(event.target.value);
  };

  return (
    <m.BtnWrapper>
      {isInput ? null : (
        <m.ToggleBtn onClick={hadleBtnClick}>별마크 등록하기</m.ToggleBtn>
      )}
      {isInput ? (
        <m.MarkNameInput
          type="text"
          value={markName}
          onChange={handleChange}
          maxLength={12}
        />
      ) : null}
      {isInput ? <button onClick={hadleBtnClick}>ㅇ!!</button> : null}
    </m.BtnWrapper>
  );
};

export default MarkBtn;
