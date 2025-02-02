import { Landing } from "@/components/Sections/Landing";

export default function HomePage() {
  const launched = process.env.LAUNCHED === "true";
  return (
    <main className="flex min-h-screen flex-col bg-zinc-100 text-black dark:bg-zinc-900 dark:text-white">
      <Landing launched={launched} />
    </main>
  );
}
