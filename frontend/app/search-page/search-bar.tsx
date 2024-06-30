"use client";

import { Input, Button, Select, Option } from "@material-tailwind/react";
import Link from "next/link";
import Image from "next/image";
import { useRouter } from "next/navigation";
import { useState } from "react";
import Map from "../components/map";

export default function SearchBar() {
  const router = useRouter();
  const [petIds, setPetIds] = useState("");
  const [serviceType, setServiceType] = useState("");
  const [startDay, setStartDay] = useState("");
  const [endDay, setEndDay] = useState("");

  const handleSelectChange = (value: string) => {
    setServiceType(value);
  };
  const [isLoading, setIsLoading] = useState(false);

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setIsLoading(true);

    const newBooking = {
      petIds,
      serviceType,
      startDay,
      endDay,
    };

    try {
      const res = await fetch("http://localhost:8081/api/auth/register", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(newBooking),
      });

      if (!res.ok) {
        throw new Error("Failed");
      }

      const data = await res.json();
      console.log("New booking created:", data);

      // Save session to local storage
      localStorage.setItem("session", JSON.stringify(data));

      // Save user data and session to local storage
      localStorage.setItem(
        "userData",
        JSON.stringify({
          petIds: data.petIds,
          serviceType: data.serviceType,
          startDay: data.startDay,
          endDay: data.endDay,
        })
      );
    } catch (error) {
      console.error("Error during booking:", error);
      // Handle error (e.g., show error message to user)
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="container mx-auto mt-10">
      <div className="text-6xl mb-10 text-center text-primary-light-green font-bold">
        <h1>Find sitters for you pets now!</h1>
      </div>

      <form
        onSubmit={handleSubmit}
        action="#"
        className=" mt-4 flex gap-4 justify-center"
      >
        <div>
          {" "}
          <Input
            icon={
              <i className="fa fa-location-arrow text-primary-dark-green"></i>
            }
            variant="outlined"
            color="teal"
            type="text"
            required
            label="Enter your location"
            // onChange={(e) => setName(e.target.value)}
            // value={name}
          />
        </div>
        <div>
          {" "}
          <Select
            className="rounded-lg px-3 py-2 "
            variant="outlined"
            color="teal"
            label="Type of service"
            // onChange={(e) => handleSelectChange(e.target.value)}
            value={serviceType}
          >
            <Option value="PET_SITTING">Pet Sitting</Option>
            <Option value="DAY_CARE">Day Care</Option>
          </Select>
        </div>
        <div>
          <Input
            type="date"
            color="teal"
            label="Start day"
            className="rounded-lg border border-gray-300 px-3 py-2 focus:outline-none focus:border-blue-500"
          />
        </div>
        <div>
          <Input
            type="date"
            color="teal"
            label="End day"
            className="rounded-lg border border-gray-300 px-3 py-2 focus:outline-none focus:border-blue-500"
          />
        </div>
        <div>
          {" "}
          <Select
            className="rounded-lg px-3 py-2 "
            variant="outlined"
            color="teal"
            label="Types of pet"
          >
            <Option value="0">Dog</Option>
            <Option value="1">Cat</Option>
            <Option value="1">Guinea pig</Option>
          </Select>
        </div>{" "}
        <div>
          {" "}
          <Button className="bg-primary-dark-green">Search</Button>
        </div>
      </form>

      <div className="w-9/12 mt-12 mx-auto">
        <Map />
        {/*<Link href={"/sitter-info"}>
          <Image
            src="/sitter1.jpg"
            width="0"
            height="0"
            sizes="100vw"
            style={{ width: "100%", height: "auto" }}
            alt="Map image"
            className="rounded"
          />
        </Link> */}
      </div>
    </div>
  );
}
