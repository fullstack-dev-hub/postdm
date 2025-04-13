// /hooks/useEstimates.ts
import { useState, useEffect } from 'react';
import axios from '@/utils/axios';
import { useRouter } from 'next/navigation';
import { Estimate, EstimateApiResponse, EstimateData, EstimateDetailApiResponse } from '@/types/estimate';

export type { Estimate };

export const useEstimates = () => {
  const [estimates, setEstimates] = useState<Estimate[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const router = useRouter();

  useEffect(() => {
    const fetchEstimates = async () => {
      // 로그인 상태 확인
      const accessToken = localStorage.getItem('accessToken');
      if (!accessToken) {
        // 로그인되지 않은 경우 로그인 페이지로 리디렉션
        router.push('/login');
        return;
      }

      setLoading(true);
      setError(null);

      try {
        // 실제 API 호출
        const response = await axios.get<EstimateApiResponse>('/api/v1/estimates', {
          headers: {
            Authorization: `Bearer ${accessToken}`
          }
        });

        // API 응답 형식에 맞게 데이터 변환
        const formattedEstimates = response.data.data.map((item: EstimateData) => ({
          id: item.id,
          title: item.title,
          content: item.content,
          date: new Date(item.createdAt).toLocaleString('ko-KR', {
            year: 'numeric',
            month: '2-digit',
            day: '2-digit',
            hour: '2-digit',
            minute: '2-digit'
          }).replace(/\./g, '/'),
          author: item.authorName,
          status: item.status
        }));

        setEstimates(formattedEstimates);
      } catch (err) {
        console.error('견적서 목록을 불러오는데 실패했습니다:', err);
        setError('견적서 목록을 불러오는데 실패했습니다. 다시 시도해주세요.');
        setEstimates([]);
      } finally {
        setLoading(false);
      }
    };

    fetchEstimates();
  }, [router]);

  return { estimates, loading, error };
};

// 단일 견적서 조회를 위한 훅
export const useEstimateDetail = (id: string) => {
  const [estimate, setEstimate] = useState<Estimate | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const router = useRouter();

  useEffect(() => {
    const fetchEstimateDetail = async () => {
      // 로그인 상태 확인
      const accessToken = localStorage.getItem('accessToken');
      if (!accessToken) {
        // 로그인되지 않은 경우 로그인 페이지로 리디렉션
        router.push('/login');
        return;
      }

      setLoading(true);
      setError(null);

      try {
        // 실제 API 호출
        const response = await axios.get<EstimateDetailApiResponse>(`/api/v1/estimates/${id}`, {
          headers: {
            Authorization: `Bearer ${accessToken}`
          }
        });

        // API 응답 형식에 맞게 데이터 변환
        const item = response.data.data;
        const formattedEstimate: Estimate = {
          id: item.id,
          title: item.title,
          content: item.content,
          date: new Date(item.createdAt).toLocaleString('ko-KR', {
            year: 'numeric',
            month: '2-digit',
            day: '2-digit',
            hour: '2-digit',
            minute: '2-digit'
          }).replace(/\./g, '/'),
          author: item.authorName,
          status: item.status
        };

        setEstimate(formattedEstimate);
      } catch (err) {
        console.error(`견적서 ID ${id}를 불러오는데 실패했습니다:`, err);
        setError('견적서를 불러오는데 실패했습니다. 다시 시도해주세요.');
        setEstimate(null);
      } finally {
        setLoading(false);
      }
    };

    fetchEstimateDetail();
  }, [id, router]);

  return { estimate, loading, error };
};