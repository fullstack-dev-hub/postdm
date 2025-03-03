"use client";

import { useState } from "react";
import { privacyPolicy } from "@/data/terms";

const PrivacyAgreement = () => {
  const [isPrivacyChecked, setIsPrivacyChecked] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);

  const handlePrivacyCheck = () => {
    setIsPrivacyChecked((prev) => !prev);
  };

  const toggleModal = () => {
    setIsModalOpen((prev) => !prev);
  };

  return (
    <div className="mt-4">
      <div className="flex items-center">
        <input
          type="checkbox"
          id="privacyCheck"
          checked={isPrivacyChecked}
          onChange={handlePrivacyCheck}
          className="w-5 h-5 text-primary border-gray-300 rounded cursor-pointer"
        />
        <label htmlFor="privacyCheck" className="ml-2 text-gray-700">
          개인정보 수집 및 이용 (필수)
        </label>
        <button
          className="ml-auto text-sm text-gray-500 underline"
          onClick={toggleModal}
        >
          더보기
        </button>
      </div>

      {isModalOpen && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
          <div className="bg-white w-4/5 max-w-md rounded-lg overflow-hidden">
            <div className="bg-primary text-white flex items-center justify-between p-4">
              <h3 className="text-lg font-semibold">
                개인정보취급방침 전체보기
              </h3>
              <button onClick={toggleModal} className="text-white text-3xl">
                ✖
              </button>
            </div>

            <div className="p-6 text-gray-700 max-h-80 overflow-auto">
              {privacyPolicy.map((text, index) => (
                <p key={index} className="mb-4">
                  {text}
                </p>
              ))}
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default PrivacyAgreement;
