// app/estimate/write/page.tsx
'use client';

import React, { useState } from 'react';
import { useRouter } from 'next/navigation';
import Title from '@/components/Title';
import { estimateApi } from '@/lib/estimateApi';

export default function EstimateWritePage() {
  const router = useRouter();
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [formData, setFormData] = useState({
    title: '',
    content: ''
  });

  // 입력 필드 변경 처리
  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  // 견적서 제출 처리 함수
  const handleSubmit = async () => {
    // 유효성 검사
    if (!formData.title.trim()) {
      setError('제목을 입력해주세요.');
      return;
    }
    
    if (!formData.content.trim()) {
      setError('견적 내용을 입력해주세요.');
      return;
    }

    try {
      setIsSubmitting(true);
      setError(null);
      
      // API 호출
      await estimateApi.createEstimate({
        title: formData.title,
        content: formData.content
      });
      
      // 제출 성공 시 done 페이지로 이동
      router.push('/estimate/done');
    } catch (err) {
      console.error('견적서 제출 중 오류가 발생했습니다:', err);
      setError('견적서 제출에 실패했습니다. 다시 시도해주세요.');
      setIsSubmitting(false);
    }
  };

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
        
        {/* 견적서 제목 */}
        <div className="ml-[4px] mb-2">
          <h2 className="font-inter font-bold text-[17px] leading-[21px] text-black">
            견적서 제목
          </h2>
        </div>
        
        <div className="w-full mx-auto mb-6">
          <input
            type="text"
            name="title"
            className="box-border w-full p-[10px] text-[13px] leading-[20px] font-inter font-normal border border-solid border-[rgba(53,51,155,0.9)] rounded-[5px] focus:outline-none"
            placeholder="견적서 제목을 입력해주세요"
            value={formData.title}
            onChange={handleChange}
          />
        </div>
        
        {/* 견적 내용 라벨 */}
        <div className="ml-[4px] mb-2">
          <h2 className="font-inter font-bold text-[17px] leading-[21px] text-black">
            견적내용
          </h2>
        </div>
        
        {/* 견적 내용 입력 박스 - 반응형 */}
        <div className="w-full mx-auto">
          <textarea
            name="content"
            className="box-border w-full h-[232px] p-[10px] text-[13px] leading-[20px] font-inter font-normal border border-solid border-[rgba(53,51,155,0.9)] rounded-[5px] resize-none focus:outline-none"
            placeholder={`우편발송 수량 / 
택배포장 수량 우편발송 할 내용물 종류 / 
택배포장할 내용물 종류 /`}
            value={formData.content}
            onChange={handleChange}
            style={{ color: formData.content ? 'black' : 'rgba(0, 0, 0, 0.3)' }}
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