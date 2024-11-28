import clsx from "clsx";

export default function Divivder({
  className,
  ...props
}: React.ComponentPropsWithoutRef<"hr">) {
  return (
    <hr
      {...props}
      className={clsx(
        className,
        "w-full border-t border-slate-600 dark:border-slate-400",
      )}
    />
  );
}
