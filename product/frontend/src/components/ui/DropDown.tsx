import { Menu, MenuButton, MenuItem, MenuItems } from "@headlessui/react";
import { ChevronDownIcon } from "@heroicons/react/24/outline";

export interface DropdownOption {
  label: string;
  onClick?: () => void;
  destructive?: boolean;
  className?: string;
}

export function DropDown({
  title,
  options,
  disabled,
}: {
  title: string;
  options: DropdownOption[];
  disabled?: boolean | false;
}) {
  return (
    <Menu as="div" className="relative inline-block text-left">
      <div>
        <MenuButton
          disabled={disabled}
          className="inline-flex w-full justify-center gap-x-1.5 rounded-md bg-white px-3 py-2 text-sm text-black shadow-md ring-1 ring-inset ring-gray-300 hover:bg-gray-50 dark:bg-black dark:text-white dark:ring-gray-700 dark:hover:bg-gray-400"
        >
          {title}
          <ChevronDownIcon
            aria-hidden="true"
            className="-mr-1 size-5 text-gray-400"
          />
        </MenuButton>
      </div>

      <MenuItems
        transition
        className="absolute right-0 z-10 mt-2 w-32 min-w-fit origin-top-right rounded-md bg-white shadow-lg ring-1 ring-black/5 dark:bg-black"
      >
        <div className="flex flex-col py-1">
          {options.map((option) => (
            <MenuItem
              key={option.label}
              as="button"
              onClick={option.onClick}
              className={`w-full rounded-md px-4 py-2 text-left text-sm text-black hover:bg-gray-100 hover:text-gray-900 dark:hover:bg-gray-500 dark:hover:text-gray-100 ${option.destructive ? "text-red-600 hover:text-red-600 dark:text-red-600 dark:hover:text-red-600" : "dark:text-white"} `}
            >
              {option.label}
            </MenuItem>
          ))}
        </div>
      </MenuItems>
    </Menu>
  );
}

export default DropDown;
