"use client";

import Image from "next/image";

export default function ServiceCard({
  imgSrc,
  title,
  desc,
  imageSize = 60,
}: {
  imgSrc: string;
  title: string;
  desc: string;
  imageSize?: number;
}) {
  return (
    <div className="bg-white rounded-xl shadow px-6 py-4 h-[180px] flex flex-col justify-between">
      <h3 className="font-extrabold text-lg leading-snug mb-2">{title}</h3>
      <div className="flex justify-between items-center flex-1">
        <p className="text-sm font-semibold leading-snug max-w-[70%]">{desc}</p>
        <Image
          src={imgSrc}
          alt={title}
          width={imageSize}
          height={imageSize}
          className="object-contain ml-4"
        />
      </div>
    </div>
  );
}
