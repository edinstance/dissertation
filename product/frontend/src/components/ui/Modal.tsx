import { Dialog, DialogPanel } from "@headlessui/react";
import React, { Dispatch, SetStateAction } from "react";

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
