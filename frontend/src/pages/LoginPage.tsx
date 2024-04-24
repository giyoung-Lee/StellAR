import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useMutation } from '@tanstack/react-query';
import { loginApi } from '../apis/UserApis';
import './style/User.css';
import './style/Fontawsome';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import Swal from 'sweetalert2';

const LoginPage = () => {
  const navigate = useNavigate();
  const goToSignup = () => {
    navigate('/signup');
  };
  const goBack = () => {
    navigate(-1);
  }

  const [loginData, setLoginData] = useState({
    userId: '',
    password: '',
  });

  const [errors, setErrors] = useState({
    userId: '',
    password: '',
  });

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setLoginData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const validateInputs = () => {
    let isValid = true;
    let errors = {
      userId: '',
      password: '',
    };
  
    if (!/^[a-zA-Z0-9]+$/.test(loginData.userId)) {
      errors.userId = '아이디는 영문자와 숫자만 사용할 수 있습니다.';
      isValid = false;
    }
  
    if (!isValid) {
      Swal.fire({
        icon: 'error',
        title: '오류',
        html: errors.userId.replace(/\n/g, '<br>'),
        customClass: {
          icon: 'center-icon'
        },
        color: "#dcdcdc",
        background: 'rgba(0, 0, 0, 0.8)',
      });
      setErrors(errors);
      return false;
    }
    
    setErrors(errors);
    return true;
  };
  

    const { mutate } = useMutation({
      mutationFn: loginApi,
      onSuccess(result: string) {
        console.log(result);
      },
      onError(error) {
        console.log(error);
      },
    });
  
    const onSubmitLogin = (e: React.FormEvent<HTMLFormElement>) => {
      e.preventDefault();
  
      if (validateInputs()) {
        console.log(loginData)
        mutate(loginData);
      }
    };


  return (
    <div className="flex items-center justify-center min-h-[100vh]">
      <div className='fixed top-4 left-5' onClick={goBack}>
        <FontAwesomeIcon icon="arrow-left" size="1x" color="white" />
      </div>

      <div>
        <p className="text-2xl text-center">로그인</p>

        <form className="container p-8" onSubmit={onSubmitLogin}>
          <div className="input-container">
            <div className="input-content">
              <div className="input-dist">
                <div className="input-type">
                  <input
                    className="input-is"
                    type="text"
                    placeholder="아이디"
                    name="userId"
                    value={loginData.userId}
                    onChange={handleInputChange}
                  />
                  <input
                    className="input-is"
                    type="password"
                    placeholder="비밀번호"
                    name="password"
                    value={loginData.password}
                    onChange={handleInputChange}
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
              <span className="circle-icon arrow"></span>
            </span>
            <span className="button-text">아직 회원이 아니신가요?</span>
          </button>
        </div>
      </div>
    </div>
  );
};

export default LoginPage;
