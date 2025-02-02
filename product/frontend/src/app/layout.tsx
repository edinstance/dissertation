import "@/styles/globals.css";
import { Inter } from "next/font/google";

import Header from "@/components/Header";
import SessionProvider from "@/components/Providers/Auth";
import { type Metadata } from "next";

const inter = Inter({ subsets: ["latin"], display: "swap" });

export const metadata: Metadata = {
  title: "Final Year Project",
  description: "Final Year Project",
};

export default async function RootLayout({
  children,
}: Readonly<{ children: React.ReactNode }>) {
  const LAUNCHED = process.env.LAUNCHED === "true";

  return (
    <html lang="en">
      <body className={`${inter.className} bg-zinc-100 dark:bg-zinc-900`}>
        <SessionProvider>
          <Header launched={LAUNCHED} />
          {children}
        </SessionProvider>
      </body>
    </html>
  );
}
