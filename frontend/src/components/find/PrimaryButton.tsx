"use client";
import { PrimaryButtonProps } from "@/types/components";

const PrimaryButton = ({
  text,
  onClick,
  disabled = false,
}: PrimaryButtonProps) => (
  <button
    className={`w-full font-semibold rounded-full py-2 text-lg mt-6 ${
      disabled
        ? "bg-primary text-white opacity-50 cursor-not-allowed"
        : "bg-primary text-white"
    }`}
    disabled={disabled}
    onClick={onClick}
  >
    {text}
  </button>
);

export default PrimaryButton;
