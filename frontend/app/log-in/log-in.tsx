"use client";
import React, { useState } from "react";
import { Input, Button } from "@material-tailwind/react";
import Image from "next/image";
import Link from "next/link";
import { useRouter } from "next/navigation";

// import getData from "../services/fetchService";

export default function LogIn() {
  const router = useRouter();
  const [username, setUserName] = useState("");
  const [password, setPassword] = useState("");
  const [isLoading, setIsLoading] = useState(false);

  const handleLogin = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setIsLoading(true);

    const User = {
      username,
      password,
    };

    try {
      const res = await fetch("http://localhost:8081/api/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(User),
      });

      if (!res.ok) {
        throw new Error("Login failed");
      }

      const data = await res.json();
      console.log("User Login:", data);

      // Save session to local storage
      localStorage.setItem("session", JSON.stringify(data));

      // Set session expiration
      const expirationTime = new Date().getTime() + 60 * 60 * 1000; // 60 minutes
      localStorage.setItem("sessionExpiration", expirationTime.toString());

      // Redirect to home page
      router.push("/home-page");
    } catch (error) {
      console.error("Error during log-in session:", error);
      // Handle error (e.g., show error message to user)
    } finally {
      setIsLoading(false);
    }
  };

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
              <div className="text-5xl text-primary-light-green font-bold">
                <h1>Welcome back!</h1>
              </div>
            </div>
            <form
              onSubmit={handleLogin}
              className="space-y-4 md:space-y-6"
              action="#"
            >
              <div className="mb-4">
                <label className="block text-gray-700 font-bold mb-2">
                  User name
                </label>
                <input
                  type="name"
                  id="username"
                  className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                  placeholder="Your username"
                  onChange={(e) => setUserName(e.target.value)}
                  value={username}
                />
              </div>
              <div className="mb-4">
                <label className="block text-gray-700 font-bold mb-2">
                  Password
                </label>
                <input
                  type="password"
                  id="password"
                  autoComplete="true"
                  className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                  placeholder="***********"
                  onChange={(e) => setPassword(e.target.value)}
                  value={password}
                />
              </div>
              <div className="flex items-center justify-between">
                <Button
                  // onClick={getData}
                  type="submit"
                  className="bg-primary-light-green hover:bg-primary-dark-green text-white font-bold py-2 px-4 rounded focus:outline-none duration-300 focus-shadow-outline"
                >
                  Log in
                </Button>
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
                  href="/sign-up"
                  className="font-medium text-primary-dark-green 
                  hover:underline"
                >
                  Sign up here
                </Link>
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
