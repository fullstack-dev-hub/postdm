"use client";

import { useRouter, useSearchParams } from "next/navigation";

const FindPage = () => {
  const router = useRouter();
  const searchParams = useSearchParams();
  const tab = searchParams.get("tab") || "id";

  const handleTabChange = (newTab: string) => {
    router.push(`/find?tab=${newTab}`);
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
          <div>
            <label className="block text-left text-lg font-medium text-gray-700">
              이메일
            </label>
            <input
              type="email"
              placeholder="이메일 주소 입력"
              className="w-full border-b border-gray-600 outline-none py-2 text-lg mt-2"
            />
            <button className="w-full bg-[#353395] text-white rounded-full py-2 text-lg mt-6">
              아이디 확인하기
            </button>
          </div>
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
              <button className="ml-2 font-bold px-4 py-2 bg-[#353395] text-white rounded-md text-sm whitespace-nowrap">
                인증
              </button>
            </div>

            <button className="w-full bg-[#353395] text-white rounded-full py-2 text-lg mt-6">
              비밀번호 재설정
            </button>
          </div>
        )}
      </div>
    </div>
  );
};

export default FindPage;
