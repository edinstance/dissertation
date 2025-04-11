"use client";
import { Chat } from "@/gql/graphql";
import {
  CHAT_SUBSCRIPTION,
  GET_CURRENT_CONVERSATION,
  SEND_CHAT_MESSAGE,
} from "@/lib/graphql/chats";
import { useMutation, useQuery, useSubscription } from "@apollo/client";
import {
  ChatBubbleLeftRightIcon,
  XMarkIcon,
} from "@heroicons/react/24/outline";
import { useCallback, useEffect, useRef, useState } from "react";
import { Button } from "../ui/Button";
import { Input } from "../ui/Input";
import LoadingSpinner from "../ui/LoadingSpinner";

/**
 * ChatWindow component for displaying a chat interface.
 *
 * This component handles the chat functionality, including sending and receiving messages,
 * managing the conversation state, and rendering the chat UI.
 *
 * @returns The rendered ChatWindow component.
 */
function ChatWindow({ conversationId }: { conversationId: string }) {
  const [isOpen, setIsOpen] = useState(false);
  const [newMessage, setNewMessage] = useState("");
  const [conversation, setConversation] = useState<Chat[]>([]);
  const chatContainerRef = useRef<HTMLDivElement | null>(null);

  // Query for existing conversation messages
  const { loading: conversationLoading } = useQuery(GET_CURRENT_CONVERSATION, {
    variables: { conversationId: conversationId },
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

  // Subscribe to new messages
  useSubscription(CHAT_SUBSCRIPTION, {
    fetchPolicy: "no-cache",
    variables: { conversationId: conversationId },
    onData: ({ data }) => {
      console.log("Subscription data:", data);
      const newMsg = data?.data?.chatSubscription;
      if (newMsg) {
        setConversation((prev) => [...prev, newMsg]);
      }
    },
    onError: (error) => {
      console.error("Subscription error:", error);
    },
  });

  const [createChatMutation] = useMutation(SEND_CHAT_MESSAGE);

  const handleSendMessage = useCallback(() => {
    if (newMessage.trim() !== "") {
      createChatMutation({
        variables: {
          conversationId: conversationId,
          message: newMessage,
        },
      }).catch((e) => console.log(e));
      setNewMessage("");
    }
  }, [newMessage, createChatMutation, conversationId]);

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
        {conversationLoading ? (
          <LoadingSpinner />
        ) : (
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
        )}
      </div>
    </div>
  );
}

export default ChatWindow;
