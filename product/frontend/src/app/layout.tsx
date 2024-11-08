import "@/styles/globals.css";
import { Inter } from "next/font/google";

import Header from "@/components/Header";
import { auth } from "@/server/auth";
import { type Metadata } from "next";
import { SessionProvider } from "next-auth/react";

const inter = Inter({ subsets: ["latin"] });

export const metadata: Metadata = {
  title: "Final Year Project",
  description: "Final Year Project",
};

export default async function RootLayout({
  children,
}: Readonly<{ children: React.ReactNode }>) {
  const session = await auth();

  return (
    <html lang="en">
      <body className={`${inter.className} bg-zinc-100 dark:bg-zinc-900`}>
        <SessionProvider session={session}>
          <Header />
          {children}
        </SessionProvider>
      </body>
    </html>
  );
}
