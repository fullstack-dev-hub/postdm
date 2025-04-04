import axios from "axios";

const instance = axios.create({
  baseURL: "http://localhost:8080", // 백엔드 서버 주소
  headers: {
    "Content-Type": "application/json",
  },
});

// accessToken 인터셉터
instance.interceptors.request.use(
  (config) => {
    if (typeof window !== "undefined") {
      const token = localStorage.getItem("accessToken");
      if (token) {
        config.headers.Authorization = `Bearer ${token}`;
      }
    }
    return config;
  },
  (error) => Promise.reject(error)
);

export default instance;
