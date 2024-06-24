"use client";
import React from "react";
import Image from "next/image";
import {
  Card,
  CardHeader,
  CardBody,
  CardFooter,
  Typography,
  Button,
} from "@material-tailwind/react";

// Array of offer data objects
const servicesData = [
  {
    imageUrl: "/service1.jpg",
    title: "Pet Sitting",
    description:
      "Our professional pet sitters provide top-notch care for your furry friends in the comfort of their own home.",
  },
  {
    imageUrl: "/service2.jpg",
    title: "Dog Walking",
    description:
      "Ensure your dog gets the exercise and fresh air they need with our reliable dog walking services.",
  },
  {
    imageUrl: "/service3.jpg",
    title: "Overnight Pet Care",
    description:
      "For pets that need a little extra attention, our overnight pet care service is the perfect solution.",
  },
];

export default function Services() {
  return (
    <div className="w-8/12 mt-24 mx-auto text-center ">
      <div className="text-6xl mb-10 text-primary-light-green font-bold">
        <h1>What Petpal offers?</h1>
      </div>

      <div className="flex flex-row gap-4 justify-between mt-10">
        {servicesData.map((service, index) => (
          <div className="basis-1/3" key={index}>
            <Card className="mt-6">
              <CardHeader>
                <Image
                  src={`${service.imageUrl}`}
                  width="0"
                  height="0"
                  sizes="100vw"
                  className="w-full h-auto rounded"
                  alt={`${service.title}`}
                />
              </CardHeader>
              <CardBody>
                <Typography
                  variant="h5"
                  className=" text-primary-dark-green mb-2"
                >
                  {service.title}
                </Typography>
                <Typography className="font-normal text-black">
                  {service.description}
                </Typography>
              </CardBody>
              <CardFooter className="pt-0">
                <Button className="font-normal bg-primary-dark-green">
                  <i className="fa fa-paw text-white mr-2" />
                  Read More
                </Button>
              </CardFooter>
            </Card>
          </div>
        ))}
      </div>
    </div>
  );
}
