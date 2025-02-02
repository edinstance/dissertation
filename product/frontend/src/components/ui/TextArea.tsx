import { Textarea as HeadlessText, TextareaProps } from "@headlessui/react";
import clsx from "clsx";
import { forwardRef } from "react";

export const TextArea = forwardRef(function Input(
  {
    className,
    ...props
  }: {
    className?: string;
  } & Omit<TextareaProps, "className">,
  ref: React.ForwardedRef<HTMLTextAreaElement>,
) {
  return (
    <HeadlessText
      ref={ref}
      className={clsx(
        "block min-h-[100px] w-full rounded-lg border p-1 shadow-lg",
        "data-[invalid]:border-red-500",
        "data-[invalid]:data-[hover]:border-red-500",
        "data-[invalid]:data-[focus]:ring-red-500",
        "data-[invalid]:data-[focus]:ring-offset-red-500",
        "dark:bg-slate-800",
        className,
      )}
      {...props}
    />
  );
});
