// "use client";
// import { useState } from "react";
import React from "react";
import Link from "next/link";
import Button from "./Button";

const Navbar = () => {
  // const [isOpen, setIsOpen] = useState(false);
  // const toggle = () => {
  //   setIsOpen(!isOpen);
  // };

  //#28221a
  return (
    <>
      <div className="w-full h-20 bg-white sticky top-0 border-y">
        <div className="container mx-auto px-4 h-full">
          <div className="flex justify-between items-center h-full">
            <Link className="pl-10 pr-16 text-[#48c5bc]" href="/">PETPAL LOGO</Link>

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
                <Button />
              </li>
              <li>
                <Link href="/">AVATAR</Link>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </>
  );
};

export default Navbar;