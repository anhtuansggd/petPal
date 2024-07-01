"use client";

import Image from "next/image";
import React, { useEffect, useState } from "react";
import { usePathname } from 'next/navigation'
import Link from "next/link";

import { Avatar, Button } from "@material-tailwind/react";

const routeNames = {
  "/home-page": "Home",
  "/search-page": "Search",
  "/sitter-info": "Pet sitter",
  "/chat-page": "Chat",
  "/user-profile": "Profile",
}

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
                src="/petpal_logo.png"
                height={0}
                width={160}
                alt="Hero image"
                className="rounded"
              />
            </Link>

          <ul className="grow hidden md:flex justify-center gap-x-10 text-[#134848]">
            {Object.entries(routeNames).map(([route, routeName]) => (
              <li>
                <Link className={usePathname() === route ? "text-[#01afa2] font-bold" : ""} href={route}>
                  <p>{routeName}</p>
                </Link>
              </li>  
            ))}
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
