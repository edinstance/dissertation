"use client";

import { unsubscribe } from "@/actions/unsubscribe";
import { useState } from "react";
import { Button } from "@/components/ui/Button";

export function UnsubscribeButton({ userId, className }: { userId: string, className?: string }) {
  const [isLoading, setIsLoading] = useState(false);

  async function handleCancel() {
    setIsLoading(true);
    try {
      const result = await unsubscribe({ id: userId });
      if (result.success ){
        window.location.reload();
      }
    } catch (error) {
      alert("An error occurred while canceling the subscription.");
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
