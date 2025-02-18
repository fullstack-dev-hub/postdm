// app/page.tsx
export default function Home() {
  return (
    <main className="pt-[100px]"> {/* 헤더의 높이만큼 패딩 추가 */}
      <div className="flex min-h-screen items-center justify-center bg-blue-500 text-white text-2xl font-bold">
        Tailwind 적용 Test
      </div>
    </main>
  );
}