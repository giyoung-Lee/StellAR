import * as e from './style/EntryPageStyle';
import logo from '/icons/logo_nobg.png';

const EntryPage = () => {
  return (
    <>
      <div>
        <e.EntryImg src={logo} alt="logo_image" />
      </div>
      <div>
        <p className='text-2xl text-white'>우하하하하하</p>
        <button className='text-white'>별 보러 가기</button>
      </div>
    </>
  );
};

export default EntryPage;
