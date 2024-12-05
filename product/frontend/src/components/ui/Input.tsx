import {Input as HeadlessInput, InputProps} from "@headlessui/react";
import clsx from "clsx";
import { forwardRef } from "react";

export const Input = forwardRef(function Input(
  {
    className,
    ...props
  }: {
    className?: string;
    type?: "email" | "password" | "tel" | "text";
  } & Omit<InputProps, "className">,
  ref: React.ForwardedRef<HTMLInputElement>,
) {
  return (
    <HeadlessInput
      ref={ref}
      className={clsx(
        className,
        "block max-h-8 w-full rounded-lg border p-1 shadow-lg data-[invalid]:border-red-500 data-[invalid]:data-[hover]:border-red-500 data-[invalid]:data-[focus]:ring-red-500 data-[invalid]:data-[focus]:ring-offset-red-500 dark:bg-slate-800",
      )}
      {...props}
    />
  );
});
