import { useState, useEffect } from "react";
import ajax from "../services/fetchService"
import getUserData from "../services/getUserData"
import { Avatar } from "@material-tailwind/react";

export default function Chat({ selectedAccount }: any) {
  const [messages, setMessages] = useState<any>([]);
  const [input, setInput] = useState<string>("");

  const fetchNewMessages = async () => {
    try {
      if (selectedAccount !== null) {
        ajax(`api/chat/messages/between?user1Id=${getUserData().userId}&user2Id=${selectedAccount.userId}`, "GET").then((receivedMessages) => {
          setMessages(receivedMessages)  
        })   
      }
    } catch (err) {
      console.log("Failed to fetch new messages", err)
    }
  }

  useEffect(() => {
    fetchNewMessages()
    const id = setInterval(fetchNewMessages, 1000);
    return () => clearInterval(id)
  }, [selectedAccount])

  const handleSendMessage = async () => {
    if (input.trim() === "") return;

    try {
      if (selectedAccount !== null) {
        ajax("api/chat/send", "POST", {
          "senderId": getUserData().userId,
          "receiverId": selectedAccount.userId,
          "message": input
        }).then(() => {
          console.log(`user ${getUserData().userId} send '${input}' to user ${selectedAccount.userId}`)
          setInput("");
        })
      }
    } catch {
      console.log("Failed to send message. Error connecting to the server")
    }
  };

  return (
    selectedAccount !== null && (
      <div className="flex flex-col w-full">
        <div className="flex-none p-4 bg-gray-100">{selectedAccount.name}</div>

        <div className="grow ml-2 flex flex-col-reverse overflow-y-scroll h-0">
          {messages.slice().reverse().map((msg:any) => (
            msg.sender.userId === getUserData().userId ?
            <div key={msg.id} className="flex flex-row items-end m-2 self-end">
              <div className="bg-[#01afa2] text-white rounded-full rounded-br-none mr-2 px-4 py-3">
                {msg.message}
              </div>
              <Avatar src="/chat-page/defaultAvatar.jpg" alt="avatar" size="sm"/>
            </div> :
            <div key={msg.id} className="flex flex-row items-end m-2">
              <Avatar src="/chat-page/defaultAvatar.jpg" alt="avatar" size="sm"/>
              <div className="bg-[#dce8ff] text-black rounded-full rounded-bl-none ml-2 px-4 py-3">
                {msg.message}
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
