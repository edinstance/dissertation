"use client";

import createUser from "@/actions/sign-up";
import { ApolloWrapper } from "@/components/apollo";
import { Button } from "@/components/ui/Button";
import { CREATE_USER_MUTATION } from "@/lib/graphql/users";
import { useMutation } from "@apollo/client";
import { EyeIcon, EyeSlashIcon } from "@heroicons/react/24/outline";
import { signIn } from "next-auth/react";
import Link from "next/link";
import { useState } from "react";
import { z } from "zod";

const passwordSchema = z
  .string()
  .min(8, { message: "Password must be at least 8 characters long" })
  .regex(/[A-Z]/, {
    message: "Password must contain at least one uppercase letter",
  })
  .regex(/[a-z]/, {
    message: "Password must contain at least one lowercase letter",
  })
  .regex(/\d/, { message: "Password must contain at least one number" });

const signUpSchema = z
  .object({
    email: z.string().email({ message: "Invalid email address" }),
    name: z.string().min(1, { message: "Name is required" }),
    password: passwordSchema,
    confirmPassword: z.string(),
  })
  .refine((data) => data.password === data.confirmPassword, {
    message: "Passwords do not match",
    path: ["confirmPassword"],
  });

function SignUpPage() {
  const [errorMessage, setErrorMessage] = useState("");
  const [showPassword, setShowPassword] = useState(false);
  const [signUpDetails, setSignUpDetails] = useState({
    email: "",
    name: "",
    password: "",
    confirmPassword: "",
  });

  const [createUserMutation] = useMutation(CREATE_USER_MUTATION);

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();

    const result = signUpSchema.safeParse(signUpDetails);
    if (!result.success) {
      setErrorMessage(result.error?.issues[0]?.message || "Invalid input");
      return;
    }
    const { email, name, password } = result.data;

    try {
      const result = await createUser({ name, email, password });
      if (result.error) {
        setErrorMessage(result.error);
      } else if (result.success) {
        createUserMutation({
          variables: {
            input: {
              id: result.id,
              name,
              email,
            },
          },
        });
        const signInResult = await signIn("credentials", {
          email: signUpDetails.email,
          password: signUpDetails.password,
          callbackUrl: "/account",
        });
        if (signInResult?.error) {
          setErrorMessage(signInResult.error);
        }
        setErrorMessage("");
      }
    } catch (error) {
      if (error instanceof Error) {
        setErrorMessage(error.message);
      } else {
        setErrorMessage("Error creating user");
      }
    }
  };

  const validatePassword = (password: string) => {
    const result = passwordSchema.safeParse(password);
    const errors = result.success
      ? []
      : result.error.errors.map((error) => error.message);

    return {
      length: !errors.includes("Password must be at least 8 characters long"),
      uppercase: !errors.includes(
        "Password must contain at least one uppercase letter",
      ),
      lowercase: !errors.includes(
        "Password must contain at least one lowercase letter",
      ),
      number: !errors.includes("Password must contain at least one number"),
    };
  };

  const passwordValidation = validatePassword(signUpDetails.password);

  return (
    <div className="flex min-h-screen flex-col items-center px-10 pt-20">
        <div>
          <h1 className="mb-6 items-start justify-start text-2xl font-bold text-black dark:text-white">
            Create a new account
          </h1>
          <form onSubmit={handleSubmit} className="min-w-96">
            <div className="space-y-6">
              <div>
                <label
                  htmlFor="email"
                  className="block text-sm font-medium text-black dark:text-white"
                >
                  Email Address
                </label>
                <input
                  type="email"
                  id="email"
                  name="email"
                  required
                  placeholder="john-doe@rhul.com"
                  value={signUpDetails.email}
                  onChange={(e) =>
                    setSignUpDetails({
                      ...signUpDetails,
                      email: e.target.value,
                    })
                  }
                  className="mt-1 block w-full rounded-md border border-gray-300 px-3 py-2 shadow-sm focus:border-blue-500 focus:outline-none focus:ring-blue-500 dark:border-gray-600 dark:bg-gray-700 dark:text-white dark:placeholder-gray-400"
                />
              </div>
              <div>
                <label
                  htmlFor="name"
                  className="block text-sm font-medium text-black dark:text-white"
                >
                  Name
                </label>
                <input
                  type="text"
                  id="name"
                  name="name"
                  required
                  placeholder="John Doe"
                  value={signUpDetails.name}
                  onChange={(e) =>
                    setSignUpDetails({ ...signUpDetails, name: e.target.value })
                  }
                  className="mt-1 block w-full rounded-md border border-gray-300 px-3 py-2 shadow-sm focus:border-blue-500 focus:outline-none focus:ring-blue-500 dark:border-gray-600 dark:bg-gray-700 dark:text-white dark:placeholder-gray-400"
                />
              </div>
              <div>
                <label
                  htmlFor="password"
                  className="block text-sm font-medium text-black dark:text-white"
                >
                  Password
                </label>
                <div className="relative mt-1">
                  <input
                    type={showPassword ? "text" : "password"}
                    id="password"
                    name="password"
                    required
                    placeholder="**********"
                    value={signUpDetails.password}
                    onChange={(e) =>
                      setSignUpDetails({
                        ...signUpDetails,
                        password: e.target.value,
                      })
                    }
                    className="block w-full rounded-md border border-gray-300 px-3 py-2 shadow-sm focus:border-blue-500 focus:outline-none focus:ring-blue-500 dark:border-gray-600 dark:bg-gray-700 dark:text-white dark:placeholder-gray-400"
                  />
                  <button
                    type="button"
                    onClick={() => {
                      setShowPassword(!showPassword);
                    }}
                    className="absolute inset-y-0 right-0 flex items-center px-3 text-gray-500 dark:text-gray-400"
                  >
                    {showPassword ? (
                      <EyeSlashIcon className="h-5 w-5" />
                    ) : (
                      <EyeIcon className="h-5 w-5" />
                    )}
                  </button>
                </div>
                <ul className="mt-2 list-inside list-disc text-sm">
                  <li
                    className={
                      passwordValidation.length
                        ? "text-green-500"
                        : "text-red-500"
                    }
                  >
                    Password must be at least 8 characters long
                  </li>
                  <li
                    className={
                      passwordValidation.uppercase
                        ? "text-green-500"
                        : "text-red-500"
                    }
                  >
                    Password must contain at least one uppercase letter
                  </li>
                  <li
                    className={
                      passwordValidation.lowercase
                        ? "text-green-500"
                        : "text-red-500"
                    }
                  >
                    Password must contain at least one lowercase letter
                  </li>
                  <li
                    className={
                      passwordValidation.number
                        ? "text-green-500"
                        : "text-red-500"
                    }
                  >
                    Password must contain at least one number
                  </li>
                </ul>
              </div>
              <div>
                <label
                  htmlFor="confirmPassword"
                  className="block text-sm font-medium text-black dark:text-white"
                >
                  Confirm Password
                </label>
                <div className="relative mt-1">
                  <input
                    type={showPassword ? "text" : "password"}
                    id="confirmPassword"
                    name="confirmPassword"
                    required
                    placeholder="**********"
                    value={signUpDetails.confirmPassword}
                    onChange={(e) =>
                      setSignUpDetails({
                        ...signUpDetails,
                        confirmPassword: e.target.value,
                      })
                    }
                    className="block w-full rounded-md border border-gray-300 px-3 py-2 shadow-sm focus:border-blue-500 focus:outline-none focus:ring-blue-500 dark:border-gray-600 dark:bg-gray-700 dark:text-white dark:placeholder-gray-400"
                  />
                  <button
                    type="button"
                    onClick={() => {
                      setShowPassword(!showPassword);
                    }}
                    className="absolute inset-y-0 right-0 flex items-center px-3 text-gray-500 dark:text-gray-400"
                  >
                    {showPassword ? (
                      <EyeSlashIcon className="h-5 w-5" />
                    ) : (
                      <EyeIcon className="h-5 w-5" />
                    )}
                  </button>
                </div>

                <ul className="mt-2 list-inside list-disc text-sm">
                  <li
                    className={
                      signUpDetails.password === signUpDetails.confirmPassword
                        ? "text-green-500"
                        : "text-red-500"
                    }
                  >
                    Passwords must match
                  </li>
                </ul>
              </div>
            </div>

            <div className="flex min-h-6 justify-center pt-1">
              {errorMessage && (
                <p className="text-sm text-red-500 dark:text-red-400">
                  {errorMessage}
                </p>
              )}
            </div>
            <div className="w-full md:flex-row md:space-y-0">
              <div className="flex w-full pt-2">
                <Button type="submit" color="blue" className="w-full">
                  Sign Up
                </Button>
              </div>
              <p className="flex items-center pt-2 text-sm text-black dark:text-white">
                Already have an account?&nbsp;
                <Link href="/sign-in" className="font-semibold">
                  Sign in
                </Link>
              </p>
            </div>
          </form>
        </div>
    </div>
  );
}

export default SignUpPage;
