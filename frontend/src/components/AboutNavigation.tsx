'use client';

import { useState, useEffect } from 'react';

interface NavItem {
  id: string;
  label: string;
}

const AboutNavigation = () => {
  const [activeSection, setActiveSection] = useState<string>('company');

  const navItems: NavItem[] = [
    { id: 'company', label: '회사 소개' },
    { id: 'service', label: '서비스 소개' },
    { id: 'location', label: '위치/교통' },
    { id: 'support', label: '고객지원' }
  ];

  useEffect(() => {
    const handleScroll = () => {
      const sections = navItems.map(item => document.getElementById(item.id));
      const scrollPosition = window.scrollY + 150; // 약간의 오프셋을 추가

      // 현재 보이는 섹션 확인
      sections.forEach((section, index) => {
        if (section) {
          const sectionTop = section.offsetTop;
          const sectionBottom = sectionTop + section.offsetHeight;

          if (scrollPosition >= sectionTop && scrollPosition < sectionBottom) {
            setActiveSection(navItems[index].id);
          }
        }
      });
    };

    // 스크롤 이벤트 리스너 등록
    window.addEventListener('scroll', handleScroll);
    
    // 초기 로드 시 한 번 호출
    handleScroll();

    // 컴포넌트 언마운트 시 리스너 제거
    return () => {
      window.removeEventListener('scroll', handleScroll);
    };
  }, []);

  const scrollToSection = (id: string) => {
    const section = document.getElementById(id);
    if (section) {
      window.scrollTo({
        top: section.offsetTop - 120, // 네비게이션 높이만큼 오프셋 조정
        behavior: 'smooth'
      });
    }
  };

  return (
    <nav className="fixed top-[95px] left-0 right-0 z-40 bg-white pb-2 flex justify-center">
      <div className="flex flex-row items-center gap-[29px]">
        {navItems.map((item) => (
          <button
            key={item.id}
            onClick={() => scrollToSection(item.id)}
            className={`text-base font-medium pb-1 ${
              activeSection === item.id 
                ? 'text-indigo-700 border-b-2 border-indigo-700' 
                : 'text-gray-400 hover:text-gray-600'
            }`}
          >
            {item.label}
          </button>
        ))}
      </div>
    </nav>
  );
};

export default AboutNavigation;