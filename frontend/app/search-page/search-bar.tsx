"use client";

import { Input } from "@material-tailwind/react";
import { Button } from "@material-tailwind/react";

export { Input, Button };
export default function SearchBar() {
  return (
    <div className="container mx-auto mt-10">
      <div className="text-6xl mb-10 text-center text-primary-light-green font-bold">
        <h1>Find sitters for you pets now!</h1>
      </div>

      <div className=" mt-4 flex gap-4 justify-center">
        <div>
          {" "}
          <Input
            icon={
              <i className="fa fa-location-arrow text-primary-dark-green"></i>
            }
            label="Enter your location"
          />
        </div>
        <div>
          {" "}
          <Input
            icon={<i className="fa fa-bars text-primary-dark-green" />}
            label="Type of service"
          />
        </div>{" "}
        <div>
          {" "}
          <Input
            icon={<i className="fa fa-calendar text-primary-dark-green" />}
            label="Add days"
          />
        </div>
        <div>
          {" "}
          <Input
            icon={<i className="fa fa-paw text-primary-dark-green" />}
            label="Type of pet"
          />
        </div>
        <div>
          {" "}
          <Button className="bg-primary-dark-green">Search</Button>
        </div>
      </div>
    </div>
  );
}
