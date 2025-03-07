// src/lib/api.ts
import axios from 'axios';

// API 기본 설정
const API_BASE_URL = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080';

const api = axios.create({
  baseURL: `${API_BASE_URL}/api/v1`,
  headers: {
    'Content-Type': 'application/json',
  },
});

// 요청 인터셉터 설정 (JWT 토큰 등을 여기서 처리할 수 있음)
api.interceptors.request.use(
  (config) => {
    // 로컬 스토리지에서 토큰 가져오기
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// 응답 인터셉터 설정 (에러 처리, 리프레시 토큰 등을 여기서 처리할 수 있음)
api.interceptors.response.use(
  (response) => {
    return response;
  },
  async (error) => {
    // 401 에러(인증 실패) 처리 - 토큰 만료 등
    if (error.response && error.response.status === 401) {
      // 토큰 갱신 로직이나 로그아웃 처리 등을 여기서 구현
      // localStorage.removeItem('token');
      // window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

export default api;