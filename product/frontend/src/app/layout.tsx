import "@/styles/globals.css";
import { Inter } from "next/font/google";

import Header from "@/components/Header";
import SessionProvider from "@/components/Providers/Auth";
import { ThemeProvider } from "@/components/Providers/ThemeProvider";
import { type Metadata } from "next";

const inter = Inter({ subsets: ["latin"], display: "swap" });

export const metadata: Metadata = {
  title: "Final Year Project",
  description: "Final Year Project",
};

/**
 * RootLayout component for the application.
 *
 * This component serves as the main layout for the application, wrapping
 * the content with necessary providers for theme management and authentication.
 * It includes a header and applies global styles.
 *
 * @param props - The props for the component.
 * @returns The rendered RootLayout component.
 */
export default async function RootLayout({
  children,
}: Readonly<{ children: React.ReactNode }>) {
  const LAUNCHED = process.env.LAUNCHED === "true";

  return (
    <html lang="en" suppressHydrationWarning>
      <body className={`${inter.className} bg-zinc-100 dark:bg-zinc-900`}>
        <ThemeProvider>
          <SessionProvider>
            <Header launched={LAUNCHED} />
            {children}
          </SessionProvider>
        </ThemeProvider>
      </body>
    </html>
  );
}
