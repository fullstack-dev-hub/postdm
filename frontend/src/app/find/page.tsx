"use client";

import { useRouter, useSearchParams } from "next/navigation";
import { useState } from "react";
import { CheckCircleIcon, XCircleIcon } from "@heroicons/react/24/solid";
import PrimaryButton from "@/components/find/PrimaryButton";
import InputField from "@/components/find/InputField";

const FindPage = () => {
  const router = useRouter();
  const searchParams = useSearchParams();
  const tab = searchParams.get("tab") || "id";

  const [foundId, setFoundId] = useState<string | null>(null);
  const [showVerification, setShowVerification] = useState(false);
  const [isVerified, setIsVerified] = useState(false);
  const [verificationCode, setVerificationCode] = useState("");
  const [verificationError, setVerificationError] = useState(false);
  const [showPasswordReset, setShowPasswordReset] = useState(false);

  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");

  const handleTabChange = (newTab: string) => {
    router.push(`/find?tab=${newTab}`);
    setFoundId(null);
    setShowVerification(false);
    setIsVerified(false);
    setVerificationCode("");
    setVerificationError(false);
    setShowPasswordReset(false);
  };

  const handleFindId = () => {
    // 추후 연동 필요
    setFoundId("ekdm*******");
  };

  const handleVerifyEmail = () => {
    setShowVerification(true);
  };

  const handleConfirmVerification = () => {
    if (verificationCode === "1234") {
      // 추후 연동 필요, 우선 인증코드가 1234면 통과
      setIsVerified(true);
      setVerificationError(false);
    } else {
      setIsVerified(false);
      setVerificationError(true);
    }
  };

  const handlePasswordReset = () => {
    setShowPasswordReset(true);
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
              ? "text-primary border-primary font-semibold"
              : "text-gray-400 border-gray-400"
          }`}
        >
          아이디 찾기
        </button>
        <button
          onClick={() => handleTabChange("password")}
          className={`w-1/2 py-3 text-center font-medium border-b-2 ${
            tab === "password"
              ? "text-primary border-primary font-semibold"
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
              <p className="text-primary text-lg font-bold">
                고객님과 일치하는 아이디입니다.
              </p>
              <p className="text-xl font-semibold mt-2"> ID : {foundId}</p>
              <PrimaryButton
                text="로그인"
                onClick={() => router.push("/login")}
              />
              <button
                className="w-full text-sm underline mt-4"
                onClick={() => handleTabChange("password")}
              >
                비밀번호 찾기
              </button>
            </div>
          ) : (
            <div>
              <InputField
                label="이메일"
                type="email"
                placeholder="이메일 주소 입력"
              />
              <PrimaryButton text="아이디 확인하기" onClick={handleFindId} />
            </div>
          )
        ) : showPasswordReset ? (
          <div>
            <InputField
              label="변경 비밀번호"
              type="password"
              placeholder="새로 변경할 비밀번호를 입력해주세요"
              value={newPassword}
              onChange={(e) => setNewPassword(e.target.value)}
              helperText="*영문 + 숫자 + 특수문자 8자 이상"
            />

            <InputField
              label="변경 비밀번호 재확인"
              type="password"
              placeholder="비밀번호를 재입력해주세요"
              value={confirmPassword}
              onChange={(e) => setConfirmPassword(e.target.value)}
            />
            <PrimaryButton text="비밀번호 변경하기" />
          </div>
        ) : (
          <div>
            <InputField label="아이디" type="text" placeholder="아이디 입력" />
            <div className="w-full flex items-center">
              <InputField
                label="이메일"
                type="email"
                placeholder="이메일 주소 입력"
              >
                <button
                  className="font-bold px-4 py-2 bg-primary text-white rounded-md text-sm whitespace-nowrap"
                  onClick={handleVerifyEmail}
                >
                  인증
                </button>
              </InputField>
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
                    value={verificationCode}
                    onChange={(e) => setVerificationCode(e.target.value)}
                    className={`flex-grow border-b outline-none py-2 text-lg ${
                      verificationError ? "border-red-500" : "border-gray-600"
                    }`}
                  />
                  {isVerified && (
                    <CheckCircleIcon className="w-6 h-6 text-green-500 flex-shrink-0 ml-2" />
                  )}
                  {verificationError && (
                    <XCircleIcon className="w-6 h-6 text-red-500 flex-shrink-0 ml-2" />
                  )}
                  <button
                    className="ml-2 font-bold px-4 py-2 bg-primary text-white rounded-md text-sm whitespace-nowrap"
                    onClick={handleConfirmVerification}
                  >
                    확인
                  </button>
                </div>
                {verificationError && (
                  <p className="text-red-500 font-semibold text-sm mt-1">
                    * 인증번호를 다시 확인해주세요.
                  </p>
                )}
              </div>
            )}
            <PrimaryButton
              text="비밀번호 재설정"
              onClick={handlePasswordReset}
              disabled={!isVerified}
            />
          </div>
        )}
      </div>
    </div>
  );
};

export default FindPage;
