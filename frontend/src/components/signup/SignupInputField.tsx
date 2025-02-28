"use client";

import { CheckCircleIcon, XCircleIcon } from "@heroicons/react/24/solid";

interface SignupInputFieldProps {
  label: string;
  type: string;
  placeholder: string;
  value?: string;
  onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void;
  buttonText?: string;
  onButtonClick?: () => void;
  error?: boolean;
  success?: boolean;
  helperText?: string;
  errorText?: string;
}

const SignupInputField = ({
  label,
  type,
  placeholder,
  value,
  onChange,
  buttonText,
  onButtonClick,
  error,
  success,
  errorText,
}: SignupInputFieldProps) => {
  return (
    <div className="w-full mt-4">
      <div className="flex items-center space-x-2">
        <label className="text-lg font-medium text-gray-700">{label}</label>
      </div>

      <div className="w-full flex items-center border-b py-2 space-x-2">
        <input
          type={type}
          placeholder={placeholder}
          value={value}
          onChange={onChange}
          className={`flex-grow outline-none ${
            error ? "border-red-500 text-red-500" : "border-gray-600"
          }`}
        />

        {success && <CheckCircleIcon className="w-6 h-6 text-green-500" />}
        {error && <XCircleIcon className="w-6 h-6 text-red-500" />}

        {buttonText && onButtonClick && (
          <button
            className="ml-2 px-4 py-2 bg-primary text-white rounded-md text-sm font-bold"
            onClick={onButtonClick}
          >
            {buttonText}
          </button>
        )}
      </div>

      {error && errorText && (
        <p className="text-red-500 text-sm mt-1">{errorText}</p>
      )}
    </div>
  );
};

export default SignupInputField;
