// components/Button.tsx
import React from 'react';

interface ButtonProps {
  width?: string;     // Tailwind width 클래스
  children: React.ReactNode;
  onClick?: () => void;
}

const Button = ({ width = 'w-[123px]', children, onClick }: ButtonProps) => {
  return (
    <button
      onClick={onClick}
      className={`
        ${width}
        h-8
        flex
        justify-center
        items-center
        px-[18px]
        py-2
        bg-[#353395]
        rounded-[31px]
        text-white
        gap-[10px]
      `}
    >
      {children}
    </button>
  );
};

export default Button;