// src/components/Header.tsx
'use client';

import React from 'react';
import Image from 'next/image';

const Header = () => {
  return (
    <header className="fixed top-0 left-0 right-0 h-[100px] flex items-center px-5 bg-white z-50">
      {/* Logo */}
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
      
      {/* Navigation Button */}
      <button 
        className="relative w-[22px] h-[20px] ml-auto flex items-center justify-center"
        aria-label="Toggle navigation"
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
  );
};

export default Header;