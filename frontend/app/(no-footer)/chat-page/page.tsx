"use client";
import ajax from "../../services/fetchService"

import Chat from "./chat";
import ChatSideBar from "./chatSideBar";
import { useState, useEffect } from "react";
import { Account } from "./interfaces";

export default function ChatPage() {
  // const [accounts, setAccounts] = useState<any>([]);

  // useEffect(() => {
  //   ajax("api/chat/contacts/1", "GET").then((contacts) => {
  //     setAccounts(contacts)
  //     // console.log('contacts: ', contacts)
  //   })
  // }, [])

  const [accounts, setAccounts] = useState<Account[]>([
    { name: "hassan" },
    { name: "stinky" },
    { name: "leira" },
  ]);

  const [selectedAccount, setSelectedAccount] = useState<Account | null>(null);

  const onAccountSelect = (account: Account) => {
    setSelectedAccount(account);
  };

  return (
    <div className="flex grow w-full h-full">
      <ChatSideBar accounts={accounts} onAccountSelect={onAccountSelect} />
      <Chat selectedAccount={selectedAccount} />
    </div>
  );
}
