import type { KnipConfig } from "knip";

const config = {
  project: ["src/**/*!", "!src/gql/*", "!cypress/**/*"],
} satisfies KnipConfig;

export default config;
