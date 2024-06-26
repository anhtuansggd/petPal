import { useState, useEffect } from "react";
import { Account, Message } from "./interfaces";
import ajax from "../services/fetchService"

interface ChatProps {
  selectedAccount: Account | null;
}

export default function Chat({ selectedAccount }: ChatProps) {
  const [messages, setMessages] = useState<Message[]>([]);
  const [input, setInput] = useState<string>("");
  const [user, setUser] = useState<string>("username");

  useEffect(() => {
    // fetch message
  }, []);

  const handleSendMessage = async () => {
    if (input.trim() === "") return;

    const newMessage = { user, text: input };
    try {
      if (selectedAccount !== null) {
        // const res = send message to server
        const res = { data: newMessage.text };
        setMessages([
          ...messages,
          { id: messages.length + 1, user: selectedAccount.name, text: input },
        ]);
        setInput("");

        ajax("api/chat/send", "POST", {
          "senderId": 1,
          "receiverId": 2,
          "message": "another one bites the dust"
        }).then(() => {
          console.log('huh')
        })
      }
    } catch {}
  };

  return (
    selectedAccount !== null && (
      <div className="flex flex-col w-full">
        <div className="flex-none p-4 bg-gray-100">{selectedAccount.name}</div>

        <div className="grow">
          {messages.map((msg) => (
            <div key={msg.id}>
              <strong>{msg.user}: </strong>
              {msg.text}
            </div>
          ))}
        </div>

        <div className="flex">
          <input
            type="text"
            value={input}
            onChange={(e) => setInput(e.target.value)}
            placeholder="Type a message..."
            className="p-2 w-full mb-4"
          />
          <button
            onClick={handleSendMessage}
            className="p-2 bg-blue-500 text-white rounded"
          >
            Send
          </button>
        </div>
      </div>
    )
  );
}
