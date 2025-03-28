// src/app/estimate/view/[id]/EstimateViewClient.tsx
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

export default function EstimateViewClient({ id }: { id: string }) {
  const [estimateData, setEstimateData] = useState<EstimateDetail | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // 실제로는 API 호출로 대체할 부분
    // 임시 데이터를 사용하여 해당 ID의 견적서 데이터를 가져옴
    const fetchEstimateData = () => {
      setLoading(true);
      
      // 임시 데이터 (실제로는 API 호출로 대체)
      const mockData = [
        {
          id: 1,
          title: '안녕하세요 이번에 문의드릴게 있어서요',
          content: '우편발송 수량 / 3개\n택배포장 수량 / 5개\n우편발송 할 내용물 종류 / 서류, 카탈로그\n택배포장할 내용물 종류 / 전자제품, 부품',
          date: '2024/02/29 10:30',
          author: 'hellosy76 (박세영)'
        },
        {
          id: 2,
          title: '빔프로젝트 설치 견적 문의드립니다',
          content: '우편발송 수량 / 2개\n택배포장 수량 / 4개\n우편발송 할 내용물 종류 / 문서\n택배포장할 내용물 종류 / 빔프로젝터, 액세서리',
          date: '2024/02/28 15:20',
          author: 'projector123 (김영수)'
        },
        {
          id: 3,
          title: '방문 설치 문의드립니다. 가능할까요?',
          content: '방문 설치 가능 여부 확인 부탁드립니다.\n설치 요청 장소: 서울시 강남구\n설치 요청 일자: 2024년 3월 10일\n설치 물품: 스크린, 스피커 시스템',
          date: '2024/02/27 09:15',
          author: 'user567 (이지원)'
        }
      ];
      
      // 파라미터로 받은 ID와 일치하는 견적서 찾기
      const foundEstimate = mockData.find(item => item.id === parseInt(id));
      
      setEstimateData(foundEstimate || null);
      setLoading(false);
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