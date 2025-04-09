"use client";

import { useState } from "react";
import axios from "@/utils/axios";
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

  const handleCheckUsername = async () => {
    try {
      const res = await axios.post("/api/v1/auth/id-check", { username });
      console.log("중복 확인 응답:", res.data);
      setIsUsernameAvailable(res.data.status === 200);
    } catch (error) {
      console.error("Username check failed", error);
      setIsUsernameAvailable(false);
    }
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

  const handleSendVerification = async () => {
    try {
      const res = await axios.post("/api/v1/email/email-certification", {
        username,
        email,
      });
      if (res.data.status === 200) {
        setShowVerificationField(true);
        setIsEmailAvailable(true);
      } else {
        setIsEmailAvailable(false);
        alert(res.data.message || "이메일 인증 요청 실패");
      }
    } catch (error) {
      console.error("Email verification request failed", error);
      setIsEmailAvailable(false);
    }
  };

  const handleVerifyCode = async () => {
    try {
      const res = await axios.post("/api/v1/auth/check-certification", {
        username,
        email,
        certificationNumber: verificationCode,
      });
      setIsVerificationSuccess(res.data.status === 200);
    } catch (error) {
      console.error("Verification failed", error);
      setIsVerificationSuccess(false);
    }
  };

  const handleSignup = async () => {
    try {
      const res = await axios.post("/api/v1/auth/sign-up", {
        nickname: name,
        username,
        password,
        confirmPassword,
        email,
        phone,
        certificationNumber: verificationCode,
      });
      if (res.data.status === 200) {
        alert("회원가입 완료");
      } else {
        alert(res.data.message || "회원가입 실패");
      }
    } catch (error) {
      console.error("Signup error", error);
      alert("회원가입 중 오류가 발생했습니다");
    }
  };

  const isSignupDisabled =
    !username ||
    !password ||
    !confirmPassword ||
    !email ||
    !name ||
    !phone ||
    !isPrivacyChecked ||
    isUsernameAvailable !== true ||
    !isPasswordValid ||
    !isPasswordMatch ||
    !isVerificationSuccess;

  return (
    <div className="max-w-md mx-auto p-6 pt-[164px]">
      <Title pageTitle="회원가입" />

      <SignupInputField
        label="아이디"
        type="text"
        placeholder="아이디 입력"
        value={username}
        onChange={(e) => {
          setUsername(e.target.value);
          setIsUsernameAvailable(null);
        }}
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
        error={isEmailAvailable === false}
        errorText="* 이미 사용 중인 이메일입니다."
        success={isEmailAvailable === true}
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
        onClick={handleSignup}
        disabled={isSignupDisabled}
      />
    </div>
  );
};

export default Signup;
