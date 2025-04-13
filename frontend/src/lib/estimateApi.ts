// src/lib/estimateApi.ts
import api from './api';
import { 
  Estimate, 
  EstimateApiResponse, 
  EstimateDetailApiResponse, 
  CreateEstimateRequest, 
  CreateEstimateResponse 
} from '@/types/estimate';

// 견적서 관련 API 함수들
export const estimateApi = {
  // 견적서 목록 조회
  getEstimates: async (): Promise<Estimate[]> => {
    try {
      const response = await api.get<EstimateApiResponse>('/estimates');
      
      // API 응답 형식에 맞게 데이터 변환
      return response.data.data.map(item => ({
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
        author: `작성자 ID: ${item.memberId}`, // memberId를 기반으로 작성자 정보 표시
        status: '작성완료' // 상태 정보가 없으므로 기본값 설정
      }));
    } catch (error) {
      console.error('견적서 목록을 불러오는데 실패했습니다:', error);
      return [];
    }
  },

  // 견적서 상세 조회
  getEstimateById: async (id: number | string): Promise<Estimate | null> => {
    try {
      const response = await api.get<EstimateDetailApiResponse>(`/estimates/${id}`);
      
      const item = response.data.data;
      // API 응답 형식에 맞게 데이터 변환
      return {
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
        author: `작성자 ID: ${item.memberId}`, // memberId를 기반으로 작성자 정보 표시
        status: '작성완료' // 상태 정보가 없으므로 기본값 설정
      };
    } catch (error) {
      console.error(`견적서 ID ${id}를 불러오는데 실패했습니다:`, error);
      return null;
    }
  },

  // 견적서 생성 (API 명세에 맞게 content만 전송)
  createEstimate: async (data: CreateEstimateRequest): Promise<CreateEstimateResponse | null> => {
    try {
      const response = await api.post<CreateEstimateResponse>('/estimates', data);
      return response.data;
    } catch (error) {
      console.error('견적서 생성에 실패했습니다:', error);
      return null;
    }
  }

  // 추후 필요시 견적서 수정/삭제 API도 구현 가능
};