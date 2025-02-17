import { graphql } from "@/gql";

export const REPORT_BUG_MUTATION = graphql(`
  mutation ReportBug($title: String!, $description: String!) {
    reportBug(title: $title, description: $description) {
      success
      message
    }
  }
`);
