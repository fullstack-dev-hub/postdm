// src/components/Header.tsx
'use client';

import React, { useState } from 'react';
import Image from 'next/image';
import Link from 'next/link';

const Header = () => {
  const [isNavOpen, setIsNavOpen] = useState(false);

  const toggleNav = () => {
    setIsNavOpen(!isNavOpen);
  };

  // Prevent scrolling when nav is open
  React.useEffect(() => {
    if (isNavOpen) {
      document.body.style.overflow = 'hidden';
    } else {
      document.body.style.overflow = 'auto';
    }
    
    return () => {
      document.body.style.overflow = 'auto';
    };
  }, [isNavOpen]);

  return (
    <>
      <header className="fixed top-0 left-0 right-0 h-[100px] flex items-center px-5 bg-white z-50">
        {/* Logo with Link to Home */}
        <Link href="/home" className="flex items-center">
          <div className="relative w-[42px] h-[40px]">
            <Image
              src="/images/logo.svg"
              alt="Logo"
              fill
              className="object-contain"
              priority
            />
          </div>
          
          {/* POSTDM */}
          <div className="relative ml-[15px]">
            <Image
              src="/images/POSTDM.svg"
              alt="POSTDM"
              width={66}
              height={24}
              className="object-contain"
              style={{
                fontFamily: 'Impact',
                fontSize: '20px',
                lineHeight: '24px',
                fontWeight: 400,
              }}
            />
          </div>
        </Link>
        
        {/* Navigation Button */}
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

      {/* Navigation Overlay - 높은 z-index로 Title까지 가리도록 설정 */}
      {isNavOpen && (
        <div className="fixed inset-0 bg-black bg-opacity-50 z-[60]" onClick={toggleNav}>
          {/* Navigation Menu */}
          <div 
            className="absolute right-0 top-0 h-full w-3/5 max-w-[220px] bg-white z-[70]"
            onClick={(e) => e.stopPropagation()} // Prevent clicks inside from closing
          >
            {/* Close button */}
            <div className="flex justify-end p-5">
              <button 
                onClick={toggleNav}
                className="p-2"
                aria-label="Close navigation"
              >
                <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                  <path d="M18 6L6 18" stroke="black" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
                  <path d="M6 6L18 18" stroke="black" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
                </svg>
              </button>
            </div>

            {/* Navigation Items */}
            <nav className="px-5 mt-5">
              <ul className="divide-y divide-gray-200">
                <li className="py-4">
                  <Link href="/home" className="block w-full text-sm font-medium">
                    홈
                  </Link>
                </li>
                <li className="py-4">
                  <Link href="/company" className="block w-full text-sm font-medium">
                    회사 소개
                  </Link>
                </li>
                <li className="py-4">
                  <Link href="/contact" className="block w-full text-sm font-medium">
                    나의 견적서
                  </Link>
                </li>
                <li className="py-4">
                  <Link href="/mypage" className="block w-full text-sm font-medium">
                    마이페이지
                  </Link>
                </li>
                <li className="py-4">
                  <Link href="/logout" className="block w-full text-sm font-medium">
                    로그아웃
                  </Link>
                </li>
              </ul>
            </nav>
          </div>
        </div>
      )}
    </>
  );
};

export default Header;