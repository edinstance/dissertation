import type { CodegenConfig } from "@graphql-codegen/cli";

// Hard Coded as localhost as codegen is for development only
const config: CodegenConfig = {
  schema: "http://localhost:8080/graphql",
  documents: ["src/**/*.{ts,tsx}"],
  generates: {
    "./src/gql/": {
      preset: "client",
    },
  },
  hooks: { afterAllFileWrite: ["prettier --write"] },
};
export default config;
