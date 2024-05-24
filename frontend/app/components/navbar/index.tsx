// "use client";
// import { useState } from "react";
import React from "react";
import Link from "next/link";

const Navbar = () => {
  // const [isOpen, setIsOpen] = useState(false);
  // const toggle = () => {
  //   setIsOpen(!isOpen);
  // };

  return (
    <>
      <div className="w-full h-20 bg-emerald-800 sticky top-0">
        <div className="container mx-auto px-4 h-full">
          <div className="flex justify-between items-center h-full">
            <Link href="/">PETPAL LOGO</Link>

            <ul className="hidden md:flex gap-x-6 text-white">
              <li>
                <Link href="/">
                  <p>Home</p>
                </Link>
              </li>
              <li>
                <p>Updates</p>
              </li>
              <li>
                <p>Services</p>
              </li>
              <li>
                <p>Features</p>
              </li>
              <li>
                <p>About Us</p>
              </li>
            </ul>

            <ul className="hidden md:flex gap-x-4">
              <li>
                <Link href="/">EN</Link>
              </li>
              <li>
                <Link href="/">Login</Link>
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