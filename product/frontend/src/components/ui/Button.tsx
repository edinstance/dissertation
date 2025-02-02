"use client";

import { Button as HeadlessButton } from "@headlessui/react";
import clsx from "clsx";
import Link from "next/link";

type ButtonProps =
  | (React.ComponentPropsWithoutRef<typeof Link> & {
      color?: keyof typeof styles.colors;
      variant?: keyof typeof styles.variants;
    })
  | (React.ComponentPropsWithoutRef<"button"> & {
      href?: undefined;
      color?: keyof typeof styles.colors;
      variant?: keyof typeof styles.variants;
    });

const styles = {
  base: [
    "flex items-center justify-center rounded-lg border border-transparent px-4 py-2 text-sm " +
      "focus:outline-none focus:ring-2 focus:ring-offset-2 w-full" +
      "data-[disabled]:bg-red-500 data-[disabled]:opacity-50",
  ],
  variants: {
    solid: "border border-transparent",
    outline: "border outline",
  },
  colors: {
    blue: {
      solid: "bg-blue-600 text-white shadow-sm hover:bg-blue-500 focus:ring-blue-500",
      outline:
        "border-blue-600 text-blue-600 hover:bg-blue-50 dark:hover:bg-blue-950 focus:ring-blue-500",
    },
    destructive: {
      solid: "bg-red-600 text-white shadow-sm hover:bg-red-500 focus:ring-red-500",
      outline: "border-red-600 text-red-600 hover:bg-red-50 focus:ring-red-500",
    },
  },
};

export function Button({
  className,
  color = "blue",
  variant = "solid",
  ...props
}: ButtonProps) {
  const classes = clsx(
    className,
    styles.base,
    styles.variants[variant],
    styles.colors[color][variant]
  );

  return typeof props.href === "undefined" ? (
    <HeadlessButton className={classes} {...props} />
  ) : (
    <Link className={classes} {...props} />
  );
}
