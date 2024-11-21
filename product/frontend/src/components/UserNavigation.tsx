"use client";

import { LayoutGroup, motion } from "framer-motion";
import Link from "next/link";
import { usePathname } from "next/navigation";

export default function UserNavigation() {
  const pathname = usePathname();

  const links = [
    { label: "Account", link: "/account" },
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
