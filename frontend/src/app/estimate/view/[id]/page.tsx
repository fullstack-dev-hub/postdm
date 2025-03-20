// src/app/estimate/view/[id]/page.tsx
import React from 'react';
import EstimateDetailClient from './EstimateDetailClient';

// ESLint 규칙을 비활성화하여 any 타입 허용
// eslint-disable-next-line @typescript-eslint/no-explicit-any
export default function EstimateViewPage(props: any) {
  return <EstimateDetailClient id={props.params.id} />;
}

// 빌드 타임 옵션 추가
export const dynamic = 'force-dynamic';