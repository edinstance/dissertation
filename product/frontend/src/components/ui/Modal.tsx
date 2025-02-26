import { Dialog, DialogPanel } from "@headlessui/react";
import React, { Dispatch, SetStateAction } from "react";

/**
 * Modal component for displaying content in a dialog overlay.
 *
 * This component uses Headless UI's Dialog to create a modal that can be opened
 * and closed based on the `open` state. It accepts children to render inside the modal.
 *
 * @param props - The props for the component.
 * @param open - Indicates whether the modal is open or closed.
 * @param setOpen - Function to set the open state of the modal.
 * @param children - The content to be displayed inside the modal.
 * @returns The rendered modal component.
 */
export default function Modal({
  open,
  setOpen,
  children,
}: {
  open: boolean;
  setOpen: Dispatch<SetStateAction<boolean>>;
  children: React.ReactNode;
}) {
  return (
    <Dialog
      open={open}
      onClose={() => setOpen(false)}
      className="relative z-50"
    >
      <div className="fixed inset-0 backdrop-blur-sm" />
      <div className="fixed inset-0 flex w-screen items-center justify-center p-4">
        <DialogPanel className="max-w-lg space-y-4 rounded-lg bg-zinc-200 p-12 shadow-lg dark:bg-zinc-800">
          {children}
        </DialogPanel>
      </div>
    </Dialog>
  );
}
