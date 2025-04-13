// app/estimate/write/page.tsx
'use client';

import React, { useState, useEffect } from 'react';
import { useRouter } from 'next/navigation';
import Title from '@/components/Title';
import { estimateApi } from '@/lib/estimateApi';

export default function EstimateWritePage() {
  const router = useRouter();
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [content, setContent] = useState('');
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  // 로그인 상태 확인
  useEffect(() => {
    const accessToken = localStorage.getItem('accessToken');
    setIsLoggedIn(!!accessToken);

    if (!accessToken) {
      router.push('/login');
    }
  }, [router]);

  // 입력 필드 변경 처리
  const handleChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setContent(e.target.value);
  };

  // 견적서 제출 처리 함수
  const handleSubmit = async () => {
    // 유효성 검사
    if (!content.trim()) {
      setError('견적 내용을 입력해주세요.');
      return;
    }

    try {
      setIsSubmitting(true);
      setError(null);
      
      // API 호출 (API 명세에 따라 content만 전송)
      const result = await estimateApi.createEstimate({
        content: content
      });
      
      if (result) {
        // 제출 성공 시 done 페이지로 이동
        router.push('/estimate/done');
      } else {
        setError('견적서 제출에 실패했습니다. 다시 시도해주세요.');
        setIsSubmitting(false);
      }
    } catch (err) {
      console.error('견적서 제출 중 오류가 발생했습니다:', err);
      setError('견적서 제출에 실패했습니다. 다시 시도해주세요.');
      setIsSubmitting(false);
    }
  };

  // 로그인 상태가 아니면 로딩 상태로 표시
  if (!isLoggedIn) {
    return (
      <main className="flex flex-col min-h-screen">
        <Title pageTitle="견적서 작성" />
        <div className="flex justify-center items-center h-screen">
          <div className="animate-spin rounded-full h-8 w-8 border-t-2 border-b-2 border-gray-900"></div>
        </div>
      </main>
    );
  }

  return (
    <main className="flex flex-col min-h-screen">
      {/* 고정된 요소들 - Title */}
      <Title pageTitle="견적서 작성" />
      
      <div className="relative px-5 pt-[164px] pb-10 w-full">
        {/* 오류 메시지 표시 */}
        {error && (
          <div className="mb-4 p-3 bg-red-50 text-red-600 rounded-md text-sm">
            {error}
          </div>
        )}
        
        {/* 견적 내용 라벨 */}
        <div className="ml-[4px] mb-2">
          <h2 className="font-inter font-bold text-[17px] leading-[21px] text-black">
            견적내용
          </h2>
          <p className="text-xs text-gray-500 mt-1">
            작성하신 내용을 바탕으로 견적서 제목이 자동으로 생성됩니다.
          </p>
        </div>
        
        {/* 견적 내용 입력 박스 - 반응형 */}
        <div className="w-full mx-auto">
          <textarea
            name="content"
            className="box-border w-full h-[232px] p-[10px] text-[13px] leading-[20px] font-inter font-normal border border-solid border-[rgba(53,51,155,0.9)] rounded-[5px] resize-none focus:outline-none"
            placeholder={`우편발송 수량 / 
택배포장 수량 / 
우편발송 할 내용물 종류 / 
택배포장할 내용물 종류 /`}
            value={content}
            onChange={handleChange}
            style={{ color: content ? 'black' : 'rgba(0, 0, 0, 0.3)' }}
          />
        </div>
        
        {/* 제출 버튼 - 오른쪽 정렬 및 크기 조정 */}
        <div className="flex justify-end mt-5">
          <button 
            onClick={handleSubmit}
            disabled={isSubmitting}
            className={`flex justify-center items-center px-5 py-[10px] w-[121px] h-[38px] rounded-[20px] text-white text-sm font-bold ${
              isSubmitting ? 'bg-gray-400 cursor-not-allowed' : 'bg-[#353395] hover:bg-[#2a2875]'
            }`}
          >
            {isSubmitting ? '제출 중...' : '견적서 보내기'}
          </button>
        </div>
      </div>
    </main>
  );
}