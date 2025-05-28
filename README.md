# 📨PostDM 웹모바일 버전

### **1. 개요**
- **프로젝트 배경**: PC 기반의 포스트디엠 서비스를 스마트폰에서도 접근 가능하도록 웹모바일 버전으로 확장 및 UI/UX 개선 필요
- **목표**: 웹모바일 버전을 통해 스마트폰 환경에서 손쉬운 접근성과 사용 편의성 제공
- **개발 기간**: 2025.02.10 ~
- **핵심 기능**
    - 견적서 작성 및 나의 견적서 목록 확인
    - 관리자 페이지에서 모든 사용자의 견적서 확인 및 관리 기능

### 2. 팀원 및 역할
#### 📌Frontend
- [hardlife0](https://github.com/hardlife0)
- [Daeun Lee](https://github.com/LEE-DA-EUN)

#### 📌Backend
- [Jiyoung Oh](https://github.com/rimeir)
- [SungMin Lee](https://github.com/Etwashoeren)

### **3. 기술 스택**
- **Frontend**: React (Vite), Next.js (ssr / csr), Typescript, Zustand, TailwindCSS
- **Backend**: Spring Boot, Java 17, Spring Security, JPA, JWT
- **DB**: MySQL
- **Infra/DevOps**: AWS EC2, ECR, IAM, GitHub Actions (CI/CD), Docker, nginx

### **4. 주요 기능**
- 사용자 회원가입 및 로그인 (JWT 기반 인증)
- 견적서 생성, 조회 기능
- 일반 사용자는 자신의 견적서 목록 조회
- 관리자 전용 전체 사용자 견적서 목록 확인 및 견적서 확인 표시 기능
- **보안**
    - JWT 기반 인증
    - 민감 정보 암호화 저장
    - HTTPS 통신 적용
