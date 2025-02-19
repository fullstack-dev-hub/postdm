import React from 'react';
import Image from 'next/image';

interface TitleProps {
  pageTitle: string;  // 페이지 이름을 props로 받습니다
}

const Title = ({ pageTitle }: TitleProps) => {
  return (
    <div className="relative w-full h-[64px] mt-[100px]">
      {/* Back button */}
      <div className="absolute left-[22px] top-[14px] w-[42px] h-[40px]">
        <Image
          src="/images/navigate_before.svg"
          alt="Go back"
          width={42}
          height={40}
          className="object-contain"
        />
      </div>
      
      {/* Page title */}
      <h1 className="absolute left-1/2 top-[24px] -translate-x-1/2 
                     font-inter font-semibold text-[20px] leading-[24px] 
                     tracking-[-0.025em] text-black w-[95px] text-center">
        {pageTitle}
      </h1>
    </div>
  );
};

export default Title;