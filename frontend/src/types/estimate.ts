// src/types/estimate.ts
export interface Estimate {
  id: number;
  title: string;
  content: string;
  createdAt: string;
  memberId: number;
}

export interface EstimateItem {
  id: number;
  title: string;
  date: string;
}