// /hooks/useEstimates.ts
import { useState, useEffect } from 'react';
import { useRouter } from 'next/navigation';
import { Estimate } from '@/types/estimate';
import { estimateApi } from '@/lib/estimateApi';

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
        // API를 통해 견적서 목록 가져오기
        const data = await estimateApi.getEstimates();
        
        if (data.length === 0) {
          // 데이터가 없는 경우 (API 호출은 성공했지만 결과가 없는 경우)
          setEstimates([]);
        } else {
          setEstimates(data);
        }
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
        // API를 통해 견적서 상세 정보 가져오기
        const data = await estimateApi.getEstimateById(id);
        
        if (!data) {
          setError('견적서를 찾을 수 없습니다.');
          setEstimate(null);
        } else {
          setEstimate(data);
        }
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