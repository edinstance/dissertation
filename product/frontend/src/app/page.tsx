import { Section } from "@/components/ui/Section";
import { Button } from "@/components/ui/Button";

export default function HomePage() {
  const launched = process.env.LAUNCHED === "true";
  return (
    <main className="flex min-h-screen flex-col bg-zinc-100 text-black dark:bg-zinc-900 dark:text-white">
      <Section className="flex min-h-screen items-center justify-center">
        <div className="mx-auto max-w-2xl py-32 sm:py-48 lg:py-56">
          <div className="text-center">
            <h1 className="text-balance text-5xl font-semibold tracking-tight text-black sm:text-7xl dark:text-white">
              A new shopping experience!
            </h1>
            <p className="mt-8 text-pretty text-lg font-medium text-gray-600 sm:text-xl/8 dark:text-gray-400">
              Why pay fees when shopping online? SubShop is a new way to shop
              online without the fees. Instead a monthly subscription catered to
              your needs.
            </p>
            <div className="mt-10 flex items-center justify-center gap-x-6">
              {launched ? (
                <>
                  <Button variant="outline">Get in touch</Button>
                  <Button href="/sign-up">Sign up</Button>
                </>
              ) : (
                <Button>Learn more</Button>
              )}
            </div>
          </div>
        </div>
      </Section>
    </main>
  );
}
