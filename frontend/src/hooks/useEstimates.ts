import { useState, useEffect } from 'react';
import { estimateApi } from '@/lib/estimateApi';
import { EstimateItem } from '@/types/estimate';

// 견적서 목록을 가져오는 커스텀 훅
export const useEstimates = () => {
  const [estimates, setEstimates] = useState<EstimateItem[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error] = useState<Error | null>(null);

  useEffect(() => {
    const fetchEstimates = async () => {
      try {
        setLoading(true);
        
        // TODO: 개발 중에는 API 호출을 시도하지 않고 바로 임시 데이터 사용 (필요에 따라 주석 처리)
        // throw new Error("Using mock data for development");
        
        const data = await estimateApi.getEstimates();
        
        // API 응답 데이터를 컴포넌트에서 사용하는 형태로 변환
        const formattedData: EstimateItem[] = data.map(estimate => ({
          id: estimate.id,
          title: estimate.title,
          date: formatDate(estimate.createdAt)
        }));
        
        // 날짜 기준으로 내림차순 정렬 (최신순)
        const sortedData = [...formattedData].sort((a, b) => {
          const dateA = new Date(a.date.replace(/\./g, '-'));
          const dateB = new Date(b.date.replace(/\./g, '-'));
          return dateB.getTime() - dateA.getTime();
        });
        
        setEstimates(sortedData);
      } catch (err) {
        console.error('견적서 목록을 불러오는 중 오류가 발생했습니다:', err);
        
        // 개발 중 - API 호출 실패 시 임시 데이터 사용
        const mockData: EstimateItem[] = [
          { id: 1, title: '안녕하세요 이번에 문의드릴게 있어서요', date: '2024.02.29' },
          { id: 2, title: '빔프로젝트 설치 견적 문의드립니다', date: '2024.02.28' },
          { id: 3, title: '방문 설치 문의드립니다. 가능할까요?', date: '2024.02.27' },
          { id: 4, title: '사무실 이전 설치 관련 문의드립니다', date: '2024.02.26' },
          { id: 5, title: '스크린 설치 관련 상담 요청드립니다', date: '2024.02.25' },
          { id: 6, title: '프로젝터 브라켓 설치 가능한지 문의해요', date: '2024.02.24' },
        ];
        
        setEstimates(mockData);
        
        // 에러는 설정하지만 UI에 표시하지 않음 (개발 중에는)
        // setError(err instanceof Error ? err : new Error('Unknown error occurred'));
      } finally {
        setLoading(false);
      }
    };

    fetchEstimates();
  }, []);

  // ISO 날짜 문자열을 YYYY.MM.DD 형식으로 변환하는 함수
  const formatDate = (dateString: string): string => {
    const date = new Date(dateString);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}.${month}.${day}`;
  };

  return { estimates, loading, error };
};