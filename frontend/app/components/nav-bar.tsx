"use client";

import Image from "next/image";
import React, { useEffect, useState } from "react";
import Link from "next/link";

import { Avatar, Button } from "@material-tailwind/react";

export default function NavBar() {
  const [user, setUser] = useState(null);

  useEffect(() => {
    const loginData = localStorage.getItem("loginData");
    if (loginData) {
      setUser(JSON.parse(loginData));

      console.log(loginData);
    } 
  }, []);
  return (
    <>
      <div className="w-full h-20 bg-white z-50 sticky top-0 border-y">
        <div className="container mx-auto px-4 h-full">
          <div className="flex justify-between items-center h-full">
            <Link className=" text-primary-light-green" href="/home-page">
              <Image
                src="/logo.png"
                height={300}
                width={240}
                alt="Hero image"
                className="rounded"
              />
            </Link>

            <ul className="hidden md:flex gap-x-10 text-[#134848]">
              <li>
                <Link className="font-bold" href="/">
                  <p>Home</p>
                </Link>
              </li>
              <li>
                <Link href="/">
                  <p>Updates</p>
                </Link>
              </li>
              <li>
                <Link href="/">
                  <p>Services</p>
                </Link>
              </li>
              <li>
                <Link href="/">
                  <p>Features</p>
                </Link>
              </li>
              <li>
                <Link href="/">
                  <p>About Us</p>
                </Link>
              </li>
            </ul>

            <ul className="hidden md:flex gap-x-8 items-center">
              <li>
                <Link href="/">EN</Link>
              </li>
              <li>
                {user ? (
                  <div className="avatar">
                    <Link href={"/user-profile"}>
                      <Avatar src="/placeholder_avatar.png" alt="avatar" />
                    </Link>
                  </div>
                ) : (
                  <Button className="h-9 w-24 rounded-lg bg-[#5a35d6] text-white px-5">
                    <Link href={"/log-in"}>LogIn</Link>
                  </Button>
                )}
              </li>
            </ul>
          </div>
        </div>
      </div>
    </>
  );
}
