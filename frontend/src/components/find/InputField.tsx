"use client";

import { InputFieldProps } from "@/types/components";

const InputField = ({
  label,
  type,
  placeholder,
  value,
  onChange,
  children,
  helperText,
  disabled = false,
}: InputFieldProps) => (
  <div className="w-full mt-4">
    <div className="flex items-center space-x-2">
      <label className="text-lg font-medium text-gray-700">{label}</label>
      {helperText && (
        <span className="text-sm text-gray-500 whitespace-nowrap">
          {helperText}
        </span>
      )}
    </div>
    <div className="w-full flex items-center">
      <input
        type={type}
        placeholder={placeholder}
        value={value}
        onChange={onChange}
        disabled={disabled}
        className={`flex-grow border-b outline-none py-2 text-lg mt-2
          ${
            disabled
              ? "bg-gray-100 text-gray-500 cursor-not-allowed"
              : "border-gray-600"
          }
        `}
      />
      {children && <div className="ml-2">{children}</div>}
    </div>
  </div>
);

export default InputField;
