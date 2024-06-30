"use client";
import Image from "next/image";
import { Button } from "@material-tailwind/react";
import Link from "next/link";

export default function HeroSection() {
  return (
    <div className="mt-10 w-9/12 mx-auto flex gap-6">
      <div id="left-content w-3/6">
        <Image
          src="/heroImage.png"
          height={500}
          width={550}
          alt="Hero image"
          className="rounded"
        />
      </div>
      <div
        id="right-content"
        className=" w-3/6 py-4 h-100 flex flex-col justify-between"
      >
        <div>
          <p className="text-gray">Pet Sitter Service</p>
        </div>
        <div className="flex flex-col gap-4">
          <p className="text-5xl font-bold text-primary-light-green">
            The Freedom to travel
          </p>
          <p className="font-thin">
            Discover free & unique homestays around the world, in exchange for
            caring for adorable pets
          </p>
          <hr />
        </div>

        <Link href={"/search-page"}>
          <Button className="font-normal bg-primary-dark-green">
            <i className="fa fa-paw text-white mr-2" />
            Finding a pet sitter
          </Button>
        </Link>
      </div>
    </div>
  );
}
