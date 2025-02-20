import { Listbox, ListboxButton, ListboxOption, ListboxOptions } from "@headlessui/react";
import { ChevronUpDownIcon } from "@heroicons/react/24/outline";
import clsx from "clsx";
import { forwardRef } from "react";

type SelectOption = {
  value: string | number;
  label: string;
};

type SelectProps = {
  className?: string;
  options: SelectOption[];
  value: SelectOption | null;
  onChange: (value: SelectOption) => void;
  placeholder?: string;
  invalid?: boolean;
  label?: string;
};

export const Select = forwardRef<HTMLButtonElement, SelectProps>(
  function Select(
    {
      className,
      options,
      value,
      onChange,
      placeholder = "Select an option",
      invalid,
      label,
      ...props
    },
    ref,
  ) {
    return (
      <div className="w-full">
        {label && (
          <label className="block text-sm font-medium text-gray-700 dark:text-gray-300">
            {label}
          </label>
        )}
        <Listbox value={value} onChange={onChange}>
          <div className="relative mt-1">
            <ListboxButton
              ref={ref}
              className={clsx(
                "relative w-full cursor-pointer rounded-lg border p-2 text-left shadow-sm focus:outline-none",
                "flex items-center justify-between",
                "dark:bg-slate-800 dark:text-white",
                "focus:ring-2 focus:ring-blue-500 focus:ring-offset-2",
                invalid && "border-red-500 focus:ring-red-500",
                className,
              )}
              aria-labelledby={label ? label : undefined}
              aria-invalid={invalid || undefined}
              {...props}
            >
              <span
                className={clsx("block truncate", !value && "text-gray-400")}
              >
                {value?.label || placeholder}
              </span>
              <ChevronUpDownIcon className="h-5 w-5 text-gray-400" />
            </ListboxButton>

            <ListboxOptions className="absolute z-10 mt-1 max-h-60 w-full overflow-auto rounded-md bg-white py-1 shadow-lg dark:bg-slate-800">
              {options.length > 0 ? (
                options.map((option) => (
                  <ListboxOption
                    key={option.value}
                    value={option}
                    className={({ selected }) =>
                      clsx(
                        "relative cursor-pointer select-none py-2 pl-3 pr-9",
                        selected
                          ? "bg-blue-600 text-white"
                          : "text-gray-900 dark:text-gray-300",
                        "dark:hover:bg-blue-900 dark:hover:text-white",
                      )
                    }
                  >
                    {({ selected }) => (
                      <div className="flex items-center justify-between">
                        <span
                          className={clsx(
                            "block truncate",
                            selected && "font-semibold",
                          )}
                        >
                          {option.label}
                        </span>
                      </div>
                    )}
                  </ListboxOption>
                ))
              ) : (
                <div className="px-3 py-2 text-gray-500 dark:text-gray-400">
                  No options available
                </div>
              )}
            </ListboxOptions>
          </div>
        </Listbox>
      </div>
    );
  },
);
