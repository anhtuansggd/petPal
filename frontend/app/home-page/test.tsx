"use client";
import { Typography } from "@material-tailwind/react";

const LINKS = [
  {
    title: "Product",
    items: ["Overview", "Features", "Solutions", "Tutorials"],
  },
  {
    title: "Company",
    items: ["About us", "Careers", "Press", "News"],
  },
  {
    title: "Resource",
    items: ["Blog", "Newsletter", "Events", "Help center"],
  },
];

const currentYear = new Date().getFullYear();

export default function Test() {
  return (
    <footer className=" w-full bg-primary-dark-green p-8">
      <div className=" w-8/12 mx-auto flex flex-row flex-wrap items-center justify-center gap-y-6 gap-x-12  bg-primary-dark-green text-center md:justify-between">
        <img src="/logo.png" alt="logo-ct" className="w-60 text-white" />
        <ul className="flex flex-wrap items-center  gap-y-2 gap-x-8">
          <li>
            <Typography
              as="a"
              href="#"
              color="blue-gray"
              className="font-normal text-white  "
            >
              About
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
      <hr className="my-8 mx-auto w-8/12 border-blue-gray-50" />
      <Typography
        color="blue-gray"
        className="text-center font-normal text-white"
      >
        &copy; 2024 PetPal - Pet Sitting Service
      </Typography>
    </footer>
  );
}
