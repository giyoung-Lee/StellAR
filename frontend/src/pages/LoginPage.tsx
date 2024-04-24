import { useNavigate } from 'react-router-dom';
import './style/LoginPage.css';
import './style/Fontawsome';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

const LoginPage = () => {
  const navigate = useNavigate();
  const goToSignup = () => {
    navigate('/signup');
  };
  const goBack = () => {
    navigate(-1);
  }

  return (
    <div className="flex items-center justify-center min-h-[100vh]">
      <div className='fixed top-4 left-5' onClick={goBack}>
        <FontAwesomeIcon icon="arrow-left" size="1x" color="white" />
      </div>

      <div>
        <p className="text-2xl text-center">로그인</p>

        <form className="container p-8" action="">
          <div className="input-container">
            <div className="input-content">
              <div className="input-dist">
                <div className="input-type">
                  <input
                    className="input-is"
                    type="text"
                    placeholder="이메일"
                  />
                  <input
                    className="input-is"
                    type="password"
                    placeholder="비밀번호"
                  />
                </div>
              </div>
            </div>
          </div>
          <button className="submit-button">접속</button>
        </form>
        <div className="px-8">
          <button className="learn-more" onClick={goToSignup}>
            <span className="circle" aria-hidden="true">
              <span className="icon arrow"></span>
            </span>
            <span className="button-text">아직 회원이 아니신가요?</span>
          </button>
        </div>
      </div>
    </div>
  );
};

export default LoginPage;
