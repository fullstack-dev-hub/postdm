// app/estimate/list/page.tsx
'use client';

import { useState, useEffect } from 'react';
import { useRouter } from 'next/navigation';
import Title from '@/components/Title';
import Button from '@/components/Button';
import EstimateList from '@/components/EstimateList';
import Pagination from '@/components/Pagination';
import { useEstimates } from '@/hooks/useEstimates';

export default function EstimateListPage() {
  const router = useRouter();
  const { estimates, loading, error } = useEstimates();
  const [currentPage, setCurrentPage] = useState(1);
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const itemsPerPage = 6; // 페이지당 표시할 항목 수

  useEffect(() => {
    // 컴포넌트 마운트 시 로그인 상태 확인
    const accessToken = localStorage.getItem('accessToken');
    setIsLoggedIn(!!accessToken);

    if (!accessToken) {
      // 로그인되지 않은 경우 로그인 페이지로 리디렉션
      router.push('/login');
    }
    // 로그인 상태 변경 감지
    window.addEventListener("auth-state-changed", () => {
      const token = localStorage.getItem('accessToken');
      setIsLoggedIn(!!token);
    });
    
    return () => {
      window.removeEventListener("auth-state-changed", () => {});
    };
  }, [router]);

  // 견적서 작성 페이지로 이동하는 함수
  const handleWriteClick = () => {
    router.push('/estimate/write');
  };

  // 현재 페이지에 표시할 항목들
  const currentItems = estimates.slice(
    (currentPage - 1) * itemsPerPage,
    currentPage * itemsPerPage
  );

  // 총 페이지 수 계산
  const totalPages = Math.ceil(estimates.length / itemsPerPage);

  // 로그인 상태가 아니면 로딩 상태로 표시
  if (!isLoggedIn) {
    return (
      <main className="flex flex-col min-h-screen">
        <Title pageTitle="나의 견적서" />
        <div className="flex justify-center items-center h-screen">
          <div className="animate-spin rounded-full h-8 w-8 border-t-2 border-b-2 border-gray-900"></div>
        </div>
      </main>
    );
  }

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
        {/* 로딩 상태 표시 */}
        {loading && (
          <div className="flex justify-center items-center py-10">
            <div className="animate-spin rounded-full h-8 w-8 border-t-2 border-b-2 border-gray-900"></div>
          </div>
        )}
        
        {/* 에러 메시지 표시 */}
        {error && (
          <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mt-4 mb-4">
            <p>{error}</p>
          </div>
        )}
        
        {/* 데이터가 없을 경우 메시지 표시 */}
        {!loading && !error && estimates.length === 0 && (
          <div className="text-center py-10 text-gray-500">
            <p>작성된 견적서가 없습니다.</p>
            <p className="mt-2">견적서 작성하기 버튼을 눌러 첫 견적서를 작성해보세요.</p>
          </div>
        )}
        
        {/* 데이터가 있는 경우 목록 표시 */}
        {!loading && !error && estimates.length > 0 && (
          <>
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
          </>
        )}
      </div>
    </main>
  );
}