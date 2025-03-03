// app/estimate/write/page.tsx
'use client';

import React, { useState } from 'react';
import { useRouter } from 'next/navigation';
import Title from '@/components/Title';

export default function EstimateWritePage() {
  const router = useRouter();
  const [estimateContent, setEstimateContent] = useState('');

  // 견적서 제출 처리 함수
  const handleSubmit = () => {
    // 여기에 실제 API 호출 등 제출 로직 추가
    console.log('제출된 견적 내용:', estimateContent);
    
    // 제출 후 done 페이지로 이동
    router.push('/estimate/done');
  };

  return (
    <main className="flex flex-col min-h-screen">
      {/* 고정된 요소들 - Title */}
      <Title pageTitle="견적서 작성" />
      
      <div className="relative px-5 pt-[164px] pb-10 w-full">
        {/* 견적 내용 라벨 */}
        <div className="ml-[4px] mb-2 mt-[12px]">
          <h2 className="font-inter font-bold text-[17px] leading-[21px] text-black">
            견적내용
          </h2>
        </div>
        
        {/* 견적 내용 입력 박스 - 반응형 */}
        <div className="w-full mx-auto">
          <textarea
            className="box-border w-full h-[232px] p-[10px] text-[13px] leading-[20px] font-inter font-normal border border-solid border-[rgba(53,51,155,0.9)] rounded-[5px] resize-none focus:outline-none"
            placeholder={`우편발송 수량 / 
택배포장 수량 우편발송 할 내용물 종류 / 
택배포장할 내용물 종류 /`}
            value={estimateContent}
            onChange={(e) => setEstimateContent(e.target.value)}
            style={{ color: estimateContent ? 'black' : 'rgba(0, 0, 0, 0.3)' }}
          />
        </div>
        
        {/* 제출 버튼 - 오른쪽 정렬 및 크기 조정 */}
        <div className="flex justify-end mt-5">
          <button 
            onClick={handleSubmit}
            className="flex justify-center items-center px-5 py-[10px] w-[121px] h-[38px] bg-[#353395] rounded-[20px] text-white text-sm font-bold"
          >
            견적서 보내기
          </button>
        </div>
      </div>
    </main>
  );
}