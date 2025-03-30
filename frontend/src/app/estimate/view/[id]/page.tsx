import { notFound } from 'next/navigation';
import EstimateViewClient from './EstimateViewClient';

export default async function EstimateViewPage({ params }: { params: Promise<{ id: string }> }) {
  const resolvedParams = await params; // params가 Promise이면 풀어서 사용

  const validIds = ['1', '2', '3'];
  if (!validIds.includes(resolvedParams.id)) {
    notFound();
  }

  return <EstimateViewClient id={resolvedParams.id} />;
}
