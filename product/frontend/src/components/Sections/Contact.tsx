"use client";

import { zodResolver } from "@hookform/resolvers/zod";
import { useTheme } from "next-themes";
import { useState } from "react";
import ReCAPTCHA from "react-google-recaptcha";
import { useForm } from "react-hook-form";
import { z } from "zod";
import { Button } from "../ui/Button";
import { Input } from "../ui/Input";
import LoadingSpinner from "../ui/LoadingSpinner";
import { Section } from "../ui/Section";
import { TextArea } from "../ui/TextArea";

const contactSchema = z.object({
  firstName: z
    .string()
    .min(2, "First name must be at least 2 characters")
    .max(50, "First name must be less than 50 characters"),
  lastName: z
    .string()
    .min(2, "Last name must be at least 2 characters")
    .max(50, "Last name must be less than 50 characters"),
  email: z.string().email("Please enter a valid email address"),
  message: z
    .string()
    .min(10, "Message must be at least 10 characters")
    .max(500, "Message must be less than 500 characters"),
});

// Infer the type from the schema
type FormData = z.infer<typeof contactSchema>;

export function Contact({
  RECAPTCHA_SITE_KEY,
}: {
  RECAPTCHA_SITE_KEY?: string;
}) {
  const {
    handleSubmit,
    register,
    formState: { errors },
    reset,
  } = useForm<FormData>({
    resolver: zodResolver(contactSchema),
    defaultValues: {
      firstName: "",
      lastName: "",
      email: "",
      message: "",
    },
  });

  const [isLoading, setIsLoading] = useState(false);
  const [captcha, setCaptcha] = useState<string | null>(null);
  const [isSubmitted, setIsSubmitted] = useState(false);

  const onSubmit = async (data: FormData, e: React.FormEvent) => {
    e.preventDefault();
    setIsLoading(true);
    console.log(data);
    const response = await fetch(`api/google/captcha/verify`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ captcha: captcha }),
    });
    const result = await response.json();
    if (result.success) {
      setIsLoading(false);
      setIsSubmitted(true);
    } else {
      console.error(result.message);
    }
    setTimeout(() => {
      reset();
      setIsSubmitted(false);
    }, 3000);
  };

  const { theme } = useTheme();
  return (
    <Section className="flex min-h-screen flex-col items-center py-16">
      <div className="mb-12 text-center">
        <h1 className="mb-4 text-4xl font-bold tracking-tight text-gray-900 dark:text-white">
          Contact Us
        </h1>
        <h2 className="text-lg text-gray-600 dark:text-gray-400">
          Fill in this form to send us a message
        </h2>
      </div>

      <form
        onSubmit={(e) => {
          e.preventDefault();
          handleSubmit((data) => onSubmit(data, e))(e);
        }}
        className="w-full max-w-2xl rounded-lg border border-gray-200 bg-white p-8 shadow-lg dark:border-zinc-800 dark:bg-zinc-800"
      >
        {isLoading ? (
          <LoadingSpinner />
        ) : (
          <>
            <div className="space-y-6">
              <div className="grid gap-6 md:grid-cols-2">
                <div>
                  <label className="mb-2 block text-sm font-medium text-gray-900 dark:text-gray-200">
                    First Name
                  </label>
                  <Input
                    type="text"
                    placeholder="John"
                    {...register("firstName", {
                      required: "Please enter your first name",
                    })}
                    invalid={errors?.firstName ? true : false}
                    className="w-full"
                  />
                  {errors.firstName && (
                    <p className="mt-1 text-xs text-red-500">
                      {errors.firstName.message}
                    </p>
                  )}
                </div>

                <div>
                  <label className="mb-2 block text-sm font-medium text-gray-900 dark:text-gray-200">
                    Last Name
                  </label>
                  <Input
                    type="text"
                    placeholder="Doe"
                    {...register("lastName", {
                      required: "Please enter your last name",
                    })}
                    invalid={errors?.lastName ? true : false}
                    className="w-full"
                  />
                  {errors.lastName && (
                    <p className="mt-1 text-xs text-red-500">
                      {errors.lastName.message}
                    </p>
                  )}
                </div>
              </div>

              <div>
                <label className="mb-2 block text-sm font-medium text-gray-900 dark:text-gray-200">
                  Email
                </label>
                <Input
                  type="email"
                  placeholder="john.doe@example.com"
                  {...register("email", {
                    required: "Please enter your email",
                  })}
                  invalid={errors?.email ? true : false}
                  className="w-full"
                />
                {errors.email && (
                  <p className="mt-1 text-xs text-red-500">
                    {errors.email.message}
                  </p>
                )}
              </div>

              <div>
                <label className="mb-2 block text-sm font-medium text-gray-900 dark:text-gray-200">
                  Message
                </label>
                <TextArea
                  placeholder="Your message..."
                  {...register("message", {
                    required: "Please enter your message",
                  })}
                  invalid={errors?.message ? true : false}
                  rows={4}
                />
                {errors.message && (
                  <p className="mt-1 text-xs text-red-500">
                    {errors.message.message}
                  </p>
                )}
              </div>
            </div>

            {isSubmitted && (
              <div className="mb-6 mt-4 w-full max-w-2xl rounded-lg bg-green-100 p-4 text-center text-green-800 dark:bg-green-800/30 dark:text-green-400">
                <p className="text-sm font-medium">
                  Thank you for your message! We&apos;ll get back to you soon.
                </p>
              </div>
            )}

            <div className="mt-8 flex flex-col justify-between gap-4 md:flex-row">
              {RECAPTCHA_SITE_KEY && (
                <ReCAPTCHA
                  sitekey={RECAPTCHA_SITE_KEY}
                  theme="light"
                  onChange={setCaptcha}
                />
              )}
              <div className="flex justify-end self-end">
                <div className="flex gap-4">
                  <Button variant="outline" onClick={() => reset()}>
                    Clear
                  </Button>
                  <Button type="submit">Send Message</Button>
                </div>
              </div>
            </div>
          </>
        )}
      </form>
    </Section>
  );
}
