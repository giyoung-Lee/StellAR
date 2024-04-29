import './Loading.css'

const Loading = () => {
  return (
    <div className='flex flex-col items-center justify-center min-h-[100vh]'>

      <div className="pyramid-loader mb-44">
        <div className="wrapper-pyramid">
          <span className="side side1"></span>
          <span className="side side2"></span>
          <span className="side side3"></span>
          <span className="side side4"></span>
          <span className="shadow"></span>
        </div>
      </div>

      <div className='absolute w-[69vw] mt-5 text-center'>
        <p>오늘 황소자리(5월~6월)가 안보이는 이유는 태양에 가려졌기 때문이예요</p>
      </div>
      
    </div>
  );
};

export default Loading;
