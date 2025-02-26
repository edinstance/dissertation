import { cn } from "@/lib/utils";

/**
 * Section component for layout purposes.
 *
 * This component renders a `div` element with predefined styles for layout,
 * allowing additional classes to be passed in via the `className` prop.
 *
 * @param props - Any other props to be passed to the `div` element.
 * @param className - Additional class names to apply to the section.
 * @returns The rendered section component.
 */
export function Section({
  className,
  ...props
}: React.ComponentPropsWithoutRef<"div">) {
  return (
    <div
      className={cn("mx-auto w-full max-w-7xl px-4 sm:px-6 lg:px-8", className)}
      {...props}
    />
  );
}
