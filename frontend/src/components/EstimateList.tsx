// components/EstimateList.tsx
import React from 'react';
import Link from 'next/link';

interface EstimateItem {
  title: string;
  date: string;
  id: number;
}

interface EstimateListProps {
  items?: EstimateItem[];
}

const EstimateList = ({ items }: EstimateListProps) => {
  // items prop이 없으면 기본 임시 데이터 사용
  const estimates = items || [
    { id: 1, title: '안녕하세요 이번에 문의드릴게 있어서요', date: '2024.02.15' },
    { id: 2, title: '빔프로젝트 설치 견적 문의드립니다', date: '2024.02.16' },
    { id: 3, title: '방문 설치 문의드립니다.', date: '2024.02.17' },
  ];

  // 제목 텍스트 포맷 함수: 10글자 이상이면 10글자 이후로 *** 표시
  const formatTitle = (title: string) => {
    if (title.length > 10) {
      return `${title.substring(0, 10)}***`;
    }
    return title;
  };

  return (
    <div className="flex flex-col gap-[30px]">
      {estimates.length === 0 ? (
        <div className="flex flex-col items-center justify-center py-16 text-gray-500">
          <p>아직 작성된 견적서가 없습니다.</p>
          <p>상단의 견적서 작성하기 버튼을 눌러 새 견적서를 작성해보세요.</p>
        </div>
      ) : (
        estimates.map((estimate) => (
          <Link href={`/estimate/${estimate.id}`} key={estimate.id}>
            <div className="relative cursor-pointer">
              <div className="flex justify-between items-center px-[30px]">
                <span className="font-inter font-normal text-[15px] leading-[20px] text-black">
                  {formatTitle(estimate.title)}
                </span>
                <span className="font-inter font-normal text-[15px] leading-[20px] text-black">
                  {estimate.date}
                </span>
              </div>
              <div className="absolute w-full h-[0.5px] left-0 top-[31px] bg-black" />
            </div>
          </Link>
        ))
      )}
    </div>
  );
}

export default EstimateList;