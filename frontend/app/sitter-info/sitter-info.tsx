"use client";
import React, { useState } from "react";
import { Input, Button } from "@material-tailwind/react";
import Link from "next/link";
import NavBar from "../components/nav-bar";
import Footer from "../components/footer";
import { useRouter } from "next/navigation";

export default function SitterInfo() {
  const router = useRouter();
  const [serviceType, setServiceType] = useState("");
  const [petType, setPetType] = useState("");
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");
  const handleContract = async (e: React.FormEvent<HTMLFormElement>) => {
    // const [isLoading, setIsLoading] = useState(false);

    e.preventDefault();

    const Contract = {
      serviceType,
      petType,
      startDate,
      endDate,
    };
    localStorage.setItem("contractData", JSON.stringify(Contract));
  };

  return (
    <div>
      <NavBar />

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
            <form onSubmit={handleContract}>
              <div className="mb-4">
                <Input
                  onChange={(e) => setServiceType(e.target.value)}
                  className="rounded-lg px-3 py-2"
                  variant="outlined"
                  color="teal"
                  label="Type of service"
                  placeholder="Pet Sitting or Pet Hosting"
                ></Input>
              </div>
              <div className="mb-4">
                <Input
                  onChange={(e) => setPetType(e.target.value)}
                  className="rounded-lg px-3 py-2"
                  variant="outlined"
                  color="teal"
                  label="Types of pet"
                  placeholder="Dog, Car or Guinea Pig"
                ></Input>
              </div>
              <div className="mb-4 flex">
                <div className="flex items-center mr-4">
                  <Input
                    onChange={(e) => setStartDate(e.target.value)}
                    type="date"
                    color="teal"
                    label="Start day"
                    className="rounded-lg border border-gray-300 px-3 py-2 focus:outline-none focus:border-blue-500"
                  />
                </div>
                <div className="flex items-center">
                  <Input
                    onChange={(e) => setEndDate(e.target.value)}
                    type="date"
                    color="teal"
                    label="End day"
                    className="rounded-lg border border-gray-300 px-3 py-2 focus:outline-none focus:border-blue-500"
                  />
                </div>
              </div>
              <div>
                <Link href={"/chat-page"}>
                  <Button type="submit" className="bg-primary-dark-green">
                    Contact Sitter
                  </Button>
                </Link>
              </div>
            </form>
          </div>
          <div className="mt-6">
            <h2 className="text-xl font-bold mb-4">Services Offered</h2>
            <div className="bg-white rounded-lg shadow-md p-4 mb-4">
              <div className="flex items-center mb-2">
                <h3 className="text-primary-dark-green text-2xl font-bold">
                  Sitting
                </h3>
              </div>
              <p className="text-gray-600">Overnight at your home</p>
              <p className="font-bold text-gray-800">From $84 a night</p>
            </div>
            <div className="bg-white rounded-lg shadow-md p-4 mb-4">
              <div className="flex items-center mb-2">
                <h3 className="text-primary-dark-green text-2xl font-bold">
                  Day care
                </h3>
              </div>
              <p className="text-gray-600">During working hours</p>
              <p className="font-bold text-gray-800">From $68 a day</p>
            </div>
          </div>
        </div>
      </div>
      <Footer />
    </div>
  );
}
