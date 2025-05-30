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
      "w-full" +
      "data-[disabled]:bg-red-500 data-[disabled]:opacity-50",
  ],
  variants: {
    solid: "border border-transparent outline-none focus:outline-none",
    outline: "border outline outline-2",
  },
  colors: {
    blue: {
      solid:
        "bg-blue-600 text-white shadow-sm hover:bg-blue-500 focus:ring-blue-500",
      outline:
        "border-blue-600 text-blue-600 hover:bg-blue-50 dark:hover:bg-blue-950 focus:ring-blue-500",
    },
    destructive: {
      solid:
        "bg-red-600 text-white shadow-sm hover:bg-red-500 focus:ring-red-500",
      outline: "border-red-600 text-red-600 hover:bg-red-50 focus:ring-red-500",
    },
  },
};

/**
 * Button component that can render as a link or a button.
 *
 * This component supports different styles based on the provided color and variant.
 * It can be used as a standard button or as a link, depending on the props passed.
 *
 * @param props - The props for the button component.
 * @param className - Additional class names to apply to the button.
 * @param color - The color variant of the button. Defaults to "blue".
 * @param variant - The style variant of the button. Defaults to "solid".
 * @returns The rendered button component.
 */
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
    styles.colors[color][variant],
  );

  return typeof props.href === "undefined" ? (
    <HeadlessButton className={classes} {...props} />
  ) : (
    <Link className={classes} {...props} />
  );
}
