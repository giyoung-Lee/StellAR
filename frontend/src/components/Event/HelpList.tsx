const MyStarList = () => {
  // const myStarList = new Array(2).fill(0);
  return (
    <div className="max-h-[70vh] overflow-scroll">
      {/* {myStarList.map(() => ( */}
      <div className="border border-[#EBB67B] rounded-xl p-2 my-3">
          <p className="text-[#EBB67B] text-lg">충</p>
          <p className="text-base">외행성과 태양사이에 지구가 위치하여 태양과 외행성의 시황경 차이가 180˚가 되는 현상, 즉 태양-지구-외행성 순서로 위치한 때이다.</p>
        </div>
        <div className="border border-[#EBB67B] rounded-xl p-2 my-3">
          <p className="text-[#EBB67B] text-lg">합</p>
          <p className="text-base">지구와 외행성 사이에 태양이 위치하여 태양과 외행성의 시황경이 같게 되는 현상, 즉 외행성-태양-지구 순서로 위치한 때이다.</p>
        </div>
        <div className="border border-[#EBB67B] rounded-xl p-2 my-3">
          <p className="text-[#EBB67B] text-lg">내합과 외합</p>
          <p className="text-base">내합과 외합의 시각은 태양과 내행성 (수성, 금성)의 시황경이 같게 되는 시각이며, 내행성이 태양과 지구 사이에 있는 경우를 내합, 내행성과 지구 사이에 태양이 있을 때를 외합이라 한다.</p>
        </div>
        <div className="border border-[#EBB67B] rounded-xl p-2 my-3">
          <p className="text-[#EBB67B] text-lg">유</p>
          <p className="text-base">행성의 시적경이 변하지 않는 시간이며, 이 때의 시적위의 변화는 완만하여 항성에 대하여 시운동은 거의 정지된 상태이다.</p>
        </div>
        <div className="border border-[#EBB67B] rounded-xl p-2 my-3">
          <p className="text-[#EBB67B] text-lg">최대이각</p>
          <p className="text-base">태양과 내행성의 각거리가 최대로 되는 각도이며, 지구에서 볼 때, 태양의 동쪽으로 최대 각거리에 있는 경우를 동방최대이각, 서쪽에 있는 경우를 서방 최대이각이라 한다.</p>
        </div>
        <div className="border border-[#EBB67B] rounded-xl p-2 my-3">
          <p className="text-[#EBB67B] text-lg">근일점</p>
          <p className="text-base">태양과 지구 사이의 거리가 최소가 되는 지점이다.</p>
        </div>
        <div className="border border-[#EBB67B] rounded-xl p-2 my-3">
          <p className="text-[#EBB67B] text-lg">원일점</p>
          <p className="text-base">태양과 지구 사이의 거리가 최대가 되는 지점이다.</p>
        </div>
        <div className="border border-[#EBB67B] rounded-xl p-2 my-3">
          <p className="text-[#EBB67B] text-lg">월령</p>
          <p className="text-base">바로 직전 합삭 시작으로부터 매일 오후 9시까지의 시간을 말한다.</p>
        </div>
        <div className="border border-[#EBB67B] rounded-xl p-2 my-3">
          <p className="text-[#EBB67B] text-lg">AU(Astronomical Unit)</p>
          <p className="text-base">태양과 지구 사이의 평균거리(1.496 X 10^11km)를 말한다.</p>
        </div>
        <div className="border border-[#EBB67B] rounded-xl p-2 my-3">
          <p className="text-[#EBB67B] text-lg">ZHR(Zenithal Hourly Rate)</p>
          <p className="text-base">6.5등성까지는 보이는 맑은 밤, 유성우의 복사점이 천정에 있다고 가정할 때 1시간 동안 한 사람이 맨눈으로 관측할 수 있는 유성의 수, 실제로 관측할 수 있는 것은 이보다 적다.</p>
        </div>
      {/* ))} */}
    </div>
  );
};

export default MyStarList;
