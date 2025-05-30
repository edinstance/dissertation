import { graphql } from "@/gql";

export const GET_ACTIONS_VALUES = graphql(`
  query GetActionsEnumValues {
    Actions: __type(name: "Actions") {
      name
      enumValues(includeDeprecated: false) {
        name
      }
    }
  }
`);

export const GET_RESOURCES_VALUES = graphql(`
  query GetResoucesEnumValues {
    Resources: __type(name: "Resources") {
      name
      enumValues(includeDeprecated: false) {
        name
      }
    }
  }
`);
