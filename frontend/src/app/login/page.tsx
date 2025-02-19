"use client";
import { useState } from "react";

const LoginPage = () => {
  const [id, setId] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState(false);

  const handleLogin = () => {
    if (!id || !password) {
      setError(true);
      return;
    }
    setError(false);
    console.log("로그인 요청"); // 추후 서버 연동
  };

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
          value={id}
          onChange={(e) => setId(e.target.value)}
          placeholder="아이디 입력"
          className={`w-full border-b outline-none py-2 text-lg mt-2 ${
            error ? "border-red-500" : "border-gray-600"
          }`}
        />
      </div>

      <div className="w-full mt-6">
        <label className="block text-left text-lg font-medium text-gray-700">
          비밀번호
        </label>
        <input
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          placeholder="비밀번호 입력"
          className={`w-full border-b outline-none py-2 text-lg mt-2 ${
            error ? "border-red-500" : "border-gray-600"
          }`}
        />
      </div>
      {error && (
        <p className="text-red-500 text-sm mt-4 w-full text-left">
          * 아이디 또는 비밀번호가 잘못되었습니다.
          <br />
          정보를 정확히 입력해주세요.
        </p>
      )}
      <button
        className="w-full font-bold bg-[#353395] text-white rounded-full py-2 text-lg mt-10"
        onClick={handleLogin}
      >
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
