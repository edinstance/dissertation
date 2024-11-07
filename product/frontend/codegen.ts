import type { CodegenConfig } from "@graphql-codegen/cli";
import { loadEnvConfig } from "@next/env";

// This loads the environment variables from the .env file
loadEnvConfig(process.cwd());
// It then sets the apiKey variable to the value of the API_KEY environment variable and false if not
const apiKey = process.env.API_KEY ?? false;

// If the apiKey variable is false, it throws an error
if (!apiKey) {
  throw new Error("API_KEY is not set");
}

// Hard Coded as localhost as codegen is for development only
const config: CodegenConfig = {
  schema: {
    "http://localhost:8080/graphql": {
      headers: {
        "X-API-KEY": apiKey,
      },
    },
  },
  documents: ["src/**/*.{ts,tsx}"],
  generates: {
    "./src/gql/": {
      preset: "client",
    },
  },
  hooks: { afterAllFileWrite: ["prettier --write"] },
};
export default config;
