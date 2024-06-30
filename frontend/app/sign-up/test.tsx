"use client";
import React from "react";
import { Input, Button } from "@material-tailwind/react";
import Image from "next/image";
import Link from "next/link";

export default function Test() {
  return (
    <div className="w-8/12 mx-auto mt-12">
      <div className="flex h-max flex-col md:flex-row items-center justify-between">
        <div className="md:w-1/2">
          <Image
            src="/dos-and-donts-of-pet-sitting.png"
            alt="Dogs being walked"
            width="0"
            height="0"
            sizes="100vw"
            className="w-full h-auto rounded"
          />
        </div>
        <div className="md:w-1/2 mt-4 md:mt-0 px-4">
          <div className="bg-white shadow-md rounded-md p-4">
            <div className="mb-6">
              <div className="text-6xl text-primary-light-green font-bold">
                <h1>Login</h1>
              </div>
              {/* <p className="text-gray-600 mt-1">
                Login with the data you entered during your registration.
              </p> */}
            </div>
            <form>
              <div className="mb-4">
                <label className="block text-gray-700 font-bold mb-2">
                  Email
                </label>
                <input
                  type="email"
                  id="email"
                  className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                  placeholder="john.doe@gmail.com"
                />
              </div>
              <div className="mb-4">
                <label className="block text-gray-700 font-bold mb-2">
                  Password
                </label>
                <input
                  type="password"
                  id="password"
                  className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                  placeholder="***********"
                />
              </div>
              <div className="flex items-center justify-between">
                <button
                  type="submit"
                  className="bg-primary-light-green hover:bg-primary-dark-green text-white font-bold py-2 px-4 rounded focus:outline-none duration-300 focus-shadow-outline"
                >
                  Log in
                </button>
                <a
                  href="#"
                  className="inline-block align-baseline font-bold text-sm text-primary-light-green hover:text-primary-dark-green duration-300"
                >
                  Did you forget your password?
                </a>
              </div>
            </form>
            <div className="mt-4">
              <p className="text-sm font-light text-gray-500 dark:text-gray-400">
                Don't have an account yet?{" "}
                <Link
                  href="/home-page"
                  className="font-medium text-primary-dark-green 
                  hover:underline"
                >
                  Register here
                </Link>
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
