"use client";

import { useState } from "react";
import SignupInputField from "@/components/signup/SignupInputField";
import PrivacyAgreement from "@/components/signup/PrivacyAgreement";
import PrimaryButton from "@/components/find/PrimaryButton";
import Title from "@/components/Title";

const Signup = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [email, setEmail] = useState("");
  const [verificationCode, setVerificationCode] = useState("");
  const [name, setName] = useState("");
  const [phone, setPhone] = useState("");

  const [isUsernameAvailable, setIsUsernameAvailable] = useState<
    boolean | null
  >(null);
  const [isPasswordValid, setIsPasswordValid] = useState(false);
  const [isPasswordMatch, setIsPasswordMatch] = useState(false);
  const [showVerificationField, setShowVerificationField] = useState(false);
  const [isEmailAvailable, setIsEmailAvailable] = useState<boolean | null>(
    null
  );
  const [isVerificationSuccess, setIsVerificationSuccess] = useState<
    boolean | null
  >(null);
  const [isPrivacyChecked, setIsPrivacyChecked] = useState(false);

  const handleCheckUsername = () => {
    // 아이디 중복 확인 -> 추후 서버 연동
    // 예제: "testuser"는 이미 사용 중
    setIsUsernameAvailable(username !== "testuser");
  };

  const validatePassword = (value: string) => {
    const isValid =
      value.length >= 8 && /\d/.test(value) && /[!@#$%^&*]/.test(value);
    setIsPasswordValid(isValid);
    setPassword(value);
  };

  const checkPasswordMatch = (value: string) => {
    setConfirmPassword(value);
    setIsPasswordMatch(password === value);
  };

  const handleSendVerification = () => {
    // 이메일 인증 요청 -> 추후 서버 연동
    setShowVerificationField(true);
    // 예제: "test@example.com"은 이미 사용 중
    setIsEmailAvailable(email !== "test@example.com");
  };

  const handleVerifyCode = () => {
    // 인증번호 확인 -> 추후 서버 연동
    setIsVerificationSuccess(verificationCode === "123456");
  };

  const isSignupDisabled =
    !username ||
    !password ||
    !confirmPassword ||
    !email ||
    !name ||
    !phone ||
    !isPrivacyChecked ||
    isUsernameAvailable === false ||
    isEmailAvailable === false ||
    !isPasswordValid ||
    !isPasswordMatch;

  return (
    <div className="max-w-md mx-auto p-6 pt-[164px]">
      <Title pageTitle="회원가입" />

      <SignupInputField
        label="아이디"
        type="text"
        placeholder="아이디 입력"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
        buttonText="중복 확인"
        onButtonClick={handleCheckUsername}
        success={isUsernameAvailable === true}
        error={isUsernameAvailable === false}
        errorText="* 사용 중인 아이디입니다."
      />

      <SignupInputField
        label="비밀번호"
        type="password"
        placeholder="비밀번호 입력"
        value={password}
        onChange={(e) => validatePassword(e.target.value)}
        helperText="* 영문 + 숫자 + 특수문자 포함 8자 이상"
        error={!isPasswordValid && password.length > 0}
        errorText="* 영문 + 숫자 + 특수문자 8자 이상"
        success={isPasswordValid}
      />

      <SignupInputField
        label="비밀번호 확인"
        type="password"
        placeholder="비밀번호 재입력"
        value={confirmPassword}
        onChange={(e) => checkPasswordMatch(e.target.value)}
        error={confirmPassword.length > 0 && !isPasswordMatch}
        errorText="* 비밀번호가 일치하지 않습니다."
        success={isPasswordMatch}
      />

      <SignupInputField
        label="이메일"
        type="email"
        placeholder="이메일 입력"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
        buttonText="인증"
        onButtonClick={handleSendVerification}
        success={isEmailAvailable === true}
        error={isEmailAvailable === false}
        errorText="* 이미 사용 중인 이메일입니다."
      />

      {showVerificationField && (
        <SignupInputField
          label="인증번호"
          type="text"
          placeholder="인증번호 입력"
          value={verificationCode}
          onChange={(e) => setVerificationCode(e.target.value)}
          buttonText="확인"
          onButtonClick={handleVerifyCode}
          success={isVerificationSuccess === true}
          error={isVerificationSuccess === false}
          errorText="* 인증번호를 다시 확인해주세요."
        />
      )}

      <SignupInputField
        label="이름"
        type="text"
        placeholder="이름 입력"
        value={name}
        onChange={(e) => setName(e.target.value)}
      />

      <SignupInputField
        label="휴대폰 번호"
        type="tel"
        placeholder="휴대폰 번호 입력"
        value={phone}
        onChange={(e) => setPhone(e.target.value.replace(/\D/g, ""))}
      />

      <PrivacyAgreement
        isChecked={isPrivacyChecked}
        setIsChecked={setIsPrivacyChecked}
      />

      <PrimaryButton
        text="회원가입"
        onClick={() => alert("회원가입 완료")}
        disabled={isSignupDisabled}
      />
    </div>
  );
};

export default Signup;
