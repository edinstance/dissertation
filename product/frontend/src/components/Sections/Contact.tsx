"use client";

import { zodResolver } from "@hookform/resolvers/zod";
import { useState } from "react";
import { set, useForm } from "react-hook-form";
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

export function Contact() {
  const {
    handleSubmit,
    register,
    formState: { errors, isSubmitted },
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

  const onSubmit = async (data: FormData) => {
      setIsLoading(true);
      await new Promise((resolve) => setTimeout(resolve, 2000));
	  setIsLoading(false);
      
      setTimeout(() => {
        reset();
      }, 3000);
  };
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
        onSubmit={handleSubmit(onSubmit)}
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
                  Thank you for your message! We'll get back to you soon.
                </p>
              </div>
            )}

            <div className="mt-8 flex justify-end space-x-4">
              <Button variant="outline" onClick={() => reset()}>
                Clear
              </Button>
              <Button
                type="submit"
                className="rounded-md bg-blue-600 px-6 py-2.5 text-sm font-semibold text-white shadow-sm hover:bg-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2"
              >
                Send Message
              </Button>
            </div>
          </>
        )}
      </form>
    </Section>
  );
}
