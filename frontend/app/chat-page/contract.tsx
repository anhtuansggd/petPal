import React, { useEffect, useState } from "react";
import { Input, Button } from "@material-tailwind/react";
import { useRouter } from "next/navigation";
import Link from "next/link";
export default function Contract() {
  const router = useRouter();

  const [role, setRole] = useState<string>("Petowner");

  const [serviceType, setServiceType] = useState<string>("");
  const [petType, setPetType] = useState<string>("");
  const [startDate, setStartDate] = useState<string>("");
  const [endDate, setEndDate] = useState<string>("");

  useEffect(() => {
    const contractData = localStorage.getItem("contractData");
    const loginData = localStorage.getItem("loginData");

    if (contractData) {
      const parsedContractData = JSON.parse(contractData);
      setServiceType(parsedContractData.serviceType);
      setPetType(parsedContractData.petType);
      setStartDate(parsedContractData.startDate);
      setEndDate(parsedContractData.endDate);
    }
    if (loginData) {
      const parsedLoginData = JSON.parse(loginData);
      const userRole = parsedLoginData.isCaregiver ? "Caregiver" : "Petowner";
      setRole(userRole);
    }
  }, []);

  return (
    <div>
      <div className="bg-white rounded-lg shadow-md p-6">
        <h2 className="text-2xl font-bold mb-4">Contract Detail</h2>
        <p className="text-gray-600 mb-6">
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
              value={serviceType}
            ></Input>
          </div>
          <div className="mb-4">
            <Input
              className="rounded-lg px-3 py-2"
              variant="outlined"
              color="teal"
              label="Types of pet"
              placeholder="Dog, Car or Guinea Pig"
              value={petType}
            ></Input>
          </div>
          <div className="mb-4 flex flex-col gap-4">
            <div className="flex items-center">
              <Input
                type="date"
                color="teal"
                label="Start day"
                className="rounded-lg border border-gray-300 px-3 py-2 focus:outline-none focus:border-blue-500"
                value={startDate}
              />
            </div>
            <div className="flex items-center">
              <Input
                type="date"
                color="teal"
                label="End day"
                className="rounded-lg border border-gray-300 px-3 py-2 focus:outline-none focus:border-blue-500"
                value={endDate}
              />
            </div>
          </div>
          <div>
            {role === "Petowner" ? (
              <Button type="submit" className="bg-primary-dark-green">
                Confirm
              </Button>
            ) : (
              <Button type="submit" className="bg-primary-dark-green">
                Accept
              </Button>
            )}
          </div>
        </form>
      </div>
    </div>
  );
}
