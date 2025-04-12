"use client";

import Image from "next/image";

export default function ServiceCard({
  imgSrc,
  title,
  desc,
  imageSize = 70,
}: {
  imgSrc: string;
  title: string;
  desc: string;
  imageSize?: number;
}) {
  return (
    <div className="bg-white rounded-xl shadow px-6 py-4 flex justify-between items-center h-[160px]">
      <div className="text-left max-w-[60%]">
        <h3 className="font-extrabold text-lg leading-snug mb-1">{title}</h3>
        <p className="text-sm text-gray-600 font-medium leading-snug">{desc}</p>
      </div>
      <Image
        src={imgSrc}
        alt={title}
        width={imageSize}
        height={imageSize}
        className={`ml-4 object-contain`}
      />
    </div>
  );
}
