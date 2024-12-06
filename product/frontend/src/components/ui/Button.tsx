import { Button as HeadlessButton } from "@headlessui/react";
import clsx from "clsx";
import Link from "next/link";

type ButtonProps =
  | (React.ComponentPropsWithoutRef<typeof Link> & {
      color?: keyof typeof styles.colors;
    })
  | (React.ComponentPropsWithoutRef<"button"> & {
      href?: undefined;
      color?: keyof typeof styles.colors;
    });

const styles = {
  base: [
    "flex items-center justify-center rounded-lg border border-transparent px-4 py-2 text-sm " +
      "focus:outline-none focus:ring-2 focus:ring-offset-2 w-full" +
      "data-[disabled]:bg-red-500 data-[disabled]:opacity-50",
  ],
  colors: {
    blue: "bg-blue-600 text-white shadow-sm hover:bg-blue-500 focus:ring-blue-500",
  },
};

export function Button({ className, color, ...props }: ButtonProps) {
  const classes = clsx(className, styles.base, styles.colors[color ?? "blue"]);

  return typeof props.href === "undefined" ? (
    <HeadlessButton className={classes} {...props} />
  ) : (
    <Link className={classes} {...props} />
  );
}
