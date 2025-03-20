'use client';

import React, { useEffect, useState } from 'react';
import Title from '@/components/Title';

// 견적서 데이터 타입 정의
interface EstimateDetail {
  id: number;
  title: string;
  content: string;
  date: string;
  author: string;
}

interface EstimateDetailClientProps {
  id: string;
}

export default function EstimateDetailClient({ id }: EstimateDetailClientProps) {
  const [estimateData, setEstimateData] = useState<EstimateDetail | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // 실제로는 API 호출로 대체할 부분
    const fetchEstimateData = async () => {
      setLoading(true);
      
      try {
        // 실제 API 호출 코드로 대체될 부분
        // 예: const response = await fetch(`/api/estimate/${id}`);
        // 예: const data = await response.json();
        
        // 임시로 데이터 가져오는 시간 시뮬레이션
        await new Promise(resolve => setTimeout(resolve, 500));
        
        // 테스트를 위한 임시 데이터 (실제 구현시 API 응답으로 대체)
        const mockData: EstimateDetail = {
          id: 1,
          title: '안녕하세요 이번에 문의드릴게 있어서요',
          content: '우편발송 수량 / 3개\n택배포장 수량 / 5개\n우편발송 할 내용물 종류 / 서류, 카탈로그\n택배포장할 내용물 종류 / 전자제품, 부품',
          date: '2024/02/29 10:30',
          author: 'hellosy76 (박세영)'
        };
        
        setEstimateData(mockData);
      } catch (error) {
        console.error('견적서 데이터 로딩 중 오류 발생:', error);
        setEstimateData(null);
      } finally {
        setLoading(false);
      }
    };

    fetchEstimateData();
  }, [id]);

  if (loading) {
    return (
      <main className="flex flex-col min-h-screen">
        <Title pageTitle="견적서 상세" />
        <div className="flex justify-center items-center h-screen">
          <p>로딩 중...</p>
        </div>
      </main>
    );
  }

  if (!estimateData) {
    return (
      <main className="flex flex-col min-h-screen">
        <Title pageTitle="견적서 상세" />
        <div className="flex justify-center items-center h-screen">
          <p>견적서를 찾을 수 없습니다.</p>
        </div>
      </main>
    );
  }

  return (
    <main className="flex flex-col min-h-screen">
      {/* 고정된 요소들 - Title */}
      <Title pageTitle="견적서 상세" />
      
      <div className="relative px-5 pt-[164px] pb-10 w-full">
        {/* 견적 내용 라벨과 날짜 */}
        <div className="flex justify-between items-center mb-2 mt-[12px]">
          <h2 className="font-inter font-bold text-[17px] leading-[21px] text-black">
            견적내용
          </h2>
          <span className="font-inter font-normal text-[15px] leading-[20px] text-[rgba(0,0,0,0.3)]">
            {estimateData.date}
          </span>
        </div>
        
        {/* 견적 내용 표시 박스 - 읽기 전용 */}
        <div className="w-full mx-auto">
          <div
            className="box-border w-full h-[232px] p-[10px] text-[13px] leading-[20px] font-inter font-normal border border-solid border-[rgba(53,51,155,0.9)] rounded-[5px] overflow-auto whitespace-pre-wrap"
          >
            {estimateData.content}
          </div>
        </div>
        
        {/* 작성자 정보 - 왼쪽 정렬 */}
        <div className="mt-2">
          <p className="font-inter font-normal text-[10px] leading-[12px] text-black">
            작성자: {estimateData.author}
          </p>
        </div>
      </div>
    </main>
  );
}