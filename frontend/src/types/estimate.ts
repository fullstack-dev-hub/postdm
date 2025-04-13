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

export interface EstimateData {
  id: number;
  title: string;
  content: string;
  createdAt: string;
  authorName: string;
  status?: string;
  // 추가적인 API 응답 필드가 있다면 여기에 추가
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