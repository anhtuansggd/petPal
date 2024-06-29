"use client";
import React from "react";
import { Input, Button, Select, Option } from "@material-tailwind/react";
import Link from "next/link";

import { useRouter } from "next/navigation";
import { useState } from "react";

export default function SignUp() {
  const [name, setName] = useState("");
  const [userName, setUserName] = useState("");
  const [email, setEmail] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [password, setPassword] = useState("");
  const [isCareGiver, setIsCareGiver] = useState(1);
  const [isLoading, setIsLoading] = useState(false);

  const handleSubmit = async (e: any) => {
    e.preventDefault();
    setIsLoading(true);

    const newUser = {
      name,
      userName,
      email,
      phoneNumber,
      password,
      isCareGiver,
    };

    const res = await fetch("http://localhost:8081/api/auth/register", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(newUser),
    });

    const final = await res.json();
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
                  value={userName}
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
                  onChange={(e) => setPhoneNumber(e.target.value)}
                  value={phoneNumber}
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
                  onChange={(e) => setIsCareGiver(e.target.)}
                  value={isCareGiver}
                >
                  <Option value="0">Pet owner</Option>
                  <Option value="1">Pet sitter</Option>
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
