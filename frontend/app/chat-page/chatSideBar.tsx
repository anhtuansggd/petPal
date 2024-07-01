import { useState, useEffect } from "react";
import { Avatar, Input } from "@material-tailwind/react";

export default function ChatSideBar({ accounts, onAccountSelect }: any) {
  const [searchInput, setSearchInput] = useState<string>("");

  return (
    <div className="flex flex-col h-full">
      <div className="p-4 cursor-pointer hover:bg-gray-200">Current user</div>

      <Input
        type="text"
        value={searchInput}
        onChange={(e) => setSearchInput(e.target.value)}
        placeholder=""
        className="w-full mb-6"
        label="Search"
      />

      <div className="w-64 grow bg-gray-100 flex-col overflow-y-scroll ">
        {accounts.map((account: any) => (
          <div
            key={account.name}
            className="p-4 cursor-pointer hover:bg-primary-light-green duration-300 flex-row"
            onClick={() => onAccountSelect(account)}
          >
            <Avatar src="/chat-page/defaultAvatar.jpg" alt="avatar" />
            <span className="m-10">{account.name}</span>
          </div>
        ))}
      </div>
    </div>
  );
}
