import "@/styles/globals.css";
import { Inter } from "next/font/google";

import { ApolloWrapper } from "@/components/apollo";
import { type Metadata } from "next";

const inter = Inter({ subsets: ["latin"] });

export const metadata: Metadata = {
  title: "Final Year Project",
  description: "Final Year Project",
};

const BACKEND_GRAPHQL_ENDPOINT = process.env.BACKEND_GRAPHQL_ENDPOINT ?? "";

export default function RootLayout({
  children,
}: Readonly<{ children: React.ReactNode }>) {
  return (
    <html lang="en">
      <body className={`${inter.className}`}>
        <ApolloWrapper link={BACKEND_GRAPHQL_ENDPOINT}>
          {children}
        </ApolloWrapper>
      </body>
    </html>
  );
}
