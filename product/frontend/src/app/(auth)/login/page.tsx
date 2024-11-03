"use client";

import { Button } from "@/components/ui/button";
import { EyeIcon, EyeSlashIcon } from "@heroicons/react/24/outline";
import { signIn } from "next-auth/react";
import Link from "next/link";
import { useEffect, useState } from "react";

function SignInPage() {
  const [errorMessage, setErrorMessage] = useState("");
  const [showPassword, setShowPassword] = useState(false);
  const [loginDetails, setLoginDetails] = useState({
    email: "",
    password: "",
  });

  const handleSubmit = (event: React.FormEvent) => {
    event.preventDefault();

    signIn("credentials", {
      ...loginDetails,
      redirectTo: `${window.location.origin}`,
    });
  };

  useEffect(() => {
    const query = new URLSearchParams(window.location.search);
    const error = query.get("error");
    const code = query.get("code");
    if (error && code) {
      setErrorMessage(code);
    }
  }, []);

  return (
    <main className="flex min-h-screen flex-col items-center bg-zinc-100 p-36 dark:bg-zinc-900">
      <h1 className="mb-6 text-2xl font-bold text-black dark:text-white">
        Login to your account
      </h1>
      <form onSubmit={handleSubmit}>
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
              value={loginDetails.email}
              onChange={(e) =>
                setLoginDetails({ ...loginDetails, email: e.target.value })
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
                value={loginDetails.password}
                onChange={(e) =>
                  setLoginDetails({ ...loginDetails, password: e.target.value })
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
              Login
            </Button>
          </div>
          <p className="flex justify-end pt-2 text-sm text-black dark:text-white">
            Don&apos;t have an account?&nbsp;
            <Link href="/signup" className="font-semibold">
              Sign up here
            </Link>
          </p>
        </div>
      </form>
    </main>
  );
}

export default SignInPage;
