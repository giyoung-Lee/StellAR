import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useMutation } from '@tanstack/react-query';
import { signupApi } from '../apis/UserApis';
import './style/PageGlobal.css';
import './style/Fontawsome';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import Swal from 'sweetalert2';

const SignupPage = () => {
  const navigate = useNavigate();
  const goToLogin = () => {
    navigate('/login');
  };
  const goBack = () => {
    navigate(-1);
  };

  const [signupData, setSignupData] = useState({
    userId: '',
    password: '',
    passwordConfirm: '',
  });

  const [errors, setErrors] = useState({
    userId: '',
    password: '',
    passwordConfirm: '',
  });

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setSignupData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const validateInputs = () => {
    let isValid = true;
    let errors = {
      userId: '',
      password: '',
      passwordConfirm: '',
    };
  
    if (!/^[a-zA-Z0-9]+$/.test(signupData.userId)) {
      errors.userId = '아이디는 영문자와 숫자만 \n 사용할 수 있습니다.';
      isValid = false;
    } else if (/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(signupData.userId)) {
      errors.userId = '아이디는 이메일 형식은 \n 사용할 수 없습니다.';
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
  
    if (!/(?=.*\d)(?=.*[a-z])(?=.*\W).{8,}/.test(signupData.password)) {
      errors.password = '비밀번호는 8자 이상이어야 하며 \n 숫자, 영문자, 특수문자를 \n 각각 하나 이상 포함해야 합니다.';
      isValid = false;
    } else if (signupData.password !== signupData.passwordConfirm) {
      errors.passwordConfirm = '비밀번호가 일치하지 않습니다.';
      isValid = false;
    }
  
    if (!isValid) {
      Swal.fire({
        icon: 'error',
        title: '오류',
        html: (errors.password || errors.passwordConfirm).replace(/\n/g, '<br>'),
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
    mutationFn: signupApi,
    onSuccess(result: string) {
      Swal.fire({
        icon: "success",
        title: "성공",
        text: "정상적으로 회원가입 되었습니다!",
      }).then(() => {
        navigate('/login');
      });
    },    
    onError(error) {
      Swal.fire({
        icon: "error",
        title: "오류",
        text: "회원가입 중 오류가 발생했습니다.",
      });
    },
  });

  const onSubmitSignup = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (validateInputs()) {
        console.log(signupData)
        mutate(signupData);
    }
  };

  return (
    <div className="flex items-center justify-center min-h-[100vh]">
      <div className="fixed top-4 left-5" onClick={goBack}>
        <FontAwesomeIcon icon="arrow-left" size="1x" color="white" />
      </div>

      <div>
        <p className="text-2xl text-center">회원가입</p>

        <form className="container p-8" onSubmit={onSubmitSignup}>
          <div className="input-container">
            <div className="input-content">
              <div className="input-dist">
                <div className="input-type">
                  <input
                    className="input-is"
                    type="text"
                    placeholder="아이디"
                    name="userId"
                    value={signupData.userId}
                    onChange={handleInputChange}
                  />
                  <input
                    className="input-is"
                    type="password"
                    placeholder="비밀번호"
                    name="password"
                    value={signupData.password}
                    onChange={handleInputChange}
                  />
                  <input
                    className="input-is"
                    type="password"
                    placeholder="비밀번호 확인"
                    name="passwordConfirm"
                    value={signupData.passwordConfirm}
                    onChange={handleInputChange}
                  />
                </div>
              </div>
            </div>
          </div>
          <button type="submit" className="submit-button">
            가입완료
          </button>
        </form>
        <div className="px-8">
          <button className="learn-more" onClick={goToLogin}>
            <span className="circle" aria-hidden="true">
              <span className="circle-icon arrow"></span>
            </span>
            <span className="button-text">이미 회원이신가요?</span>
          </button>
        </div>
      </div>
    </div>
  );
};

export default SignupPage;
