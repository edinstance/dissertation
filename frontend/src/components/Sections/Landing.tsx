"use client";
import { Button } from "../ui/Button";
import { Section } from "../ui/Section";

/**
 * Landing component for displaying the landing page content.
 *
 * This component renders a section with a title, description, and buttons
 * based on the launch status. It provides a welcoming message and options
 * for users to either learn more or sign up.
 *
 * @param props - The props for the component.
 * @returns The rendered Landing component.
 */
export function Landing({ launched }: { launched: boolean }) {
  return (
    <Section className="flex min-h-screen items-center justify-center">
      <div className="mx-auto max-w-2xl py-32 sm:py-48 lg:py-56">
        <div className="text-center">
          <h1 className="text-balance text-5xl font-semibold tracking-tight text-black dark:text-white sm:text-7xl">
            A new shopping experience!
          </h1>
          <p className="mt-8 text-pretty text-lg font-medium text-gray-600 dark:text-gray-400 sm:text-xl/8">
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
  );
}
