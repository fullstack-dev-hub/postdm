"use client";

import Image from "next/image";
import ServiceCard from "@/components/ServiceCard";

export default function Home() {
  return (
    <main className="bg-white text-gray-800 pt-[100px]">
      <section className="relative">
        <Image
          src="/images/메인이미지.png"
          alt="메인이미지"
          width={390}
          height={220}
          className="w-full object-cover"
        />
        <div className="absolute inset-x-0 bottom-0 p-4 bg-gradient-to-t from-black/60 to-transparent text-white">
          <p className="text-xl font-semibold">SINCE 1996</p>
          <p className="text-3xl font-bold leading-snug">
            업계 최고의 자부심으로
            <br />
            빠르고 정확한 DM 발송을 약속합니다.
          </p>
        </div>
      </section>

      <section className="bg-[#f0edfa] py-8">
        <div className="flex justify-center mb-6">
          <button className="text-white bg-primary px-4 py-2 rounded-lg text-lg font-bold">
            “포스티디엠” 서비스 소개
          </button>
        </div>
        <div className="grid grid-cols-2 gap-4 px-4">
          <ServiceCard
            imgSrc="/images/1_1 주소데이터.png"
            title="주소 데이터 가공 및 출력"
            desc="고객님의 주소 데이터를 안전하게 관리하고 출력합니다."
            imageSize={50}
          />
          <ServiceCard
            imgSrc="/images/1_2 우편물봉인.png"
            title="우편물 봉인 작업"
            desc="우편물을 철저하게 봉인 작업을 진행합니다."
          />
          <ServiceCard
            imgSrc="/images/1_3 우체국접수.png"
            title="우체국 접수 대행"
            desc="고객님의 물품을 신속하게 접수하여 대행합니다."
            imageSize={70}
          />
          <ServiceCard
            imgSrc="/images/1_4 택배포장.png"
            title="택배 포장 대행"
            desc="고객님의 택배를 안전하게 포장하여 전달드립니다."
          />
        </div>
      </section>

      <section className="bg-white px-4 pb-10">
        <div className="text-center my-6">
          <button className="text-white bg-primary px-4 py-2 rounded-lg text-lg font-bold">
            실제 배송작업 사진
          </button>
        </div>
        <div className="grid grid-cols-3 gap-2">
          {Array.from({ length: 9 }).map((_, idx) => (
            <Image
              key={idx}
              src={`/images/2_${idx + 1}.png`}
              alt={`작업 이미지 ${idx + 1}`}
              width={120}
              height={120}
              className="w-full h-auto rounded-sm"
            />
          ))}
        </div>
      </section>

      <footer className="text-sm text-gray-700 px-4 pb-10">
        <div className="border-t text-md font-semibold pt-4 space-y-1">
          <p className="text-lg font-bold">포스티디엠 실버케어</p>
          <p>대표자 : 박춘화</p>
          <p>대표번호 : 02-701-7088</p>
          <p>서울시 마포구 용강동 149-48 지영빌딩 1층</p>
          <p>통신판매업신고번호: 제2007-3130118-30-2-06622호</p>
        </div>
      </footer>
    </main>
  );
}
