"use client";

import { useRouter, useSearchParams } from "next/navigation";
import { useState } from "react";

const FindPage = () => {
  const router = useRouter();
  const searchParams = useSearchParams();
  const tab = searchParams.get("tab") || "id";

  const [foundId, setFoundId] = useState<string | null>(null);
  const [showVerification, setShowVerification] = useState(false);
  const [isVerified, setIsVerified] = useState(false);

  const handleTabChange = (newTab: string) => {
    router.push(`/find?tab=${newTab}`);
    setFoundId(null);
    setShowVerification(false);
    setIsVerified(false);
  };

  const handleFindId = () => {
    // 추후 연동 필요
    setFoundId("ekdm*******");
  };

  const handleVerifyEmail = () => {
    setShowVerification(true);
  };

  const handleConfirmVerification = () => {
    setIsVerified(true);
  };

  return (
    <div className="flex flex-col items-center w-[390px] h-[844px] mx-auto p-6">
      <div className="w-full h-16 border-b border-gray-400 flex items-center justify-center text-gray-500">
        상단바 공간
      </div>

      <div className="w-full flex border-b">
        <button
          onClick={() => handleTabChange("id")}
          className={`w-1/2 py-3 text-center font-medium border-b-2 ${
            tab === "id"
              ? "text-[#353395] border-[#353395] font-semibold"
              : "text-gray-400 border-gray-400"
          }`}
        >
          아이디 찾기
        </button>
        <button
          onClick={() => handleTabChange("password")}
          className={`w-1/2 py-3 text-center font-medium border-b-2 ${
            tab === "password"
              ? "text-[#353395] border-[#353395] font-semibold"
              : "text-gray-400 border-gray-400"
          }`}
        >
          비밀번호 찾기
        </button>
      </div>

      <div className="w-full mt-6">
        {tab === "id" ? (
          foundId ? (
            <div className="text-center">
              <p className="text-[#353395] text-lg font-bold">
                고객님과 일치하는 아이디입니다.
              </p>
              <p className="text-xl font-semibold mt-2"> ID : {foundId}</p>
              <button
                className="w-full bg-[#353395] font-bold text-white rounded-full py-2 text-lg mt-6"
                onClick={() => router.push("/login")}
              >
                로그인
              </button>
              <button
                className="w-full text-sm underline mt-4"
                onClick={() => handleTabChange("password")}
              >
                비밀번호 찾기
              </button>
            </div>
          ) : (
            <div>
              <label className="block text-left text-lg font-medium text-gray-700">
                이메일
              </label>
              <input
                type="email"
                placeholder="이메일 주소 입력"
                className="w-full border-b border-gray-600 outline-none py-2 text-lg mt-2"
              />
              <button
                className="w-full bg-[#353395] font-semibold text-white rounded-full py-2 text-lg mt-6"
                onClick={handleFindId}
              >
                아이디 확인하기
              </button>
            </div>
          )
        ) : (
          <div>
            <label className="block text-left text-lg font-medium text-gray-700">
              아이디
            </label>
            <input
              type="text"
              placeholder="아이디 입력"
              className="w-full border-b border-gray-600 outline-none py-2 text-lg mt-2"
            />

            <label className="block text-left text-lg font-medium text-gray-700 mt-6">
              이메일
            </label>
            <div className="w-full flex items-center">
              <input
                type="email"
                placeholder="이메일 주소 입력"
                className="w-full border-b border-gray-600 outline-none py-2 text-lg"
              />
              <button
                className="ml-2 font-bold px-4 py-2 bg-[#353395] text-white rounded-md text-sm whitespace-nowrap"
                onClick={handleVerifyEmail}
              >
                인증
              </button>
            </div>
            {showVerification && (
              <div className="mt-4">
                <label className="block text-left text-lg font-medium text-gray-700">
                  인증번호
                </label>
                <div className="w-full flex items-center">
                  <input
                    type="text"
                    placeholder="인증번호 입력"
                    className="w-full border-b border-gray-600 outline-none py-2 text-lg"
                  />
                  <button
                    className="ml-2 font-bold px-4 py-2 bg-[#353395] text-white rounded-md text-sm whitespace-nowrap"
                    onClick={handleConfirmVerification}
                  >
                    확인
                  </button>
                </div>
              </div>
            )}

            <button
              className={`w-full font-semibold rounded-full py-2 text-lg mt-6 ${
                isVerified
                  ? "bg-[#353395] text-white"
                  : "bg-[#353395] text-white opacity-50 cursor-not-allowed"
              }`}
              disabled={!isVerified}
            >
              비밀번호 재설정
            </button>
          </div>
        )}
      </div>
    </div>
  );
};

export default FindPage;
