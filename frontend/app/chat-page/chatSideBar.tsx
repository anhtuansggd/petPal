import { useState, useEffect } from "react";

export default function ChatSideBar({ accounts, onAccountSelect }) {
  const [searchInput, setSearchInput] = useState<string>("");

  return (
    <div className="flex grow flex-col h-full">
      <div className="p-4 cursor-pointer hover:bg-gray-200">Current user</div>

      <input
        type="text"
        value={searchInput}
        onChange={(e) => setSearchInput(e.target.value)}
        placeholder=""
        className="p-2 w-full mb-4"
      />

      <div className="w-64 grow bg-gray-100 flex-col overflow-y-scroll">
        {accounts.map((account: any) => (
          <div
            key={account.name}
            className="p-4 cursor-pointer hover:bg-gray-200"
            onClick={() => onAccountSelect(account)}
          >
            {account.name}
          </div>
        ))}
      </div>
    </div>
  );
}
