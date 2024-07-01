"use client";
import { Typography } from "@material-tailwind/react";

export default function Footer() {
  const currentYear = new Date().getFullYear();
  return (
    <footer className=" w-full bg-primary-dark-green">
      <div className=" w-8/12 mx-auto flex flex-row flex-wrap items-center justify-center gap-y-6 gap-x-12  bg-primary-dark-green text-center md:justify-between">
        <img src="/petpal_logo.png" alt="logo-ct" className="w-40 text-white" />
        <ul className="flex flex-wrap items-center  gap-y-2 gap-x-8">
          <li>
            <Typography
              as="a"
              href="#"
              color="blue-gray"
              className="font-normal text-white  "
            >
              About us
            </Typography>
          </li>
          <li>
            <Typography
              as="a"
              href="#"
              color="blue-gray"
              className="font-normal text-white"
            >
              Support
            </Typography>
          </li>
          <li>
            <Typography
              as="a"
              href="#"
              color="blue-gray"
              className="font-normal text-white"
            >
              Social
            </Typography>
          </li>
          <li>
            <Typography
              as="a"
              href="#"
              color="blue-gray"
              className="font-normal text-white"
            >
              Legal
            </Typography>
          </li>
        </ul>
      </div>
      <hr className="my-4 mx-auto w-8/12 border-blue-gray-50" />
      <Typography
        color="blue-gray"
        className="text-center font-normal text-white"
      >
        &copy; 2024 PetPal - Pet Sitting Service
      </Typography>
    </footer>
  );
}
