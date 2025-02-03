"use client";
import deleteUser from "@/actions/delete-user";
import { Button } from "@/components/ui/Button";
import Modal from "@/components/ui/Modal";
import { DELETE_USER_MUTATION } from "@/lib/graphql/users";
import { useMutation } from "@apollo/client";
import { DialogTitle } from "@headlessui/react";
import { signOut, useSession } from "next-auth/react";
import { redirect } from "next/navigation";
import { useState } from "react";

export default function Account() {
  const session = useSession();

  const [deleteUserMutation] = useMutation(DELETE_USER_MUTATION);
  const handleDeleteUser = async () => {
    await deleteUserMutation();
    signOut({ redirect: false });
    await deleteUser({
      userId: session.data?.user?.id,
      email: session.data?.user?.email,
    });

    redirect("/");
  };

  const [open, setOpen] = useState(false);

  return (
    <div className="pt-8">
      <Button
        onClick={() => {
          setOpen(true);
        }}
        color="destructive"
      >
        Delete User
      </Button>
      <Modal open={open} setOpen={setOpen}>
        <DialogTitle className="text-lg text-black dark:text-white">
          Are you sure?
        </DialogTitle>
        <p className="text-md text-black dark:text-white">
          This action cannot be undone. Are you sure you want to delete your
          account?
        </p>
        <div className="flex justify-end space-x-4">
          <Button onClick={() => setOpen(false)} color="blue">
            Cancel
          </Button>
          <Button onClick={handleDeleteUser} color="destructive">
            Confirm
          </Button>
        </div>
      </Modal>
    </div>
  );
}
