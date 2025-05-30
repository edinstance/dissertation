import {
  ApolloGateway,
  IntrospectAndCompose,
  RemoteGraphQLDataSource,
} from "@apollo/gateway";
import { ApolloServer } from "apollo-server-express";
import dotenv from "dotenv";
import express from "express";

dotenv.config();

const apiKey = process.env.API_KEY || "";
const originUrl = process.env.ORIGIN_URL || "";
const backendSubgraphUrl = process.env.BACKEND_SUBGRAPH_URL || "";

class AuthenticatedDataSource extends RemoteGraphQLDataSource {
  willSendRequest({ request, context }: { request: any; context: any }) {
    console.log(
      "Sending request to subgraph with headers:",
      request.http.headers,
    );

    request.http.headers.set("Authorization", context.authHeader);
    request.http.headers.set("x-api-key", apiKey);
    request.http.headers.set("x-forwarded-for", context.forwardedFor);
    request.http.headers.set("user-agent", context.userAgent);

    console.log("Modified headers:", request.http.headers);
  }
}

const gateway = new ApolloGateway({
  supergraphSdl: new IntrospectAndCompose({
    subgraphs: [
      {
        name: "backend",
        url: backendSubgraphUrl,
      },
    ],
  }),
  introspectionHeaders: {
    "x-api-key": apiKey,
  },
  buildService({ url }) {
    return new AuthenticatedDataSource({ url });
  },
});

async function startServer() {
  const app = express();

  app.get("/health", (req, res) => {
    res.status(200).send("OK");
  });

  const server = new ApolloServer({
    gateway,
    context: ({ req }) => {
      const authHeader = req.headers["authorization"] || null;
      const apiKey = req.headers["x-api-key"] || null;
      const forwardedFor = req.headers["x-forwarded-for"] || null;
      const userAgent = req.headers["user-agent"] || null;

      return {
        authHeader,
        apiKey,
        forwardedFor,
        userAgent,
      };
    },
  });

  await server.start();
  server.applyMiddleware({
    app,
    cors: {
      origin: [originUrl, "https://studio.apollographql.com"],
      credentials: true,
      allowedHeaders: [
        "x-api-key",
        "x-forwarded-for",
        "user-agent",
        "Authorization",
        "Content-Type",
        "access-control-allow-origin",
      ],
      exposedHeaders: [
        "x-api-key",
        "x-forwarded-for",
        "user-agent",
        "access-control-allow-origin",
      ],
    },
  });
  const PORT = process.env.PORT || 4000;

  app.listen(PORT, () => {
    console.log(`Server is running at http://localhost:${PORT}`);
  });
}

startServer().catch((err) => {
  console.error("Server failed to start:", err);
});
