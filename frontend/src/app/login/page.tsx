"use client";
import { useState } from "react";
import { useRouter } from "next/navigation";
import axios from "@/utils/axios";
import Title from "@/components/Title";
import { motion, AnimatePresence } from "framer-motion";
import confetti from "canvas-confetti";
const LoginPage = () => {
  const [id, setId] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState(false);
  const [showModal, setShowModal] = useState(false);
  const router = useRouter();

  const fireConfetti = () => {
    confetti({
      particleCount: 200,
      spread: 120,
      origin: { y: 0.6 },
      scalar: 0.8,
      colors: ["#A3C4F3", "#FFB6B9", "#FFDAC1", "#E2F0CB", "#B5EAD7"],
    });
  };

  const handleLogin = async () => {
    if (!id || !password) {
      setError(true);
      return;
    }
    try {
      const res = await axios.post("/api/v1/auth/sign-in", {
        username: id,
        password,
      });
      if (res.data.status === 200) {
        const { accessToken, role } = res.data.data;
        localStorage.setItem("accessToken", accessToken);
        localStorage.setItem("userRole", role);
        setShowModal(true);
        fireConfetti();
        setTimeout(() => {
          setShowModal(false);
          router.push("/");
        }, 2000);
      } else {
        setError(true);
      }
    } catch (err) {
      console.error("Login failed:", err);
      setError(true);
    }
  };

  return (
    <div className="flex flex-col items-center w-[390px] pt-[164px] h-[844px] mx-auto p-6 relative overflow-hidden">
      <Title pageTitle="로그인" />

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
        className="w-full font-bold bg-primary text-white rounded-full py-2 text-lg mt-10"
        onClick={handleLogin}
      >
        로그인
      </button>

      <div className="w-full flex justify-between mt-4 text-xs text-gray-700">
        <button className="underline" onClick={() => router.push("/signup")}>
          회원가입
        </button>
        <div className="flex gap-4">
          <button
            className="underline"
            onClick={() => router.push("/find?tab=id")}
          >
            아이디 찾기
          </button>
          <button
            className="underline"
            onClick={() => router.push("/find?tab=password")}
          >
            비밀번호 찾기
          </button>
        </div>
      </div>

      <AnimatePresence>
        {showModal && (
          <motion.div
            className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 z-50"
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            exit={{ opacity: 0 }}
          >
            <motion.div
              className="bg-white p-6 rounded-lg shadow-lg text-center"
              initial={{ scale: 0.8, opacity: 0 }}
              animate={{ scale: 1, opacity: 1 }}
              exit={{ scale: 0.8, opacity: 0 }}
              transition={{ duration: 0.3 }}
            >
              <p className="text-lg font-semibold">로그인에 성공했습니다!</p>
            </motion.div>
          </motion.div>
        )}
      </AnimatePresence>
    </div>
  );
};

export default LoginPage;
