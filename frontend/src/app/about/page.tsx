'use client';

import Header from '@/components/Header';
import AboutNavigation from '@/components/AboutNavigation';

export default function AboutPage() {
  return (
    <main className="flex flex-col min-h-screen">
      {/* 헤더 */}
      <Header />
      
      {/* 네비게이션 - 스크롤해도 고정됨 */}
      <AboutNavigation />
      
      {/* 콘텐츠 영역 - 중앙 정렬 적용 */}
      <div className="pt-[130px] px-10 flex flex-col items-center">
        {/* 회사 소개 섹션 */}
        <section id="company" className="min-h-screen py-10 w-full max-w-4xl">
          <h2 className="text-2xl font-bold mb-6 text-center">회사 소개</h2>
          <p className="text-gray-700 text-center">
            회사 소개 내용이 여기에 들어갑니다. 추후에 실제 내용으로 대체될 예정입니다.
          </p>
          {/* 여기에 추가 내용이 들어갈 예정 */}
        </section>
        
        {/* 서비스 소개 섹션 */}
        <section id="service" className="min-h-screen py-10 w-full max-w-4xl">
          <h2 className="text-2xl font-bold mb-6 text-center">서비스 소개</h2>
          <p className="text-gray-700 text-center">
            서비스 소개 내용이 여기에 들어갑니다. 추후에 실제 내용으로 대체될 예정입니다.
          </p>
          {/* 여기에 추가 내용이 들어갈 예정 */}
        </section>
        
        {/* 위치/교통 섹션 */}
        <section id="location" className="min-h-screen py-10 w-full max-w-4xl">
          <h2 className="text-2xl font-bold mb-6 text-center">위치/교통</h2>
          <p className="text-gray-700 text-center">
            위치 및 교통 정보가 여기에 들어갑니다. 추후에 실제 내용으로 대체될 예정입니다.
          </p>
          {/* 여기에 지도나 교통 정보가 들어갈 예정 */}
        </section>
        
        {/* 고객지원 섹션 */}
        <section id="support" className="min-h-screen py-10 w-full max-w-4xl">
          <h2 className="text-2xl font-bold mb-6 text-center">고객지원</h2>
          <p className="text-gray-700 text-center">
            고객지원 정보가 여기에 들어갑니다. 추후에 실제 내용으로 대체될 예정입니다.
          </p>
          {/* 여기에 고객지원 관련 내용이 들어갈 예정 */}
        </section>
      </div>
    </main>
  );
}