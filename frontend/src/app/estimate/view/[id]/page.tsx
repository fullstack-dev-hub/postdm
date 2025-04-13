// src/app/estimate/view/[id]/page.tsx
import EstimateViewClient from './EstimateViewClient';

export default function EstimateViewPage({ params }: { params: { id: string } }) {
  return <EstimateViewClient id={params.id} />;
}