"use client";
import { useEffect, useState } from "react";

interface ItemCountdownProps {
  endingTime: string;
}

function ItemCountdown({ endingTime }: ItemCountdownProps) {
  const calculateTimeLeft = () => {
    const secondsLeft = Math.max(
      0,
      Math.floor((new Date(endingTime).getTime() - Date.now()) / 1000),
    );

    const days = Math.floor(secondsLeft / (24 * 60 * 60));
    const hours = Math.floor((secondsLeft % (24 * 60 * 60)) / (60 * 60));
    const minutes = Math.floor((secondsLeft % (60 * 60)) / 60);
    const seconds = Math.floor(secondsLeft % 60);

    if (days > 0) {
      return `${days} day${days > 1 ? "s" : ""} remaining`;
    }
    if (hours > 0) {
      return `${hours} hour${hours > 1 ? "s" : ""} remaining`;
    }
    if (minutes > 0) {
      return `${minutes} minute${minutes > 1 ? "s" : ""} remaining`;
    }
    return `${seconds} second${seconds > 1 ? "s" : ""} remaining`;
  };

  const [timeLeft, setTimeLeft] = useState(calculateTimeLeft());

  useEffect(() => {
    const intervalId = setInterval(() => {
      setTimeLeft(calculateTimeLeft());
    }, 1000);

    return () => clearInterval(intervalId);
  }, [endingTime]);

  return <span>{timeLeft}</span>;
}

export default ItemCountdown;
