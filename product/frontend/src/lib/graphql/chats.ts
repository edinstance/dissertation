import { graphql } from "@/gql";

export const CHAT_SUBSCRIPTION = graphql(`
  subscription ChatSubscription($conversationId: String!) {
    chatSubscription(conversationId: $conversationId) {
      conversationId
      chatId
      userId
      sender
      message
      createdAt
    }
  }
`);

export const SEND_CHAT_MESSAGE = graphql(`
  mutation createChat($conversationId: String!, $message: String!) {
    createChat(conversationId: $conversationId, message: $message) {
      success
      message
    }
  }
`);

export const GET_CURRENT_CONVERSATION = graphql(`
  query getCurrentConversation($conversationId: String!) {
    getCurrentConversation(conversationId: $conversationId) {
      chatId
      userId
      sender
      message
      createdAt
    }
  }
`);
