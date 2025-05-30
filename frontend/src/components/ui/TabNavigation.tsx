"use client";

import { LayoutGroup, motion } from "framer-motion";
import Link from "next/link";
import { usePathname } from "next/navigation";
import { useEffect, useState } from "react";

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
  getLinks,
}: {
  links?: { label: string; link: string }[];
  getLinks?: () => { label: string; link: string }[];
}) {
  const pathname = usePathname();

  const [navigationLinks, setNavigationLinks] = useState<
    { label: string; link: string }[]
  >([]);

  useEffect(() => {
    const dynamicLinks = getLinks ? getLinks() : links || [];
    setNavigationLinks(dynamicLinks);
  }, [getLinks, links]);

  return (
    <nav className="relative rounded-lg bg-gray-200 p-4 dark:bg-gray-800">
      <LayoutGroup>
        <ul className="flex space-x-4">
          {navigationLinks &&
            navigationLinks.map(({ link, label }) => (
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
