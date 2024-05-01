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
    <m.Star>
      {isInput ? null : (
        <button onClick={hadleBtnClick}>별마크 등록하기</button>
      )}
      {isInput ? (
        <input
          type="text"
          value={markName}
          onChange={handleChange}
          style={{ backgroundColor: 'red' }}
        />
      ) : null}
      {isInput ? <button onClick={hadleBtnClick}>ㅇ!!</button> : null}
    </m.Star>
  );
};

export default MarkBtn;

