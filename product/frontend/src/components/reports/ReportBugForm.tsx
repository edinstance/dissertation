"use client";
import { REPORT_BUG_MUTATION } from "@/lib/graphql/reports";
import { useMutation } from "@apollo/client";
import { DialogTitle } from "@headlessui/react";
import { zodResolver } from "@hookform/resolvers/zod";
import { useState } from "react";
import { useForm } from "react-hook-form";
import { z } from "zod";
import { Button } from "../ui/Button";
import { Input } from "../ui/Input";
import LoadingSpinner from "../ui/LoadingSpinner";
import { TextArea } from "../ui/TextArea";

const bugReportSchema = z.object({
  title: z
    .string()
    .min(3, "Title must be at least 3 characters")
    .max(100, "Title must be less than 100 characters"),
  description: z
    .string()
    .min(10, "Description must be at least 10 characters")
    .max(1000, "Description must be less than 1000 characters"),
});

type BugReportFormData = z.infer<typeof bugReportSchema>;

interface MutationResponse {
  success: boolean;
  message: string;
}

function ReportBugForm({ setOpen }: { setOpen: (value: boolean) => void }) {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<BugReportFormData>({
    resolver: zodResolver(bugReportSchema),
  });

  const [loading, setLoading] = useState(false);
  const [response, setResponse] = useState<MutationResponse | null>(null);

  const [reportBugMutation] = useMutation<{
    reportBug: MutationResponse;
  }>(REPORT_BUG_MUTATION);

  async function onSubmit(data: BugReportFormData) {
    setLoading(true);
    setResponse(null);

    try {
      const result = await reportBugMutation({
        variables: {
          title: data.title,
          description: data.description,
        },
      });

      const mutationResponse = result.data?.reportBug;
      setResponse(mutationResponse || null);
      setLoading(false);

      if (mutationResponse?.success) {
        setTimeout(() => {
          setOpen(false);
        }, 5000);
      }
    } catch (err) {
      setLoading(false);
      setResponse({
        success: false,
        message: "An unexpected error occurred. Please try again.",
      });
    }
  }

  return (
    <>
      {loading ? (
        <LoadingSpinner />
      ) : response?.success ? (
        <div className="text-center">
          <DialogTitle className="text-xl text-green-600 dark:text-green-400">
            Thank for for submitting this issue.
          </DialogTitle>
          <p className="text-md mt-2 text-black dark:text-white">
            {response.message}
          </p>
          <p className="mt-2 text-sm text-black dark:text-white">
            Modal will close automatically in 5 seconds...
          </p>
        </div>
      ) : (
        <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
          <DialogTitle className="text-xl text-black dark:text-white">
            Thank you for reporting an issue, we will look into it as soon as
            possible.
          </DialogTitle>
          <p className="text-md text-black dark:text-white">
            Please provide a title and description of the issue you are facing.
          </p>
          <div className="space-y-4">
            <Input
              placeholder="Title"
              {...register("title", {
                required: "Please enter a title for the issue",
              })}
              invalid={errors?.title ? true : false}
            />
            <TextArea
              placeholder="Description"
              {...register("description", {
                required: "Please enter the description of the issue",
              })}
              invalid={errors?.description ? true : false}
            />

            <div className="min-h-10">
              {errors.title && (
                <p className="text-sm text-red-500">{errors.title.message}</p>
              )}
              {errors.description && (
                <p className="text-sm text-red-500">
                  {errors.description.message}
                </p>
              )}
              {response && !response.success && (
                <p className="text-sm text-red-500">{response.message}</p>
              )}
            </div>

            <div className="flex justify-end">
              <Button onClick={handleSubmit(onSubmit)} color="blue">
                Submit
              </Button>
            </div>
          </div>
        </form>
      )}
    </>
  );
}

export default ReportBugForm;
