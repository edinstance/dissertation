import { graphql } from "@/gql";

export const GET_BIDS_BY_ITEM = graphql(`
  query getItemBidsById($itemId: String!) {
    getItemBidsById(itemId: $itemId) {
      bidId
      itemId
      userId
      amount
      createdAt
    }
  }
`);

export const SUBMIT_BID_MUTATION = graphql(`
  mutation submitBid($bid: SubmitBidInput!) {
    submitBid(bid: $bid) {
      success
      message
    }
  }
`);
