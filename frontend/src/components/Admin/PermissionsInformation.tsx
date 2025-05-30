"use client";

import { GET_ALL_PERMISSIONS } from "@/lib/graphql/admin-permissions";
import { useQuery } from "@apollo/client";
import { useState } from "react";
import { Button } from "../ui/Button";
import Disclosure from "../ui/Disclosure";
import LoadingSpinner from "../ui/LoadingSpinner";
import Modal from "../ui/Modal";
import CreatePermissionModalContent from "./CreatePermissionModalContent";

function PermissionsInformation() {
  const { data, loading, error } = useQuery(GET_ALL_PERMISSIONS);
  const permissions = data?.getAllPermissions ?? [];

  const [isModalOpen, setIsModalOpen] = useState(false);

  if (loading) {
    return <LoadingSpinner />;
  }

  if (error) {
    return <div>Error: {error.message}</div>;
  }

  if (permissions.length < 0 || !permissions) {
    return (
      <div className="flex flex-col items-center justify-center space-y-4 pt-16 text-xl">
        <p>No permissions found.</p>
        <Button>Create Permission</Button>
      </div>
    );
  }

  return (
    <div>
      <div className="flex flex-row items-center justify-between pb-8">
        <h1 className="text-xl">Permissions</h1>
        <Button
          onClick={() => {
            setIsModalOpen(true);
          }}
        >
          Create Permission
        </Button>
      </div>
      <div className="flex max-h-screen flex-col items-center justify-center space-y-4 overflow-y-scroll">
        {permissions.map((permission) => (
          <Disclosure
            title={permission?.description ?? "Permission"}
            key={permission?.id}
          >
            <div className="flex flex-col items-start justify-start space-y-4">
              <div>
                <p className="text-lg font-semibold">Resource</p>
                <p className="pl-4">- {permission?.resource?.description}</p>
              </div>
              <div>
                <p className="text-lg font-semibold">Action</p>
                <p className="pl-4">- {permission?.action?.description}</p>
              </div>
            </div>
          </Disclosure>
        ))}
      </div>
      <Modal open={isModalOpen} setOpen={setIsModalOpen}>
        <CreatePermissionModalContent setModalOpen={setIsModalOpen} />
      </Modal>
    </div>
  );
}

export default PermissionsInformation;
