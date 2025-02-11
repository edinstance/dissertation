import clsx from "clsx";

/**
 * Divider component for rendering a horizontal rule.
 *
 * This component renders an `<hr>` element with customizable styles.
 * It accepts additional class names and props to modify its appearance.
 *
 * @param props - The props for the component.
 * @param className - Additional class names to apply to the divider.
 * @returns The rendered divider component.
 */
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
