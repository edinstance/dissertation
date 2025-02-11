"use client";

import { LayoutGroup, motion } from "framer-motion";
import Link from "next/link";
import { usePathname } from "next/navigation";

/**
 * UserNavigation component for rendering navigation links for user account management.
 *
 * This component displays a list of navigation links related to user account features.
 * It highlights the active link with an animated underline using Framer Motion.
 *
 * @returns The rendered UserNavigation component.
 */
export default function UserNavigation() {
  const pathname = usePathname();

  const links = [
    { label: "Account", link: "/account" },
    { label: "Details", link: "/account/details" },
    { label: "Billing", link: "/account/billing" },
  ];

  return (
    <nav className="relative">
      <LayoutGroup>
        <ul className="flex space-x-4">
          {links.map(({ link, label }) => (
            <li key={link} className="relative">
              <Link
                href={link}
                className="text-black hover:text-gray-300 dark:text-white"
              >
                {label}
              </Link>
              {pathname === link && (
                <motion.div
                  layoutId="underline"
                  className="absolute bottom-0 left-0 right-0 h-0.5 bg-blue-500"
                />
              )}
            </li>
          ))}
        </ul>
      </LayoutGroup>
    </nav>
  );
}
