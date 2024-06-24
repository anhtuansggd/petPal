"use client";
import Image from "next/image";
import Link from "next/link";
import { Button } from "@material-tailwind/react";
export default function Map() {
  return (
    <div className="w-8/12 my-24 mx-auto text-center">
      <div className="text-6xl mb-10 text-primary-light-green font-bold">
        <h1 className="mb-4">Find sitters for your pets now!</h1>
        <Link href={"/search-page"}>
          <Button className="font-normal bg-primary-dark-green">
            <i className="fa fa-paw text-white mr-2" />
            Connect now!
          </Button>
        </Link>
      </div>

      <div>
        <Image
          src="/map.jpg"
          width="0"
          height="0"
          sizes="100vw"
          className="w-full h-auto rounded"
          alt=""
        />
      </div>
    </div>
  );
}
