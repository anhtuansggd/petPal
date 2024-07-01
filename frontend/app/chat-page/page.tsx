"use client";

import Chat from "./chat";
import ChatSideBar from "./chatSideBar";
import { useState, useEffect } from "react";
import { Account } from "./interfaces";
import Contract from "./contract";

export default function ChatPage() {
  const [accounts, setAccounts] = useState<Account[]>([
    { name: "bibi" },
    { name: "bonbon" },
    { name: "bebe" },
  ]);
  const [selectedAccount, setSelectedAccount] = useState<Account | null>(null);

  const onAccountSelect = (account: Account) => {
    setSelectedAccount(account);
  };

  return (
    <div className="flex grow w-full h-full">
      <ChatSideBar accounts={accounts} onAccountSelect={onAccountSelect} />
      <Chat selectedAccount={selectedAccount} />
      <Contract />
    </div>
  );
}
