import { graphql } from "@/gql";

export const SEND_CHAT_MESSAGE = graphql(`
  mutation createChat($conversationId: String!, $message: String!) {
    createChat(conversationId: $conversationId, message: $message) {
      chat {
        chatId
        userId
        sender
        message
        createdAt
      }
      response {
        chatId
        userId
        sender
        message
        createdAt
      }
    }
  }
`);

export const CLEAR_CONVERSATION_MUTATION = graphql(`
  mutation clearCurrentConversation($conversationId: String!) {
    clearCurrentConversation(conversationId: $conversationId) {
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
