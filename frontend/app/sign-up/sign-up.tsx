"use client";
import React from "react";
import { Input, Button, Select, Option } from "@material-tailwind/react";
import Link from "next/link";

import { useRouter } from "next/navigation";
import { useState } from "react";

export default function SignUp() {
  const router = useRouter();
  const [name, setName] = useState("");
  const [username, setUserName] = useState("");
  const [email, setEmail] = useState("");
  const [phone, setPhone] = useState("");
  const [password, setPassword] = useState("");
  // const [isCareGiver, setIsCareGiver] = useState<Number>(1);
  const isCaregiver = 1;
  const [isLoading, setIsLoading] = useState(false);

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setIsLoading(true);

    const newUser = {
      name,
      phone,
      email,
      username,
      password,
      isCaregiver,
    };

    try {
      const res = await fetch("http://localhost:8081/api/auth/register", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(newUser),
      });

      if (!res.ok) {
        throw new Error("Registration failed");
      }

      const data = await res.json();
      console.log("New user created:", data);

      // Save session to local storage
      localStorage.setItem("session", JSON.stringify(data));

      // Save user data and session to local storage
      localStorage.setItem(
        "userData",
        JSON.stringify({
          name: data.name,
          email: data.email,
          username: data.username,
          // Add any other relevant user data you want to store
        })
      );
      // const expirationTime = new Date().getTime() + 15 * 60 * 1000; // 15 minutes
      // localStorage.setItem("sessionExpiration", expirationTime.toString());

      // Redirect to home page
      router.push("/home-page");
    } catch (error) {
      console.error("Error during registration:", error);
      // Handle error (e.g., show error message to user)
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className=" w-8/12 mx-auto mt-12 text-center ">
      <div className="text-6xl mb-10 text-primary-light-green font-bold">
        <h1> Create an account</h1>
      </div>

      <div className="flex flex-col items-center justify-center px-6 py-8 mx-auto lg:py-0">
        <div className="w-full bg-white rounded-lg shadow dark:border md:mt-0 sm:max-w-md xl:p-0 ">
          <div className="p-6 space-y-4 md:space-y-6 sm:p-8">
            <h1 className="text-xl font-bold leading-tight tracking-tight text-primary-dark-green md:text-2xl">
              Enter your info here
            </h1>
            <form
              onSubmit={handleSubmit}
              className="space-y-4 md:space-y-6"
              action="#"
            >
              <div>
                <Input
                  variant="outlined"
                  color="teal"
                  type="text"
                  required
                  label="Name"
                  className=" rounded-lg px-3 py-2 "
                  onChange={(e) => setName(e.target.value)}
                  value={name}
                />
              </div>
              <div>
                <Input
                  variant="outlined"
                  color="teal"
                  type="text"
                  label="User name"
                  className=" rounded-lg px-3 py-2 "
                  onChange={(e) => setUserName(e.target.value)}
                  value={username}
                />
              </div>
              <div>
                <Input
                  variant="outlined"
                  color="teal"
                  type="email"
                  label="Email"
                  className=" rounded-lg px-3 py-2 "
                  onChange={(e) => setEmail(e.target.value)}
                  value={email}
                />
              </div>
              <div>
                <Input
                  variant="outlined"
                  color="teal"
                  type="tel"
                  label="Phone number"
                  className=" rounded-lg px-3 py-2 "
                  onChange={(e) => setPhone(e.target.value)}
                  value={phone}
                />
              </div>
              <div>
                <Input
                  variant="outlined"
                  color="teal"
                  type="password"
                  label="Password"
                  autoComplete="true"
                  className="rounded-lg px-3 py-2 "
                  onChange={(e) => setPassword(e.target.value)}
                  value={password}
                />
              </div>

              {/* <div>
                <Select
                  className="rounded-lg px-3 py-2 "
                  variant="outlined"
                  color="teal"
                  label="You are?"
                  onChange={(value) => setIsCareGiver(Number(value))}
                  value={isCareGiver.toString()}
                >
                  <Option value="0">Pet owner</Option>
                  <Option value="1">Care giver</Option>
                </Select>
              </div> */}

              <Button
                type="submit"
                className="bg-primary-light-green hover:bg-primary-dark-green text-white font-bold py-2 px-4 rounded  duration-300 "
                disabled={isLoading}
              >
                {isLoading && <span>Signing up</span>}
                {!isLoading && <span>Sign up</span>}
              </Button>

              <p className="text-sm font-light text-gray-500 dark:text-gray-400">
                Already have an account?{" "}
                <Link
                  href="/log-in"
                  className="font-medium text-primary-dark-green 
                    hover:underline"
                >
                  Login here
                </Link>
              </p>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}
