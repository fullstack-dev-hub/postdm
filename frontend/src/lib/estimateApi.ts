// src/lib/estimateApi.ts
import api from './api';
import { Estimate } from '@/types/estimate';

// 견적서 관련 API 함수들
export const estimateApi = {
  // 견적서 목록 조회
  getEstimates: async (): Promise<Estimate[]> => {
    const response = await api.get<Estimate[]>('/estimates');
    return response.data;
  },

  // 견적서 상세 조회
  getEstimateById: async (id: number): Promise<Estimate> => {
    const response = await api.get<Estimate>(`/estimates/${id}`);
    return response.data;
  },

  // 견적서 생성
  createEstimate: async (estimateData: Omit<Estimate, 'id' | 'createdAt' | 'memberId'>): Promise<Estimate> => {
    const response = await api.post<Estimate>('/estimates', estimateData);
    return response.data;
  },

//   // 견적서 수정
//   updateEstimate: async (id: number, estimateData: Partial<Estimate>): Promise<Estimate> => {
//     const response = await api.put<Estimate>(`/estimates/${id}`, estimateData);
//     return response.data;
//   },

//   // 견적서 삭제
//   deleteEstimate: async (id: number): Promise<void> => {
//     await api.delete(`/estimates/${id}`);
//   }
};