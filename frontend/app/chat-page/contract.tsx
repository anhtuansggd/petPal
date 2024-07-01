import React from "react";
import { Input, Button } from "@material-tailwind/react";
import Link from "next/link";
export default function Contract() {
  return (
    <div>
      <div className="bg-white rounded-lg shadow-md p-6">
        <h2 className="text-2xl font-bold mb-4">Contract Detail</h2>
        <p className="text-gray-600 mb-4">
          Please check the info before the confirmation
        </p>
        <form>
          <div className="mb-4">
            <Input
              className="rounded-lg px-3 py-2"
              variant="outlined"
              color="teal"
              label="Type of service"
              placeholder="Pet Sitting or Pet Hosting"
            ></Input>
          </div>
          <div className="mb-4">
            <Input
              className="rounded-lg px-3 py-2"
              variant="outlined"
              color="teal"
              label="Types of pet"
              placeholder="Dog, Car or Guinea Pig"
            ></Input>
          </div>
          <div className="mb-4 flex">
            <div className="flex items-center mr-4">
              <Input
                type="date"
                color="teal"
                label="Start day"
                className="rounded-lg border border-gray-300 px-3 py-2 focus:outline-none focus:border-blue-500"
              />
            </div>
            <div className="flex items-center">
              <Input
                type="date"
                color="teal"
                label="End day"
                className="rounded-lg border border-gray-300 px-3 py-2 focus:outline-none focus:border-blue-500"
                value={"hihi"}
              />
            </div>
          </div>
          <div>
            <Link href={"/chat-page"}>
              <Button type="submit" className="bg-primary-dark-green">
                Confirm
              </Button>
            </Link>
          </div>
        </form>
      </div>
    </div>
  );
}
