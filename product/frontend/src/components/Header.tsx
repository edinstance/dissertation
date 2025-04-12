"use client";

import { Bars3Icon, XMarkIcon } from "@heroicons/react/24/outline";
import { AnimatePresence, motion } from "framer-motion";
import { signIn, useSession } from "next-auth/react";
import Link from "next/link";
import { useState } from "react";
import { ThemeToggle } from "./ThemeToggle";
import { Button } from "./ui/Button";
import { UserMenu } from "./Users/UserMenu";

/**
 * Header component for the application.
 *
 * This component renders the top navigation bar, including the application title,
 * theme toggle, user menu, and sign-in buttons. It also manages the mobile menu state.
 *
 * @param props - The props for the component.
 * @returns The rendered Header component.
 */
export default function Header({ launched }: { launched: boolean }) {
  const session = useSession();
  const [isMobileMenuOpen, setIsMobileMenuOpen] = useState(false);

  return (
    <>
      <header className="fixed left-0 right-0 top-0 z-50 flex h-16">
        <div className="flex w-full flex-row items-center justify-between bg-zinc-200 px-4 text-black dark:bg-zinc-950 dark:text-white md:px-16">
          <Link href="/" className="text-3xl font-bold">
            SubShop
          </Link>
          <div className="flex items-center gap-4">
            <ThemeToggle />

            {session.status === "authenticated" ? (
              <div className="flex flex-row space-x-4">
                {window.location.pathname === "/" && (
                  <Button href="/shop">Go to the shop</Button>
                )}
                <div className="flex flex-row space-x-4 rounded-lg text-zinc-700 hover:bg-zinc-300 dark:text-zinc-200 dark:hover:bg-zinc-600">
                  <UserMenu />
                </div>
              </div>
            ) : (
              launched && (
                <>
                  <div className="hidden flex-row items-center space-x-4 md:flex">
                    <Button onClick={() => signIn()}>Sign In</Button>
                    <Button href="/sign-up" variant="outline">
                      Sign up
                    </Button>
                  </div>
                  <button
                    className="rounded-lg p-2 hover:bg-zinc-300 dark:hover:bg-zinc-800 md:hidden"
                    onClick={() => setIsMobileMenuOpen(!isMobileMenuOpen)}
                  >
                    <MenuIcon isOpen={isMobileMenuOpen} />
                  </button>
                </>
              )
            )}
          </div>
        </div>
      </header>
      <MobileMenu
        isOpen={isMobileMenuOpen}
        onClose={() => setIsMobileMenuOpen(false)}
        launched={launched}
      />
    </>
  );
}

/**
 * MenuIcon component for displaying the mobile menu icon.
 *
 * This component renders either a close icon or a hamburger icon based on the
 * current state of the mobile menu.
 *
 * @param props - The props for the component.
 * @returns The rendered MenuIcon component.
 */
const MenuIcon = ({ isOpen }: { isOpen: boolean }) => (
  <AnimatePresence mode="wait" initial={false}>
    {isOpen ? (
      <motion.div
        key="close"
        initial={{ opacity: 0, rotate: -45 }}
        animate={{ opacity: 1, rotate: 0 }}
        exit={{ opacity: 0, rotate: 45 }}
        transition={{
          type: "spring",
          stiffness: 300,
          damping: 30,
        }}
      >
        <XMarkIcon className="h-6 w-6" />
      </motion.div>
    ) : (
      <motion.div
        key="open"
        initial={{ opacity: 0, rotate: 45 }}
        animate={{ opacity: 1, rotate: 0 }}
        exit={{ opacity: 0, rotate: -45 }}
        transition={{
          type: "spring",
          stiffness: 300,
          damping: 30,
        }}
      >
        <Bars3Icon className="h-6 w-6" />
      </motion.div>
    )}
  </AnimatePresence>
);

/**
 * MobileMenu component for displaying the mobile navigation menu.
 *
 * This component renders a menu with options for signing in and signing up
 * when the mobile menu is open.
 *
 * @param props - The props for the component.
 * @returns The rendered MobileMenu component or null if not open.
 */
function MobileMenu({
  isOpen,
  onClose,
  launched,
}: {
  isOpen: boolean;
  onClose: () => void;
  launched: boolean;
}) {
  const { status } = useSession();
  if (status !== "unauthenticated") return null;
  return (
    <>
      {isOpen && (
        <div
          className="fixed inset-0 z-30 bg-black/50 md:hidden"
          onClick={onClose}
        />
      )}
      <div
        className={`fixed right-0 top-16 z-40 w-full transform bg-zinc-200 transition-transform duration-300 ease-in-out dark:bg-zinc-950 md:hidden ${isOpen ? "translate-x-0" : "translate-x-full"}`}
      >
        <div className="flex flex-col space-y-4 p-4">
          {launched && (
            <>
              <Button onClick={() => signIn()}>Sign In</Button>
              <Button href="/sign-up" variant="outline">
                Sign up
              </Button>
            </>
          )}
        </div>
      </div>
    </>
  );
}
