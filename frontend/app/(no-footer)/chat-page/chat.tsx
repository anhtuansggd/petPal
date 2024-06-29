import { useState, useEffect } from "react";
import { Account, Message } from "./interfaces";
import ajax from "../../services/fetchService"
import Avatar from 'react-avatar';

export default function Chat({ selectedAccount }: any) {
  const [messages, setMessages] = useState<Message[]>([]);
  const [input, setInput] = useState<string>("");
  const [user, setUser] = useState<string>("username");

  useEffect(() => {
    ajax("api/chat/messages?senderId=1&receiverId=2", "GET").then((messages) => {
      console.log('messages user 1 send to user 2 ', messages)
    })
    ajax("api/chat/messages?senderId=2&receiverId=1", "GET").then((messages) => {
      console.log('messages user 2 send to user 1 ', messages)
    })
  }, [])

  const handleSendMessage = async () => {
    if (input.trim() === "") return;

    // try {
    //   if (selectedAccount !== null) {
    //     ajax("api/chat/send", "POST", {
    //       "senderId": 1,
    //       "receiverId": 2,
    //       "message": input
    //     }).then(() => {
    //       console.log("user 1 send 'another one bites the dust' to user 2")
    //       setInput("");
    //     })
    //   }
    // } catch {

    // }

    const newMessage = { user, text: input };
    try {
      if (selectedAccount !== null) {
        // const res = send message to server
        const res = { data: newMessage.text };
        setMessages([
          { id: messages.length + 1, user: selectedAccount.name, text: input },
          ...messages,
        ]);
        setInput("");
      }
    } catch {}
  };

  return (
    selectedAccount !== null && (
      <div className="flex flex-col w-full">
        <div className="flex-none p-4 bg-gray-100">{selectedAccount.name}</div>

        <div className="grow ml-2 flex flex-col-reverse">
          {messages.map((msg) => (
            msg.user === "hassan" ?
            <div key={msg.id} className="flex flex-row items-end m-2 self-end">
              <div className="bg-[#01afa2] text-white rounded-full rounded-br-none mr-2 px-4 py-3">
                {msg.text}
              </div>
              <Avatar name={msg.user} size="30" round={true} />
            </div> :
            <div key={msg.id} className="flex flex-row items-end m-2">
              <Avatar name={msg.user} size="30" round={true} />
              <div className="bg-[#dce8ff] text-black rounded-full rounded-bl-none ml-2 px-4 py-3">
                {msg.text}
              </div>
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
