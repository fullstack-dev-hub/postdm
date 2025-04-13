"use client";

import React, { useState, useEffect } from "react";
import Image from "next/image";
import Link from "next/link";
import axios from "@/utils/axios";
import { useRouter } from "next/navigation";
import { motion, AnimatePresence } from "framer-motion";

const Header = () => {
  const [isNavOpen, setIsNavOpen] = useState(false);
  const [showLogoutModal, setShowLogoutModal] = useState(false);
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const router = useRouter();

  // Check login status on every navigation bar open
  useEffect(() => {
    const checkLoginStatus = () => {
      const accessToken = localStorage.getItem("accessToken");
      setIsLoggedIn(!!accessToken);
    };

    // Initial check
    checkLoginStatus();

    // Setup event listener for localStorage changes
    window.addEventListener("storage", checkLoginStatus);
    
    // Custom event listener for login/logout within the same tab
    window.addEventListener("auth-state-changed", checkLoginStatus);

    return () => {
      window.removeEventListener("storage", checkLoginStatus);
      window.removeEventListener("auth-state-changed", checkLoginStatus);
    };
  }, []);
  
  // Check login status every time navigation opens
  useEffect(() => {
    if (isNavOpen) {
      const accessToken = localStorage.getItem("accessToken");
      setIsLoggedIn(!!accessToken);
    }
  }, [isNavOpen]);

  const toggleNav = () => {
    // Check login status before opening nav
    const accessToken = localStorage.getItem("accessToken");
    setIsLoggedIn(!!accessToken);
    setIsNavOpen(!isNavOpen);
  };

  const closeNav = () => {
    setIsNavOpen(false);
  };

  const handleLogout = async () => {
    try {
      const accessToken = localStorage.getItem("accessToken");
      await axios.post(
        "/api/v1/auth/sign-out",
        {},
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        }
      );
      localStorage.removeItem("accessToken");
      localStorage.removeItem("userRole");
      
      // Dispatch custom event to notify the app about auth state change
      window.dispatchEvent(new Event("auth-state-changed"));
      
      setShowLogoutModal(true);
      setIsLoggedIn(false);
      closeNav();
      setTimeout(() => {
        setShowLogoutModal(false);
        router.push("/login");
      }, 1500);
    } catch (err) {
      console.error("로그아웃 실패:", err);
    }
  };

  const handleLogin = () => {
    closeNav();
    router.push("/login");
  };

  useEffect(() => {
    if (isNavOpen) {
      document.body.style.overflow = "hidden";
    } else {
      document.body.style.overflow = "auto";
    }
    return () => {
      document.body.style.overflow = "auto";
    };
  }, [isNavOpen]);

  return (
    <>
      <header className="fixed top-0 left-0 right-0 h-[100px] flex items-center px-5 bg-white z-50">
        <Link href="/" className="flex items-center">
          <div className="relative w-[42px] h-[40px]">
            <Image
              src="/images/logo.svg"
              alt="Logo"
              fill
              className="object-contain"
              priority
            />
          </div>
          <div className="relative ml-[15px]">
            <Image
              src="/images/POSTDM.svg"
              alt="POSTDM"
              width={66}
              height={24}
              className="object-contain"
              style={{
                fontFamily: "Impact",
                fontSize: "20px",
                lineHeight: "24px",
                fontWeight: 400,
              }}
            />
          </div>
        </Link>

        <button
          className="relative w-[22px] h-[20px] ml-auto flex items-center justify-center"
          aria-label="Toggle navigation"
          onClick={toggleNav}
        >
          <Image
            src="/images/nav.svg"
            alt="Navigation menu"
            width={22}
            height={19}
            className="object-contain"
          />
        </button>
      </header>

      {isNavOpen && (
        <div
          className="fixed inset-0 bg-black bg-opacity-50 z-[60]"
          onClick={toggleNav}
        >
          <div
            className="absolute right-0 top-0 h-full w-3/5 max-w-[220px] bg-white z-[70]"
            onClick={(e) => e.stopPropagation()}
          >
            <div className="flex justify-end p-5">
              <button
                onClick={toggleNav}
                className="p-2"
                aria-label="Close navigation"
              >
                <svg
                  width="24"
                  height="24"
                  viewBox="0 0 24 24"
                  fill="none"
                  xmlns="http://www.w3.org/2000/svg"
                >
                  <path
                    d="M18 6L6 18"
                    stroke="black"
                    strokeWidth="2"
                    strokeLinecap="round"
                    strokeLinejoin="round"
                  />
                  <path
                    d="M6 6L18 18"
                    stroke="black"
                    strokeWidth="2"
                    strokeLinecap="round"
                    strokeLinejoin="round"
                  />
                </svg>
              </button>
            </div>

            <nav className="px-5 mt-5">
              <ul className="divide-y divide-gray-200">
                <li className="py-4">
                  <Link 
                    href="/" 
                    className="block w-full text-sm font-medium"
                    onClick={closeNav}
                  >
                    홈
                  </Link>
                </li>
                <li className="py-4">
                  <Link
                    href="/about"
                    className="block w-full text-sm font-medium"
                    onClick={closeNav}
                  >
                    회사 소개
                  </Link>
                </li>
                
                {isLoggedIn ? (
                  <>
                    <li className="py-4">
                      <Link
                        href="/estimate/list"
                        className="block w-full text-sm font-medium"
                        onClick={closeNav}
                      >
                        나의 견적서
                      </Link>
                    </li>
                    <li className="py-4">
                      <Link
                        href="/mypage"
                        className="block w-full text-sm font-medium"
                        onClick={closeNav}
                      >
                        마이페이지
                      </Link>
                    </li>
                    <li className="py-4">
                      <button
                        onClick={handleLogout}
                        className="block w-full text-left text-sm font-medium"
                      >
                        로그아웃
                      </button>
                    </li>
                  </>
                ) : (
                  <li className="py-4">
                    <button
                      onClick={handleLogin}
                      className="block w-full text-left text-sm font-medium"
                    >
                      로그인
                    </button>
                  </li>
                )}
              </ul>
            </nav>
          </div>
        </div>
      )}

      <AnimatePresence>
        {showLogoutModal && (
          <motion.div
            className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 z-[80]"
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            exit={{ opacity: 0 }}
          >
            <motion.div
              className="bg-white px-6 py-4 rounded-lg shadow-lg text-center"
              initial={{ scale: 0.9, opacity: 0 }}
              animate={{ scale: 1, opacity: 1 }}
              exit={{ scale: 0.9, opacity: 0 }}
              transition={{ duration: 0.3 }}
            >
              <p className="text-lg font-semibold">로그아웃되었습니다.</p>
            </motion.div>
          </motion.div>
        )}
      </AnimatePresence>
    </>
  );
};

export default Header;