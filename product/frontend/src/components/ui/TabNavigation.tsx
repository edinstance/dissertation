"use client";

import { LayoutGroup, motion } from "framer-motion";
import Link from "next/link";
import { usePathname } from "next/navigation";

/**
 * TabNavigation component for rendering navigation links.
 *
 * This component displays a list of navigation links.
 * It highlights the active link with an animated underline using Framer Motion.
 *
 * @returns The rendered TabNavigation component.
 */
export default function TabNavigation({
  links,
}: {
  links: { label: string; link: string }[];
}) {
  const pathname = usePathname();

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
