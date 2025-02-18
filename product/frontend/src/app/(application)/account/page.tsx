"use client";
import ReportBugForm from "@/components/reports/ReportBugForm";
import { Button } from "@/components/ui/Button";
import Modal from "@/components/ui/Modal";
import DeleteUserInformation from "@/components/Users/DeleteUserInformation";
import { useState } from "react";

/**
 * Account component for managing user account settings.
 *
 * This component allows users to delete their account. It displays a button
 * that opens a confirmation modal when clicked. If the user confirms the
 * deletion, the account is deleted, and the user is signed out.
 *
 * @returns The rendered Account component.
 */
export default function Account() {
  const [open, setOpen] = useState(false);
  const [reportBugModalOpen, setReportBugModalOpen] = useState(false);

  return (
    <div className="space-y-4 pt-8">
      <Button
        onClick={() => {
          setReportBugModalOpen(true);
          setOpen(true);
        }}
      >
        Report an issue
      </Button>
      <Button
        onClick={() => {
          setReportBugModalOpen(false);
          setOpen(true);
        }}
        color="destructive"
      >
        Delete User
      </Button>
      <Modal open={open} setOpen={setOpen}>
        {reportBugModalOpen ? (
          <ReportBugForm setOpen={setOpen} />
        ) : (
          <DeleteUserInformation setOpen={setOpen} />
        )}
      </Modal>
    </div>
  );
}
