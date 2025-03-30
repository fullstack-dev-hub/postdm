'use client';

import { useState, useEffect, useCallback, useMemo } from 'react';
import React from 'react';

interface NavItem {
  id: string;
  label: string;
}

const AboutNavigation = () => {
  const [activeSection, setActiveSection] = useState<string>('company');

  // Wrap navItems in useMemo to prevent recreation on each render
  const navItems = useMemo<NavItem[]>(() => [
    { id: 'company', label: '회사 소개' },
    { id: 'service', label: '서비스 소개' },
    { id: 'location', label: '위치/교통' },
    { id: 'support', label: '고객지원' }
  ], []);

  // 현재 보이는 섹션 감지 함수
  const handleScroll = useCallback(() => {
    // 모든 섹션 가져오기
    const sections = navItems.map(item => {
      const section = document.getElementById(item.id);
      if (section) {
        return {
          id: item.id,
          offsetTop: section.offsetTop,
          offsetHeight: section.offsetHeight
        };
      }
      return null;
    }).filter(Boolean);

    // 스크롤 위치
    const scrollPosition = window.scrollY + 150;
    
    // 마지막 섹션부터 확인 (더 낮은 섹션이 우선)
    let activeId = sections[0]?.id || 'company';
    
    for (let i = sections.length - 1; i >= 0; i--) {
      const section = sections[i];
      if (!section) continue;
      
      // 섹션 상단부터 하단까지의 영역 계산
      const sectionTop = section.offsetTop;
      const sectionBottom = sectionTop + section.offsetHeight;
      
      // 현재 스크롤 위치가 섹션 내에 있는지 확인
      if (scrollPosition >= sectionTop && scrollPosition < sectionBottom) {
        activeId = section.id;
        break;
      }
    }
    
    // 스크롤이 페이지 맨 아래에 있을 때 마지막 섹션 활성화
    if (window.innerHeight + window.scrollY >= document.body.offsetHeight - 100) {
      activeId = sections[sections.length - 1]?.id || activeId;
    }
    
    setActiveSection(activeId);
  }, [navItems]);

  useEffect(() => {
    // 스크롤 이벤트 리스너 등록 (스로틀링 적용)
    let ticking = false;
    const scrollListener = () => {
      if (!ticking) {
        window.requestAnimationFrame(() => {
          handleScroll();
          ticking = false;
        });
        ticking = true;
      }
    };

    window.addEventListener('scroll', scrollListener);
    
    // 초기 로드 시 한 번 호출
    handleScroll();

    // 윈도우 크기가 변경될 때도 호출
    window.addEventListener('resize', handleScroll);

    // 컴포넌트 언마운트 시 리스너 제거
    return () => {
      window.removeEventListener('scroll', scrollListener);
      window.removeEventListener('resize', handleScroll);
    };
  }, [handleScroll]);

  const scrollToSection = (id: string) => {
    const section = document.getElementById(id);
    if (section) {
      window.scrollTo({
        top: section.offsetTop - 120, // 네비게이션 높이만큼 오프셋 조정
        behavior: 'smooth'
      });
      setActiveSection(id); // 클릭 즉시 활성 섹션 업데이트
    }
  };

  return (
    <nav className="fixed top-[95px] left-0 right-0 z-40 bg-white pb-2 flex justify-center">
      <div className="flex flex-row items-center">
        {navItems.map((item, index) => (
          <React.Fragment key={item.id}>
            {index > 0 && (
              <div className="h-5 border-l border-gray-300 mx-3.5"></div>
            )}
            <button
              onClick={() => scrollToSection(item.id)}
              className={`text-base font-medium pb-1 ${
                activeSection === item.id 
                  ? 'text-indigo-700 border-b-2 border-indigo-700' 
                  : 'text-gray-400 hover:text-gray-600'
              }`}
            >
              {item.label}
            </button>
          </React.Fragment>
        ))}
      </div>
    </nav>
  );
};

export default AboutNavigation;