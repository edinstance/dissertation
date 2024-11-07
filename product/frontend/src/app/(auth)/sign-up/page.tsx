"use client";

import createUser from "@/actions/sign-up";
import { Button } from "@/components/ui/Button";
import { EyeIcon, EyeSlashIcon } from "@heroicons/react/24/outline";
import { signIn } from "next-auth/react";
import Link from "next/link";
import { useState } from "react";

function SignUpPage() {
  const [errorMessage, setErrorMessage] = useState("");
  const [showPassword, setShowPassword] = useState(false);
  const [signUpDetails, setSignUpDetails] = useState({
    email: "",
    name: "",
    password: "",
    confirmPassword: "",
  });

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();
    if (signUpDetails.password !== signUpDetails.confirmPassword) {
      setErrorMessage("Passwords do not match");
      return;
    }

    try {
      const result = await createUser(signUpDetails);
      if (result.error) {
        setErrorMessage(result.error);
      } else if (result.success) {
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

  return (
    <main className="flex min-h-screen flex-col items-center bg-zinc-100 px-20 py-36 dark:bg-zinc-900">
      <h1 className="mb-6 text-2xl font-bold text-black dark:text-white">
        Create a new account
      </h1>
      <form onSubmit={handleSubmit} className="max-2-36">
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
                setSignUpDetails({ ...signUpDetails, email: e.target.value })
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
          </div>
        </div>

        <div className="flex min-h-6 justify-center pt-1">
          {errorMessage && (
            <p className="text-sm text-red-500 dark:text-red-400">
              {errorMessage}
            </p>
          )}
        </div>
        <div className="md:flex-row md:space-y-0">
          <div className="flex w-full pt-2">
            <Button type="submit" color="blue">
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
    </main>
  );
}

export default SignUpPage;
