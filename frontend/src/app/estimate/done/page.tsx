// app/estimate/done/page.tsx
'use client';

import React from 'react';
import { useRouter } from 'next/navigation';
import Image from 'next/image';

export default function EstimateDonePage() {
  const router = useRouter();

  // 나의 견적서 페이지로 이동하는 함수
  const goToMyEstimates = () => {
    router.push('/estimate/list');
  };

  return (
    <main className="flex flex-col items-center justify-center min-h-screen px-5">
      {/* done-icon SVG */}
      <div className="relative w-[60px] h-[60px] mb-8">
        <Image
          src="/images/done-icon.svg"
          alt="완료 아이콘"
          fill
          className="object-contain"
        />
      </div>
      
      {/* 성공 메시지 */}
      <h1 className="font-inter font-semibold text-[20px] leading-[24px] tracking-[-0.025em] text-black mb-12 text-center">
        견적서를 성공적으로 보냈습니다.
      </h1>
      
      {/* 나의 견적서 확인 버튼 */}
      <button 
        onClick={goToMyEstimates}
        className="flex justify-center items-center px-[43px] py-[10px] w-[191px] h-[38px] bg-[#353395] rounded-[20px] text-white text-sm font-bold"
      >
        나의 견적서 확인
      </button>
    </main>
  );
}