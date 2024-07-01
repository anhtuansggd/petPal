"use client";
import ajax from "../services/fetchService";
import getUserData from "../services/getUserData";
import Chat from "./chat";
import ChatSideBar from "./chatSideBar";
import { useState, useEffect } from "react";
import { Account } from "./interfaces";
import NavBar from "../components/nav-bar";
import Contract from "./contract";
import { Navbar } from "@material-tailwind/react";

export default function ChatPage() {
  const [accounts, setAccounts] = useState<any>([]);

  useEffect(() => {
    ajax(`api/chat/contacts/${getUserData().userId}`, "GET").then(
      (contacts) => {
        setAccounts(contacts);
        console.log("contacts: ", contacts);
      }
    );
  }, []);

  const [selectedAccount, setSelectedAccount] = useState<Account | null>(null);

  const onAccountSelect = (account: Account) => {
    setSelectedAccount(account);
  };

  return (
    <div>
      <NavBar />
      <div className="text-6xl my-10 text-center text-primary-light-green font-bold">
        <h1>Connect with pet sitters now!</h1>
      </div>
      <div className=" border border-primary-light-green rounded p-4 mt-10 w-8/12 flex flex-row justify-center mx-auto h-[80vh] ">
        <div className=" flex basis-2/3">
          <ChatSideBar accounts={accounts} onAccountSelect={onAccountSelect} />
          <Chat selectedAccount={selectedAccount} />
        </div>
        <div className="flex-basis-1/3">
          {" "}
          <Contract />
        </div>
      </div>
    </div>
  );
}
