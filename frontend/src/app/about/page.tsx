'use client';

import Header from '@/components/Header';
import AboutNavigation from '@/components/AboutNavigation';
import Image from 'next/image';

export default function AboutPage() {
  return (
    <main className="flex flex-col min-h-screen">
      {/* 헤더 */}
      <Header />
      
      {/* 네비게이션 - 스크롤해도 고정됨 */}
      <AboutNavigation />
      
      {/* 콘텐츠 영역 - 중앙 정렬 적용 */}
      <div className="pt-[130px] px-4 sm:px-6 md:px-10 flex flex-col items-center">
        {/* 회사 소개 섹션 */}
        <section id="company" className="min-h-screen py-10 w-full max-w-4xl relative">
          <h2 className="text-xl sm:text-2xl font-bold mb-6 text-center">회사 소개</h2>
          <div className="space-y-4 mb-10">
            <p className="text-gray-700 text-center font-semibold">
              1996년 10월 1일 창업
            </p>
            <p className="text-center text-[#767676] text-sm sm:text-base">
              이 회사는 우편 DM 발송 대행 서비스를 전문으로 하며, 맞춤형 DM 마케팅을 통해 고객의 성공을 지원합니다.
            </p>
            <p className="text-center text-[#767676] text-sm sm:text-base">
              주요 서비스로는 주소 데이터 가공 및 출력, 우편물 봉입 작업, 우체국 접수 대행, 택배 포장 대행 등이 있습니다.
            </p>
            <p className="text-center text-[#767676] text-sm sm:text-base">
              포스트디엠은 우정사업본부에서 지정한 우편번호 바코드 인증업체로, 자동포장을 통해 신속한 우편물 발송을 약속합니다.
            </p>
          </div>
          
          {/* 구분선 */}
          <div className="w-full sm:w-[280px] h-[1px] bg-[#D9D9D9] mx-auto my-8"></div>
          
          {/* 주요 거래처 제목 */}
          <h3 className="text-center text-sm text-gray-400 mb-6">주요 거래처</h3>
          
          {/* 로고 1행 - 3개 로고 */}
          <div className="flex justify-center gap-0">
            <div className="w-[70px] sm:w-[91px] h-[70px] sm:h-[91px] relative flex items-center justify-center">
              <Image 
                src="/images/about-logo1.svg" 
                alt="서울특별시" 
                width={60} 
                height={71} 
                style={{ objectFit: "contain" }} 
              />
            </div>
            <div className="w-[70px] sm:w-[91px] h-[70px] sm:h-[91px] relative flex items-center justify-center">
              <Image 
                src="/images/about-logo2.svg" 
                alt="국가보훈부" 
                width={73} 
                height={72} 
                style={{ objectFit: "contain" }} 
              />
            </div>
            <div className="w-[70px] sm:w-[91px] h-[70px] sm:h-[91px] relative flex items-center justify-center">
              <Image 
                src="/images/about-logo3.svg" 
                alt="이천시" 
                width={89} 
                height={55} 
                style={{ objectFit: "contain" }} 
              />
            </div>
          </div>
          
          {/* 로고 2행 - 3개 로고 */}
          <div className="flex justify-center gap-0">
            <div className="w-[70px] sm:w-[91px] h-[70px] sm:h-[91px] relative flex items-center justify-center">
              <Image 
                src="/images/about-logo4.svg" 
                alt="KOICA" 
                width={90} 
                height={44} 
                style={{ objectFit: "contain" }} 
              />
            </div>
            <div className="w-[70px] sm:w-[91px] h-[70px] sm:h-[91px] relative flex items-center justify-center">
              <Image 
                src="/images/about-logo5.svg" 
                alt="동아시아재단" 
                width={80} 
                height={18} 
                style={{ objectFit: "contain" }} 
              />
            </div>
            <div className="w-[70px] sm:w-[91px] h-[70px] sm:h-[91px] relative flex items-center justify-center">
              <Image 
                src="/images/about-logo6.svg" 
                alt="국제앰네스티" 
                width={77} 
                height={31} 
                style={{ objectFit: "contain" }} 
              />
            </div>
          </div>
        </section>
        
        {/* 회사 소개 섹션 구분선 */}
        <div className="w-full max-w-4xl h-[1px] bg-[#EAEAEA] my-4"></div>
        
        {/* 서비스 소개 섹션 */}
        <section id="service" className="py-10 w-full max-w-4xl">
          <h2 className="text-xl sm:text-2xl font-bold mb-6 text-center">서비스 소개</h2>
          
          {/* 우편물 발송 대행 서비스 */}
          <div className="mb-16">
            <div className="bg-[#35339B] py-[7px] px-[14px] rounded-[5px] mx-auto w-[127px] h-[26px] flex justify-center items-center mb-6 sm:mb-10">
              <h3 className="w-[99px] h-[12px] text-[10px] leading-[12px] text-center font-semibold text-white tracking-[-0.025em]">우편물 발송 대행 서비스</h3>
            </div>
            
            <div className="flex flex-col items-center">
              <Image 
                src="/images/letter-service.svg" 
                alt="우편물 발송 대행 서비스 프로세스" 
                width={800} 
                height={250} 
                style={{ objectFit: "contain", maxWidth: "100%" }}
              />
            </div>
          </div>
          
          {/* 택배포장 대행 서비스 */}
          <div>
            <div className="bg-[#35339B] py-[7px] px-[14px] rounded-[5px] mx-auto w-[127px] h-[26px] flex justify-center items-center mb-6 sm:mb-10">
              <h3 className="w-[99px] h-[12px] text-[10px] leading-[12px] text-center font-semibold text-white tracking-[-0.025em]">택배포장 대행 서비스</h3>
            </div>
            
            <div className="flex flex-col items-center">
              <Image 
                src="/images/box-service.svg" 
                alt="택배포장 대행 서비스 프로세스" 
                width={800} 
                height={250} 
                style={{ objectFit: "contain", maxWidth: "100%" }}
              />
            </div>
          </div>
        </section>
        
        {/* 서비스 소개 섹션 구분선 */}
        <div className="w-full max-w-4xl h-[1px] bg-[#EAEAEA] my-4"></div>
        
        {/* 위치/교통 섹션 */}
        <section id="location" className="py-10 w-full max-w-4xl">
          <h2 className="text-xl sm:text-2xl font-bold mb-6 text-center">위치/교통</h2>
          
          {/* 카카오맵 (이미지 + 링크) */}
          <div className="w-full mb-8 flex flex-col items-center">
            <div className="w-full max-w-[640px]">
              <div className="w-full relative">
                {/* 지도 이미지와 링크 */}
                <a 
                  href="https://map.kakao.com/?urlX=486538.00000000035&urlY=1122795&itemId=958860664&q=%ED%8F%AC%EC%8A%A4%ED%8A%B8%20%EB%94%94%EC%97%A0&srcid=958860664&map_type=TYPE_MAP&from=roughmap" 
                  target="_blank"
                  rel="noopener noreferrer"
                  className="block border border-[#ccc] rounded-t-lg overflow-hidden"
                >
                  <Image 
                    src="http://t1.daumcdn.net/roughmap/imgmap/f6a2680fe0b903ca9edc8971ad25fc55864ef4ea8bb12f27030dc7102792ba88" 
                    alt="포스트 디엠 지도" 
                    width={638} 
                    height={358} 
                    style={{ width: '100%', height: 'auto' }}
                    unoptimized // 외부 도메인 이미지에 필요
                  />
                </a>
                
                {/* 하단 정보 영역 */}
                <div className="flex justify-between items-center p-2 bg-[#f9f9f9] border border-t-0 border-[rgba(0,0,0,0.1)] rounded-b-lg">
                  <a 
                    href="https://map.kakao.com" 
                    target="_blank"
                    rel="noopener noreferrer"
                  >
                    <Image 
                      src="//t1.daumcdn.net/localimg/localimages/07/2018/pc/common/logo_kakaomap.png" 
                      width={72} 
                      height={16} 
                      alt="카카오맵" 
                      unoptimized
                    />
                  </a>
                  <div className="text-xs flex items-center">
                    <a 
                      target="_blank" 
                      rel="noopener noreferrer"
                      href="https://map.kakao.com/?from=roughmap&srcid=958860664&confirmid=958860664&q=%ED%8F%AC%EC%8A%A4%ED%8A%B8%20%EB%94%94%EC%97%A0&rv=on" 
                      className="text-black hover:underline hidden sm:inline"
                    >
                      로드뷰
                    </a>
                    <span className="mx-2 h-[11px] border-l border-[#d0d0d0] hidden sm:inline"></span>
                    <a 
                      target="_blank" 
                      rel="noopener noreferrer"
                      href="https://map.kakao.com/?from=roughmap&eName=%ED%8F%AC%EC%8A%A4%ED%8A%B8%20%EB%94%94%EC%97%A0&eX=486538.00000000035&eY=1122795" 
                      className="text-black hover:underline"
                    >
                      길찾기
                    </a>
                    <span className="mx-2 h-[11px] border-l border-[#d0d0d0]"></span>
                    <a 
                      target="_blank" 
                      rel="noopener noreferrer"
                      href="https://map.kakao.com?map_type=TYPE_MAP&from=roughmap&srcid=958860664&itemId=958860664&q=%ED%8F%AC%EC%8A%A4%ED%8A%B8%20%EB%94%94%EC%97%A0&urlX=486538.00000000035&urlY=1122795" 
                      className="text-black hover:underline"
                    >
                      지도 크게 보기
                    </a>
                  </div>
                </div>
              </div>
            </div>
          </div>
          
          {/* 주소 및 교통 정보 */}
          <div className="space-y-4">
            <div>
              <h3 className="text-base sm:text-lg font-semibold mb-2">주소</h3>
              <p className="text-gray-700 text-sm sm:text-base">
                서울특별시 마포구 독막로 330, 1층 (우)04169
              </p>
            </div>
            
            <div>
              <h3 className="text-base sm:text-lg font-semibold mb-2">대중교통</h3>
              <ul className="list-disc pl-5 space-y-2 text-gray-700 text-sm sm:text-base">
                <li><span className="font-medium">지하철:</span> 5호선 마포역, 6호선 대흥역, 경의중앙선 광흥창역</li>
                <li><span className="font-medium">버스:</span> 7013A, 7013B, 마포11, 마포12 (용강동주민센터, 신석초등학교 정류장)</li>
              </ul>
            </div>
            
            <div>
              <h3 className="text-base sm:text-lg font-semibold mb-2">주차</h3>
              <p className="text-gray-700 text-sm sm:text-base">
                건물 내 지하주차장 이용 가능 (방문 시 주차권 제공)
              </p>
            </div>
          </div>
        </section>
        
        {/* 위치/교통 섹션 구분선 */}
        <div className="w-full max-w-4xl h-[1px] bg-[#EAEAEA] my-4"></div>
        
        {/* 고객지원 섹션 */}
        <section id="support" className="py-10 w-full max-w-4xl">
          <h2 className="text-xl sm:text-2xl font-bold mb-6 text-center">고객지원</h2>
          
          <div className="flex flex-col items-center justify-center space-y-6">
            <div className="bg-white shadow-md rounded-lg p-4 sm:p-8 w-full max-w-md border border-gray-200">
              <div className="text-center space-y-6">
                <div>
                  <h3 className="text-base sm:text-lg font-semibold mb-2">이메일 문의</h3>
                  <a href="mailto:postdm@hanmail.net" className="text-blue-600 hover:underline flex items-center justify-center text-sm sm:text-base">
                    <svg xmlns="http://www.w3.org/2000/svg" className="h-4 w-4 sm:h-5 sm:w-5 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z" />
                    </svg>
                    postdm@hanmail.net
                  </a>
                </div>
                
                <div className="w-full h-px bg-gray-200"></div>
                
                <div>
                  <h3 className="text-base sm:text-lg font-semibold mb-2">전화 문의</h3>
                  <a href="tel:02-701-7088" className="text-blue-600 hover:underline flex items-center justify-center text-sm sm:text-base">
                    <svg xmlns="http://www.w3.org/2000/svg" className="h-4 w-4 sm:h-5 sm:w-5 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M3 5a2 2 0 012-2h3.28a1 1 0 01.948.684l1.498 4.493a1 1 0 01-.502 1.21l-2.257 1.13a11.042 11.042 0 005.516 5.516l1.13-2.257a1 1 0 011.21-.502l4.493 1.498a1 1 0 01.684.949V19a2 2 0 01-2 2h-1C9.716 21 3 14.284 3 6V5z" />
                    </svg>
                    02-701-7088
                  </a>
                </div>
              </div>
            </div>
            
            <div className="text-gray-600 text-center mt-4 text-xs sm:text-sm">
              <p>업무 시간: 평일 09:00 - 18:00 (점심시간 12:00 - 13:00)</p>
              <p>토요일, 일요일, 공휴일은 휴무입니다.</p>
            </div>
          </div>
        </section>
        
        {/* 푸터 구분선 */}
        <div className="w-full max-w-4xl h-[1px] bg-[#EAEAEA] my-6"></div>
        
        {/* 간단한 푸터 */}
        <footer className="w-full max-w-4xl text-center text-xs text-gray-500 pb-10">
          <p>© 2025 포스트디엠. All rights reserved.</p>
        </footer>
      </div>
    </main>
  );
}