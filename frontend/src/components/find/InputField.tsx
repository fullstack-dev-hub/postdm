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
        className="flex-grow border-b border-gray-600 outline-none py-2 text-lg mt-2"
      />
      {children && <div className="ml-2">{children}</div>}
    </div>
  </div>
);

export default InputField;
