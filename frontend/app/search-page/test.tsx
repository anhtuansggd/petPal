"use client";
import React from "react";
import { Input, Button } from "@material-tailwind/react";

export default function Test() {
  return (
    <div className=" w-8/12 mx-auto flex flex-col md:flex-row">
      <div className="p-6">
        <div className="relative shadow-md">
          <img
            src="/shannon.jpg"
            alt="Shannon Payne"
            className="rounded shadow-md"
          />
          <div className="absolute bottom-4 p-2 backdrop-blur-sm text-white left-4">
            {" "}
            <h2 className="text-2xl font-bold">Shannon Payne</h2>
            <p className="text">Verified Sitter</p>
          </div>
        </div>

        <div className="mt-6 backdrop-brightness-75 rounded p-4">
          <h3>About Shannon</h3>
          <p className="text-gray-600">
            Hi there! I'm Shannon, and a huge animal lover. I have two fur
            babies (cats) Lockie & Zoe. I live in Randwick, I drive, and happy
            to drive out of area (Eastern suburbs) if needed.
          </p>
        </div>
        <div className="mt-6">
          <h3>Shannon's experience</h3>
          <ul className="list-disc ml-6 text-gray-600">
            <li>3 years of experience</li>
            <li>Can administer liquid medication</li>
            <li>Can administer pill medication</li>
            <li>Can administer topical medication</li>
          </ul>
        </div>
      </div>
      <div className="w-full md:w-1/2 p-6">
        <div className="bg-white rounded-lg shadow-md p-6">
          <h2 className="text-2xl font-bold mb-4">Pet sitting</h2>
          <p className="text-gray-600 mb-4">Overnight at your home</p>
          <div className="mb-4">
            <Input
              icon={<i className="fa fa-home text-primary-dark-green"></i>}
              type="text"
              variant="outlined"
              color="teal"
              label="Type of pet service"
              className="w-full rounded-lg border border-gray-300 px-3 py-2"
            />
          </div>
          <div className="mb-4">
            <Input
              icon={<i className="fa fa-paw text-primary-dark-green"></i>}
              variant="outlined"
              color="teal"
              type="text"
              label="Type of pet"
              className="w-full rounded-lg px-3 py-2 "
            />
          </div>
          <div className="mb-4 flex">
            <div className="flex items-center mr-4">
              <Input
                // icon={<i className="fa fa-calendar text-primary-dark-green" />}
                type="date"
                color="teal"
                label="Start day"
                className="rounded-lg border border-gray-300 px-3 py-2 focus:outline-none focus:border-blue-500"
              />
            </div>
            <div className="flex items-center">
              <Input
                type="date"
                color="teal"
                label="End day"
                className="rounded-lg border border-gray-300 px-3 py-2 focus:outline-none focus:border-blue-500"
              />
            </div>
          </div>
          <div>
            <Button className="bg-primary-dark-green">Contact Shannon</Button>
          </div>
        </div>
        <div className="mt-6">
          <h2 className="text-xl font-bold mb-4">Services Offered</h2>
          <div className="bg-white rounded-lg shadow-md p-6 mb-4">
            <div className="flex items-center mb-2">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                className="w-6 h-6 inline-block mr-2"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m0 0l-7 7-7-7"
                />
              </svg>
              <h3 className="text-gray-800 font-medium">Sitting</h3>
            </div>
            <p className="text-gray-600">Overnight at your home</p>
            <p className="text-right font-bold text-gray-800">
              From $84 a night
            </p>
          </div>
          <div className="bg-white rounded-lg shadow-md p-6 mb-4">
            <div className="flex items-center mb-2">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                className="w-6 h-6 inline-block mr-2"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M12 8c-1.657 0-3-1.343-3-3s1.343-3 3-3 3 1.343 3 3-1.343 3-3 3z"
                />
              </svg>
              <h3 className="text-gray-800 font-medium">Day care</h3>
            </div>
            <p className="text-gray-600">During working hours</p>
            <p className="text-right font-bold text-gray-800">From $68 a day</p>
          </div>
          <div className="bg-white rounded-lg shadow-md p-6 mb-4">
            <div className="flex items-center mb-2">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                className="w-6 h-6 inline-block mr-2"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"
                />
              </svg>
              <h3 className="text-gray-800 font-medium">House visits</h3>
            </div>
            <p className="text-gray-600">Drop-in and check on your pet</p>
            <p className="text-right font-bold text-gray-800">
              From $45 a visit
            </p>
          </div>
          <div className="bg-white rounded-lg shadow-md p-6 mb-4">
            <div className="flex items-center mb-2">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                className="w-6 h-6 inline-block mr-2"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M9 13h6m-3-3v6m-9 1V7a2 2 0 012-2h5l2 2h5a2 2 0 012 2v12a2 2 0 01-2 2h-5l-2-2h-5a2 2 0 01-2-2z"
                />
              </svg>
              <h3 className="text-gray-800 font-medium">Dog walks</h3>
            </div>
            <p className="text-gray-600">30-45 minutes</p>
            <p className="text-right font-bold text-gray-800">
              From $34 a walk
            </p>
          </div>
          <button className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-full focus:outline-none focus:shadow-outline">
            View Additional Rates
          </button>
          <p className="text-gray-600 mt-2">
            Extra pets, pick up & drop off, peak season
          </p>
        </div>
      </div>
    </div>
  );
}
