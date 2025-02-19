import Title from '@/components/Title';

export default function Home() {
  return (
    <main> 
      <Title pageTitle="메인페이지" />
      <div className="flex min-h-screen items-center justify-center bg-blue-500 text-white text-2xl font-bold">
        Tailwind 적용 Test
      </div>
    </main>
  );
}