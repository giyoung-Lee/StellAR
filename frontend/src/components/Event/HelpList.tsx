const MyStarList = () => {
  const myStarList = new Array(2).fill(0);
  return (
    <div>
      {myStarList.map(() => (
        <div className="border border-[#EBB67B] rounded-xl p-2 my-3">
          <p className="text-[#EBB67B] text-lg">근일점</p>
          <p className="text-base">태양과 지구 사이의 거리가 최소가 되는 지점이다.</p>
        </div>
      ))}
    </div>
  );
};

export default MyStarList;
