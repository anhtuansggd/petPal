"use client";

import { ACTION } from "next/dist/client/components/app-router-headers";
import Chat from "./chat";
import ChatSideBar from "./chatSideBar";
import { useState, useEffect } from "react";

interface Account {
  name: string;
}

export default function ChatPage() {
  const [accounts, setAccounts] = useState<Account[]>([
    { name: "bibi" },
    { name: "bonbon" },
    { name: "bebe" },
  ]);
  const [selectedAccount, setSelectedAccount] = useState(null);

  const onAccountSelect = (account: any) => {
    setSelectedAccount(account);
  };

  return (
    <div className="flex grow w-full h-full">
      <ChatSideBar accounts={accounts} onAccountSelect={onAccountSelect} />
      <Chat selectedAccount={selectedAccount} />
    </div>
  );
}
