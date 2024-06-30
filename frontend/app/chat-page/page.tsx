"use client";
import ajax from "../services/fetchService"
import Chat from "./chat";
import ChatSideBar from "./chatSideBar";
import { useState, useEffect } from "react";
import { Account } from "./interfaces";
import NavBar from "../components/nav-bar";

export default function ChatPage() {
  const [accounts, setAccounts] = useState<any>([]);

  useEffect(() => {
    ajax("api/chat/contacts/1", "GET").then((contacts) => {
      setAccounts(contacts)
      console.log('contacts: ', contacts)
    })
  }, [])

  const [selectedAccount, setSelectedAccount] = useState<Account | null>(null);

  const onAccountSelect = (account: Account) => {
    setSelectedAccount(account);
  };

  return (
    <div className="flex flex-col w-screen h-screen overflow-x-hidden overflow-y-hidden">
      <NavBar />
      <div className="grow">
        <div className="flex grow w-full h-full">
          <ChatSideBar accounts={accounts} onAccountSelect={onAccountSelect} />
          <Chat selectedAccount={selectedAccount} />
        </div>
      </div>
    </div>

  );
}
