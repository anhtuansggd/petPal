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
        // Optionally, store the user data in localStorage if needed
        // localStorage.setItem("userData", JSON.stringify(data));
        console.log(data);
      } catch (error) {
        console.error("Error fetching user data:", error);
        // Handle errors, such as showing a notification to the user
      }
    };

    fetchUserData();
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
                    className="bg-gray-300 rounded-full mb-4 shrink-0"
                  />
                  <h1 className="text-xl font-bold"></h1>
                  <p className="text-gray-700 text-center">{"Pet owner"}</p>
                </div>

                <div className="mt-6 flex flex-wrap gap-4 justify-center">
                  <Button
                    ripple={true}
                    className="bg-primary-light-green text-white hover:bg-primary-dark-green py-2 px-4 duration-300 rounded"
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
                    className="hover:bg-red-800"
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
                <h1 className="text-5xl font-bold mb-4 text-primary-light-green">
                  My pets
                </h1>
                <p className="text-gray-700">
                  Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed
                  finibus est vitae tortor ullamcorper, ut vestibulum velit
                  convallis. Aenean posuere risus non velit egestas suscipit.
                  Nunc finibus vel ante id euismod. Vestibulum ante ipsum primis
                  in faucibus orci luctus et ultrices posuere cubilia Curae;
                  Aliquam erat volutpat. Nulla vulputate pharetra tellus, in
                  luctus risus rhoncus id.
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
