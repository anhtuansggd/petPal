"use client";
import { Button } from "@material-tailwind/react";
import Image from "next/image";
import React, { useEffect, useState } from "react";
import { useRouter } from "next/navigation";

interface UserData {
  id: number;
  name: string;
  picture?: string;
  role?: string;
}
export default function UserProfile() {
  const router = useRouter();
  const [user, setUser] = useState<UserData[]>([]);
  const [role, setRole] = useState<string>("Petowner");
  const [name, setName] = useState<string>("");
  const [email, setEmail] = useState<string>("");

  useEffect(() => {
    const fetchUserData = async () => {
      try {
        const response = await fetch(
          "http://localhost:8081/api/users/profile/test",
          {
            method: "GET",
            headers: {
              "Content-Type": "application/json",
            },
          }
        );

        if (!response.ok) {
          throw new Error("Network response was not ok");
        }

        const data = await response.json();
        setUser(data);
        console.log(data);
      } catch (error) {
        console.error("Error fetching user data:", error);
      }
    };

    fetchUserData();

    const loginData = localStorage.getItem("loginData");
    if (loginData) {
      const parsedLoginData = JSON.parse(loginData);
      const userRole = parsedLoginData.isCaregiver ? "Caregiver" : "Petowner";
      setRole(userRole);
      setName(parsedLoginData.name);
      setEmail(parsedLoginData.email);
    }
  }, []);

  return (
    <div className="bg-gray-100 h-fit">
      <div className="w-8/12 mx-auto py-8">
        <div className="grid grid-cols-4 sm:grid-cols-12 gap-6 px-4">
          <div className="col-span-4 sm:col-span-3">
            <div className="bg-white shadow rounded-lg p-6">
              <div className="flex flex-col items-center">
                <div>
                  {" "}
                  {/* Ensure you have a unique key here */}
                  <Image
                    src={"/placeholder_avatar.png"}
                    width={120}
                    height={120}
                    alt="Placeholder image"
                    className="bg-gray-300 rounded-full mb-4 shrink-0 text-center"
                  />
                  <h1 className="text-xl font-bold text-center">{name}</h1>
                  <p className="text-center">{email}</p>
                  <p className="text-center">{role}</p>
                </div>

                <div className="mt-6 flex flex-wrap gap-4 justify-center">
                  <Button
                    ripple={true}
                    className="bg-primary-light-green text-white hover:bg-primary-dark-green py-2 px-4 duration-300"
                  >
                    Edit info
                  </Button>
                  <Button
                    onClick={() => {
                      localStorage.removeItem("loginData");
                      router.push("/home-page");
                    }}
                    ripple={true}
                    color="red"
                    className="hover:bg-red-800 text-white py-2 px-4 duration-300"
                  >
                    Log out
                  </Button>
                </div>
              </div>
            </div>
          </div>
          <div className="col-span-4 sm:col-span-9">
            <div className="bg-white shadow rounded-lg p-6">
              <div>
                {role === "Petowner" ? (
                  <>
                    <h1 className="text-5xl font-bold mb-6 text-primary-light-green">
                      Petowner Details
                    </h1>
                    <p className="text-2xl font-bold text-gray-700 mb-1">
                      Your Pets:
                    </p>
                    {/* <ul className="text-gray-700 list-disc pl-5">
                      <li>Dog</li>
                      <li>Cat</li>
                      <li>Guinea Pig</li>
                    </ul> */}
                    <form className="text-gray-700">
                      <label className="block mb-2">
                        <input type="checkbox" className="mr-2" />
                        Dog
                      </label>
                      <label className="block mb-2">
                        <input type="checkbox" className="mr-2" />
                        Cat
                      </label>
                      <label className="block mb-2">
                        <input type="checkbox" className="mr-2" />
                        Guinea Pig
                      </label>
                    </form>
                  </>
                ) : (
                  <>
                    <h1 className="text-5xl font-bold mb-4 text-primary-light-green">
                      Caregiver Details
                    </h1>
                    <p className="text-2xl font-bold text-gray-700 mb-1">
                      <strong>Service:</strong>
                    </p>
                    <ul className="text-gray-700 list-disc pl-5 mb-1">
                      <li>Pet Hosting</li>
                      <li>Pet Sitting</li>
                    </ul>
                    {/* <form className="text-gray-700">
                      <label className="block mb-2">
                        <input type="checkbox" className="mr-2" />
                        Pet Hosting
                      </label>
                      <label className="block mb-2">
                        <input type="checkbox" className="mr-2" />
                        Pet Sitting
                      </label>
                    </form> */}
                    <p className="text-2xl font-bold text-gray-700 mb-2">
                      <strong>Availability:</strong>
                    </p>
                    <form className="text-gray-700 flex justify-between mb-1">
                      {[
                        "Monday",
                        "Tuesday",
                        "Wednesday",
                        "Thursday",
                        "Friday",
                        "Saturday",
                        "Sunday",
                      ].map((day) => (
                        <label key={day} className="block">
                          <input type="checkbox" className="mr-2" />
                          {day}
                        </label>
                      ))}
                    </form>
                    <p className="text-2xl font-bold text-gray-700 mb-1">
                      <strong>Pet Types:</strong>
                    </p>
                    <ul className="text-gray-700 list-disc pl-5">
                      <li>Dog</li>
                      <li>Cat</li>
                      <li>Guinea Pig</li>
                    </ul>
                    {/* <form className="text-gray-700">
                      <label className="block mb-2">
                        <input type="checkbox" className="mr-2" />
                        Dog
                      </label>
                      <label className="block mb-2">
                        <input type="checkbox" className="mr-2" />
                        Cat
                      </label>
                      <label className="block mb-2">
                        <input type="checkbox" className="mr-2" />
                        Guinea Pig
                      </label>
                    </form> */}
                  </>
                )}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
