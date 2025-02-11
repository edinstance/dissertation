"use client";

import { unsubscribe } from "@/actions/unsubscribe";
import { Button } from "@/components/ui/Button";
import { useState } from "react";

/**
 * UnsubscribeButton component for canceling a user's subscription.
 *
 * This component renders a button that allows users to cancel their subscription.
 * It shows a loading state while the cancellation request is being processed.
 *
 * @param props - The props for the component.
 * @param userId - The ID of the user whose subscription is to be canceled.
 * @param className - Optional additional class names to apply to the button.
 * @returns The rendered UnsubscribeButton component.
 */
export function UnsubscribeButton({
  userId,
  className,
}: {
  userId: string;
  className?: string;
}) {
  const [isLoading, setIsLoading] = useState(false);

  async function handleCancel() {
    setIsLoading(true);
    try {
      const result = await unsubscribe({ id: userId });
      if (result.success) {
        window.location.reload();
      }
    } catch (error) {
      console.error("Error canceling subscription:", error);
    } finally {
      setIsLoading(false);
    }
  }

  return (
    <Button onClick={handleCancel} disabled={isLoading} className={className}>
      {isLoading ? "Canceling..." : "Cancel Subscription"}
    </Button>
  );
}
