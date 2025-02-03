import { Contact } from "@/components/Sections/Contact";
import { Landing } from "@/components/Sections/Landing";

export default function HomePage() {
  const launched = process.env.LAUNCHED === "true";
  const RECAPTCHA_SITE_KEY = process.env?.RECAPTCHA_SITE_KEY;
  return (
    <main className="flex min-h-screen flex-col bg-zinc-100 text-black dark:bg-zinc-900 dark:text-white">
      <Landing launched={launched} />
      <Contact RECAPTCHA_SITE_KEY={RECAPTCHA_SITE_KEY} />
    </main>
  );
}
