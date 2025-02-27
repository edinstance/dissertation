"use client";

import { cn } from "@/lib/utils";
import * as React from "react";

// This is based off of shadcn's badge component

type BadgeProps = React.HTMLAttributes<HTMLDivElement> & {
  color?: keyof typeof styles.colors;
  variant?: keyof typeof styles.variants;
};

const styles = {
  base: [
    "inline-flex items-center rounded-md border px-2.5 py-0.5 text-xs font-semibold transition-colors",
    "focus:outline-none focus:ring-2 focus:ring-ring focus:ring-offset-2",
  ],
  variants: {
    solid: "border-transparent",
    outline: "border",
  },
  colors: {
    default: {
      solid: "bg-blue-500 text-white hover:bg-blue-600",
      outline: "border-blue-500 text-blue-700 hover:bg-blue-50",
    },
    secondary: {
      solid: "bg-gray-500 text-white hover:bg-gray-600",
      outline: "border-gray-500 text-gray-700 hover:bg-gray-50",
    },
    success: {
      solid: "bg-green-500 text-white hover:bg-green-600",
      outline: "border-green-500 text-green-700 hover:bg-green-50",
    },
    destructive: {
      solid: "bg-red-500 text-white hover:bg-red-600",
      outline: "border-red-500 text-red-700 hover:bg-red-50",
    },
  },
};

/**
 * Badge component that supports different styles based on color and variant.
 *
 * @param className - Additional class names to apply to the badge.
 * @param color - The color variant of the badge. Defaults to "default".
 * @param variant - The style variant of the badge. Defaults to "solid".
 * @param props - Additional HTML attributes for the badge.
 * @returns The rendered badge component.
 */
export function Badge({
  className,
  color = "default",
  variant = "solid",
  ...props
}: BadgeProps) {
  const classes = cn(
    styles.base,
    styles.variants[variant],
    styles.colors[color][variant],
    className,
  );

  return <div className={classes} {...props} />;
}
