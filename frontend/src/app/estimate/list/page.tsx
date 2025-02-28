// app/estimate/list/page.tsx
'use client';

import { useState, useEffect } from 'react';
import { useRouter } from 'next/navigation';
import Title from '@/components/Title';
import Button from '@/components/Button';
import EstimateList from '@/components/EstimateList';
import Pagination from '@/components/Pagination';

// 견적서 항목의 타입 정의
interface EstimateItem {
  id: number;
  title: string;
  date: string;
}

export default function EstimateListPage() {
  const router = useRouter();
  // 명시적으로 타입 지정
  const [estimates, setEstimates] = useState<EstimateItem[]>([]);
  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 6; // 페이지당 표시할 항목 수

  // 견적서 작성 페이지로 이동하는 함수
  const handleWriteClick = () => {
    router.push('/estimate/write');
  };

  // 실제 구현 시 API 호출 등으로 데이터를 가져옵니다
  useEffect(() => {
    // 임시 데이터 (실제로는 API 호출로 대체)
    const mockData = [
      { id: 1, title: '안녕하세요 이번에 문의드릴게 있어서요', date: '2024.02.29' },
      { id: 2, title: '빔프로젝트 설치 견적 문의드립니다', date: '2024.02.28' },
      { id: 3, title: '방문 설치 문의드립니다. 가능할까요?', date: '2024.02.27' },
      { id: 4, title: '사무실 이전 설치 관련 문의드립니다', date: '2024.02.26' },
      { id: 5, title: '스크린 설치 관련 상담 요청드립니다', date: '2024.02.25' },
      { id: 6, title: '프로젝터 브라켓 설치 가능한지 문의해요', date: '2024.02.24' },
      { id: 7, title: '매립 작업 관련 문의드리고 싶습니다', date: '2024.02.23' },
      { id: 8, title: '설치 후 AS 관련 문의드립니다.', date: '2024.02.22' },
      { id: 9, title: '스피커 시스템 설치 견적 요청드립니다', date: '2024.02.21' },
      { id: 10, title: '홈시어터 설치 관련 전문가 상담 부탁드려요', date: '2024.02.20' },
      { id: 11, title: '천장형 프로젝터 설치 문의드립니다', date: '2024.02.19' },
      { id: 12, title: '회의실 음향 시스템 설치 견적 문의', date: '2024.02.18' },
      { id: 13, title: '강의실 영상 장비 설치 관련 문의드립니다', date: '2024.02.17' },
      { id: 14, title: '방문 견적 가능한지 문의드립니다', date: '2024.02.16' },
      { id: 15, title: '빔프로젝터 스크린 교체 견적 부탁드립니다', date: '2024.02.15' },
    ];
    
    // 날짜 기준으로 내림차순 정렬 (최신순)
    // 참고: 실제 API에서 가져올 때는 이미 정렬되어 있을 수 있음
    const sortedData = [...mockData].sort((a, b) => {
      const dateA = new Date(a.date.replace(/\./g, '-'));
      const dateB = new Date(b.date.replace(/\./g, '-'));
      return dateB.getTime() - dateA.getTime();
    });
    
    setEstimates(sortedData);
  }, []);

  // 현재 페이지에 표시할 항목들
  const currentItems = estimates.slice(
    (currentPage - 1) * itemsPerPage,
    currentPage * itemsPerPage
  );

  // 총 페이지 수 계산
  const totalPages = Math.ceil(estimates.length / itemsPerPage);

  return (
    <main className="flex flex-col min-h-screen">
      {/* 고정된 요소들 - Title */}
      <Title pageTitle="나의 견적서" />
      
      {/* 버튼 영역 - Title 아래에 고정 */}
      <div className="fixed top-[164px] w-full px-5 z-30 bg-white pb-4">
        <div className="flex justify-end">
          <Button onClick={handleWriteClick}>견적서 작성하기</Button>
        </div>
      </div>
      
      {/* 콘텐츠 영역 - 버튼 아래부터 시작 */}
      <div className="px-5 pt-[220px] pb-10">
        {/* List Container */}
        <EstimateList items={currentItems} />
        
        {/* Pagination - 항목이 1개 페이지(6개)를 초과할 때만 표시 */}
        {totalPages > 1 && (
          <div className="flex justify-center mt-8">
            <Pagination 
              currentPage={currentPage}
              totalPages={totalPages}
              onPageChange={setCurrentPage}
            />
          </div>
        )}
      </div>
    </main>
  );
}