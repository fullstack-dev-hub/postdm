// src/app/estimate/view/[id]/EstimateViewClient.tsx
'use client';

import React from 'react';
import Title from '@/components/Title';
import { useEstimateDetail } from '@/hooks/useEstimates';

interface EstimateViewClientProps {
  id: string;
}

export default function EstimateViewClient({ id }: EstimateViewClientProps) {
  const { estimate, loading, error } = useEstimateDetail(id);

  if (loading) {
    return (
      <main className="flex flex-col min-h-screen">
        <Title pageTitle="견적서 상세" />
        <div className="flex justify-center items-center h-screen">
          <div className="animate-spin rounded-full h-8 w-8 border-t-2 border-b-2 border-gray-900"></div>
        </div>
      </main>
    );
  }

  if (error || !estimate) {
    return (
      <main className="flex flex-col min-h-screen">
        <Title pageTitle="견적서 상세" />
        <div className="flex flex-col justify-center items-center h-screen px-5">
          <p className="text-red-500 mb-2">{error || '견적서를 찾을 수 없습니다.'}</p>
          <p className="text-sm text-gray-500">잠시 후 다시 시도하거나 관리자에게 문의하세요.</p>
        </div>
      </main>
    );
  }

  return (
    <main className="flex flex-col min-h-screen">
      {/* 고정된 요소들 - Title */}
      <Title pageTitle="견적서 상세" />
      
      <div className="relative px-5 pt-[164px] pb-10 w-full">
        {/* 견적서 제목 */}
        <h1 className="font-inter font-bold text-[22px] leading-[26px] text-black mb-4">
          {estimate.title}
        </h1>
        
        {/* 견적 상태 표시 (있는 경우) */}
        {estimate.status && (
          <div className="mb-4">
            <span className="inline-block px-3 py-1 rounded-full text-sm bg-green-100 text-green-800">
              {estimate.status}
            </span>
          </div>
        )}
        
        {/* 견적 내용 라벨과 날짜 */}
        <div className="flex justify-between items-center mb-2 mt-[12px]">
          <h2 className="font-inter font-bold text-[17px] leading-[21px] text-black">
            견적내용
          </h2>
          <span className="font-inter font-normal text-[15px] leading-[20px] text-[rgba(0,0,0,0.3)]">
            {estimate.date}
          </span>
        </div>
        
        {/* 견적 내용 표시 박스 - 읽기 전용 */}
        <div className="w-full mx-auto">
          <div
            className="box-border w-full h-[232px] p-[10px] text-[13px] leading-[20px] font-inter font-normal border border-solid border-[rgba(53,51,155,0.9)] rounded-[5px] overflow-auto whitespace-pre-wrap"
          >
            {estimate.content}
          </div>
        </div>
        
        {/* 작성자 정보 - 왼쪽 정렬 */}
        <div className="mt-2">
          <p className="font-inter font-normal text-[10px] leading-[12px] text-black">
            작성자: {estimate.author}
          </p>
        </div>
      </div>
    </main>
  );
}