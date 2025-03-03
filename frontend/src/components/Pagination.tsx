// components/Pagination.tsx
import React from 'react';
import Image from 'next/image';

interface PaginationProps {
  currentPage: number;
  totalPages: number;
  onPageChange: (page: number) => void;
}

const Pagination = ({ currentPage, totalPages, onPageChange }: PaginationProps) => {
  // 페이지 번호를 표시할 범위 계산
  const getPageNumbers = () => {
    const pages = [];
    
    // 총 페이지가 4개 이하면 모든 페이지 표시
    if (totalPages <= 4) {
      for (let i = 1; i <= totalPages; i++) {
        pages.push(i);
      }
    } else {
      // 현재 페이지 주변 페이지들 표시 (항상 4개만 표시)
      let startPage = Math.max(1, currentPage - 1);
      const endPage = Math.min(totalPages, startPage + 3);
      
      // 끝 페이지가 최대값을 초과하지 않도록 조정
      if (endPage - startPage < 3) {
        startPage = Math.max(1, endPage - 3);
      }
      
      for (let i = startPage; i <= endPage; i++) {
        pages.push(i);
      }
    }
    
    return pages;
  };

  // 페이지 번호 리스트
  const pageNumbers = getPageNumbers();

  // 화살표 버튼이 필요한지 여부
  const needArrows = totalPages > 4;

  return (
    <div className="flex items-center gap-[8px] text-[12px] font-inter">
      {/* 이전 페이지 버튼 (필요한 경우만 표시) */}
      {needArrows && (
        <button 
          className="w-4 h-4 flex items-center justify-center" 
          aria-label="Previous page"
          onClick={() => currentPage > 1 && onPageChange(currentPage - 1)}
          disabled={currentPage === 1}
        >
          <Image
            src="/images/page-left.svg"
            alt="Previous page"
            width={16}
            height={16}
            className={`object-contain ${currentPage === 1 ? 'opacity-50' : ''}`}
          />
        </button>
      )}
      
      {/* 페이지 번호 버튼들 */}
      {pageNumbers.map(number => (
        <button 
          key={number}
          className={`w-[15px] h-[15px] text-center ${
            currentPage === number ? 'font-bold text-[#353395]' : ''
          }`}
          onClick={() => onPageChange(number)}
        >
          {number}
        </button>
      ))}
      
      {/* 다음 페이지 버튼 (필요한 경우만 표시) */}
      {needArrows && (
        <button 
          className="w-4 h-4 flex items-center justify-center" 
          aria-label="Next page"
          onClick={() => currentPage < totalPages && onPageChange(currentPage + 1)}
          disabled={currentPage === totalPages}
        >
          <Image
            src="/images/page-right.svg"
            alt="Next page"
            width={16}
            height={16}
            className={`object-contain ${currentPage === totalPages ? 'opacity-50' : ''}`}
          />
        </button>
      )}
    </div>
  );
};

export default Pagination;