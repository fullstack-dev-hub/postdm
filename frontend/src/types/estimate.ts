// /types/estimate.ts

// 견적서 API 응답 타입 정의
export interface EstimateApiResponse {
  status: number;
  message: string;
  data: EstimateData[];
}

export interface EstimateDetailApiResponse {
  status: number;
  message: string;
  data: EstimateData;
}

// API 응답의 실제 데이터 구조
export interface EstimateData {
  id: number;
  title: string;
  content: string;
  createdAt: string;
  memberId: number;
  // API 문서에 나와있는 필드만 포함
}

// 프론트엔드에서 사용하는 견적서 데이터 타입
export interface Estimate {
  id: number;
  title: string;
  content: string;
  date: string;
  author: string;
  status?: string;
}

// 견적서 생성 요청 타입
export interface CreateEstimateRequest {
  content: string;
}

// 견적서 생성 응답 타입
export interface CreateEstimateResponse {
  status: number;
  message: string;
  data: {
    id: number;
    title: string;
    content: string;
    createdAt: string;
    memberId: number;
  };
}