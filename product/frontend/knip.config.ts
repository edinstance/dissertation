import type { KnipConfig } from "knip";

const config = {
  project: ["src/**/*!", "!src/gql/*", "!cypress/**/*"],
  ignore: ["src/server/auth/index.ts"],
  //   Ignore Next auth config as it is still in beta so there are some issues.
} satisfies KnipConfig;

export default config;
