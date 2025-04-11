"use client";
import { Chat } from "@/gql/graphql";
import {
  GET_CURRENT_CONVERSATION,
  SEND_CHAT_MESSAGE,
} from "@/lib/graphql/chats";
import { useMutation, useQuery } from "@apollo/client";
import {
  ChatBubbleLeftRightIcon,
  XMarkIcon,
} from "@heroicons/react/24/outline";
import { useCallback, useEffect, useRef, useState } from "react";
import { v4 as uuidv4 } from "uuid";
import { Button } from "../ui/Button";
import { Input } from "../ui/Input";

const CHAT_STORAGE_KEY = "conversation_id";
const TIMEOUT_MINUTES = 10;

// Types for localStorage data
interface StoredChatData {
  conversationId: string;
  lastInteraction: number; // timestamp
}

/**
 * ChatWindow component for displaying a chat interface.
 *
 * This component handles the chat functionality, including sending and receiving messages,
 * managing the conversation state, and rendering the chat UI.
 *
 * @returns The rendered ChatWindow component.
 */
function ChatWindow() {
  const [isOpen, setIsOpen] = useState(false);
  const [newMessage, setNewMessage] = useState("");
  const [conversation, setConversation] = useState<Chat[]>([]);
  const [conversationId, setConversationId] = useState<string | null>(null);

  const chatContainerRef = useRef<HTMLDivElement | null>(null);

  // Store conversation data in localStorage
  const storeConversationData = useCallback((id: string) => {
    if (typeof window === "undefined") return;

    const data: StoredChatData = {
      conversationId: id,
      lastInteraction: Date.now(),
    };
    localStorage.setItem(CHAT_STORAGE_KEY, JSON.stringify(data));
  }, []);

  useEffect(() => {
    // Initialize conversation ID from localStorage or create a new one
    setConversationId(getStoredConversationId());
  }, [getStoredConversationId]);

  // Get the stored conversation ID from localStorage
  const getStoredConversationId = useCallback((): string => {
    if (typeof window === "undefined") return "";

    const storedData = localStorage.getItem(CHAT_STORAGE_KEY);
    if (!storedData) {
      const newId = uuidv4();
      storeConversationData(newId);
      return newId;
    }

    try {
      const data: StoredChatData = JSON.parse(storedData);
      const now = Date.now();
      const elapsedMinutes = (now - data.lastInteraction) / (1000 * 60);

      if (elapsedMinutes < TIMEOUT_MINUTES) {
        return data.conversationId;
      } else {
        localStorage.removeItem(CHAT_STORAGE_KEY);
        const newId = uuidv4();
        storeConversationData(newId);
        return newId;
      }
    } catch (e) {
      console.error("Error parsing stored conversation data", e);
      localStorage.removeItem(CHAT_STORAGE_KEY);
      const newId = uuidv4();
      storeConversationData(newId);
      return newId;
    }
  }, [storeConversationData]);

  // Function to update the last interaction time
  const updateLastInteraction = useCallback(() => {
    if (typeof window === "undefined") return;

    const storedData = localStorage.getItem(CHAT_STORAGE_KEY);
    if (storedData) {
      try {
        const data: StoredChatData = JSON.parse(storedData);
        data.lastInteraction = Date.now();
        localStorage.setItem(CHAT_STORAGE_KEY, JSON.stringify(data));
      } catch (e) {
        console.error("Error updating last interaction time", e);
      }
    }
  }, []);

  // Set up mutation for sending messages
  const [createChatMutation] = useMutation(SEND_CHAT_MESSAGE, {
    onCompleted: (data) => {
      if (data?.createChat) {
        const { chat, response } = data.createChat;

        // Update the conversation state with the new message
        setConversation((prev) => [
          ...prev,
          ...[chat, response].filter(
            (msg): msg is Chat => msg !== null && msg !== undefined,
          ),
        ]);
      }
    },
  });

  // Query for existing conversation messages
  useQuery(GET_CURRENT_CONVERSATION, {
    variables: { conversationId: conversationId || "" },
    skip: !conversationId, // Skip the query until we have a valid conversationId
    onCompleted: (data) => {
      if (data?.getCurrentConversation) {
        setConversation(
          data.getCurrentConversation.filter(
            (message): message is Chat => message !== null,
          ),
        );
      }
    },
    onError: (error) => {
      console.error("Error fetching conversation:", error);
    },
  });

  const handleSendMessage = useCallback(() => {
    if (newMessage.trim() !== "" && conversationId) {
      createChatMutation({
        variables: {
          conversationId: conversationId,
          message: newMessage,
        },
      });

      setNewMessage("");
      updateLastInteraction();
    }
  }, [newMessage, createChatMutation, conversationId, updateLastInteraction]);

  // Auto-scroll to bottom when new messages arrive
  useEffect(() => {
    if (chatContainerRef.current) {
      chatContainerRef.current.scrollTop =
        chatContainerRef.current.scrollHeight;
    }
  }, [conversation]);

  const toggleChat = useCallback(() => {
    setIsOpen((prev) => !prev);
  }, []);

  return (
    <div>
      <Button
        onClick={toggleChat}
        className="fixed bottom-0 right-0 z-50 mb-8 mr-8"
      >
        <ChatBubbleLeftRightIcon className="h-6 w-6" />
      </Button>
      <div
        className={`fixed bottom-0 right-0 z-50 mb-8 mr-8 flex h-[80vh] w-96 flex-col rounded-md border bg-white shadow-lg ${
          isOpen ? "" : "hidden"
        }`}
      >
        <>
          <div className="flex items-center justify-between border-b p-4">
            <h2 className="text-lg font-semibold">Chat with Subshop</h2>
            <Button variant="outline" onClick={() => setIsOpen(false)}>
              <XMarkIcon className="h-5 w-5" />
            </Button>
          </div>

          <div
            ref={chatContainerRef}
            className="flex-1 space-y-3 overflow-y-scroll p-4"
          >
            {conversation.map((message, index) => (
              <div
                key={message.chatId || index}
                className={`max-w-[70%] rounded-lg p-3 text-sm ${
                  message.sender?.substring(0, 4) === "User"
                    ? "ml-auto bg-blue-100"
                    : "mr-auto bg-gray-100"
                }`}
              >
                {message.message}
              </div>
            ))}
          </div>

          <div className="border-t p-4">
            <div className="flex items-center space-x-2">
              <Input
                type="text"
                className="flex-grow"
                placeholder="Type your message..."
                value={newMessage}
                onChange={(e) => setNewMessage(e.target.value)}
                onKeyDown={(e) => {
                  if (e.key === "Enter" && newMessage.trim() !== "") {
                    handleSendMessage();
                  }
                }}
              />
              <Button
                onClick={handleSendMessage}
                disabled={newMessage.length === 0}
              >
                Send
              </Button>
            </div>
          </div>
        </>
      </div>
    </div>
  );
}

export default ChatWindow;
