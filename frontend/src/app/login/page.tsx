const LoginPage = () => {
  return (
    <div className="flex flex-col items-center w-[390px] h-[844px] mx-auto p-6">
      <div className="w-full h-16 border-b border-gray-400 flex items-center justify-center text-gray-500">
        상단바 공간
      </div>

      <div className="w-full mt-8">
        <label className="block text-left text-lg font-medium text-gray-700">
          아이디
        </label>
        <input
          type="text"
          placeholder="아이디 입력"
          className="w-full border-b border-gray-600 outline-none py-2 text-lg mt-2"
        />
      </div>

      <div className="w-full mt-6">
        <label className="block text-left text-lg font-medium text-gray-700">
          비밀번호
        </label>
        <input
          type="password"
          placeholder="비밀번호 입력"
          className="w-full border-b border-gray-600 outline-none py-2 text-lg mt-2"
        />
      </div>

      <button className="w-full font-bold bg-[#353395] text-white rounded-full py-2 text-lg mt-10">
        로그인
      </button>

      <div className="w-full flex justify-between mt-4 text-xs text-gray-700">
        <button className="underline">회원가입</button>
        <div className="flex gap-4">
          <button className="underline">아이디 찾기</button>
          <button className="underline">비밀번호 찾기</button>
        </div>
      </div>
    </div>
  );
};

export default LoginPage;
