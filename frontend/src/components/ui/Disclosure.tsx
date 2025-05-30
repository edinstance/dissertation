import {
  DisclosureButton,
  DisclosurePanel,
  Disclosure as HeadlessDisclosure,
} from "@headlessui/react";
import ChevronDownIcon from "@heroicons/react/24/outline/ChevronDownIcon";

export default function Disclosure({
  title,
  children,
}: {
  title: string;
  children: React.ReactNode;
}) {
  return (
    <div className="w-full max-w-lg divide-y rounded-xl border border-gray-200 bg-white shadow-sm dark:divide-gray-700 dark:border-gray-700 dark:bg-gray-800">
      <HeadlessDisclosure as="div" className="p-6" defaultOpen={false}>
        <DisclosureButton className="group flex w-full items-center justify-between">
          <span className="text-xl text-gray-900 group-data-[hover]:text-gray-700 dark:text-white dark:group-data-[hover]:text-gray-300">
            {title}
          </span>
          <ChevronDownIcon className="h-6 w-6 group-data-[open]:rotate-180" />
        </DisclosureButton>
        <DisclosurePanel className="mt-2 text-gray-600 dark:text-gray-400">
          {children}
        </DisclosurePanel>
      </HeadlessDisclosure>
    </div>
  );
}
