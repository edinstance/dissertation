"use client";

import { useEffect, useState } from "react";

function BidCountdownTimer({ endTime }: { endTime: Date }) {
  const [timeLeft, setTimeLeft] = useState<{
    days: number;
    hours: number;
    minutes: number;
    seconds: number;
  }>({ days: 0, hours: 0, minutes: 0, seconds: 0 });

  const [isEnded, setIsEnded] = useState(false);

  useEffect(() => {
    const calculateTimeLeft = () => {
      const difference = endTime.getTime() - new Date().getTime();

      if (difference <= 0) {
        setIsEnded(true);
        return { days: 0, hours: 0, minutes: 0, seconds: 0 };
      }

      return {
        days: Math.floor(difference / (1000 * 60 * 60 * 24)),
        hours: Math.floor((difference / (1000 * 60 * 60)) % 24),
        minutes: Math.floor((difference / 1000 / 60) % 60),
        seconds: Math.floor((difference / 1000) % 60),
      };
    };

    setTimeLeft(calculateTimeLeft());

    const timer = setInterval(() => {
      setTimeLeft(calculateTimeLeft());
    }, 1000);

    return () => clearInterval(timer);
  }, [endTime]);

  if (isEnded) {
    return <p className="font-bold text-red-500">Auction ended</p>;
  }

  return (
    <p className="flex flex-grow items-start font-bold text-emerald-600 dark:text-emerald-400">
      {timeLeft.days > 0 && `${timeLeft.days}d `}
      {String(timeLeft.hours).padStart(2, "0")}:
      {String(timeLeft.minutes).padStart(2, "0")}:
      {String(timeLeft.seconds).padStart(2, "0")}
    </p>
  );
}

export default BidCountdownTimer;
