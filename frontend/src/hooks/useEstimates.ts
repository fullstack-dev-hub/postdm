// src/hooks/useEstimates.ts
import { useState, useEffect } from 'react';
import { estimateApi } from '@/lib/estimateApi';
import {EstimateItem } from '@/types/estimate';

// 견적서 목록을 가져오는 커스텀 훅
export const useEstimates = () => {
  const [estimates, setEstimates] = useState<EstimateItem[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<Error | null>(null);

  useEffect(() => {
    const fetchEstimates = async () => {
      try {
        setLoading(true);
        const data = await estimateApi.getEstimates();
        
        // API 응답 데이터를 컴포넌트에서 사용하는 형태로 변환
        const formattedData: EstimateItem[] = data.map(estimate => ({
          id: estimate.id,
          title: estimate.title,
          // createdAt 문자열을 Date 객체로 변환 후 YYYY.MM.DD 형식으로 포맷팅
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
        setError(err instanceof Error ? err : new Error('Unknown error occurred'));
        console.error('견적서 목록을 불러오는 중 오류가 발생했습니다:', err);
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