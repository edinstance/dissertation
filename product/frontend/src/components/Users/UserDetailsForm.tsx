"use client";

import { GET_USER, SAVE_USER_DETAILS_MUTATION } from "@/lib/graphql/users";
import { useMutation, useQuery } from "@apollo/client";
import { Input } from "@headlessui/react";
import { useForm } from "react-hook-form";
import { Button } from "../ui/Button";
import Divider from "../ui/Divider";

type FormData = {
  contactNumber: string;
  addressStreet: string;
  addressCity: string;
  addressCounty: string;
  addressPostcode: string;
};

export default function UserDetailsForm() {
  const { data: userData, loading: queryLoading } = useQuery(GET_USER);
  const [saveUserDetailsMutation, { loading: mutationLoading }] = useMutation(
    SAVE_USER_DETAILS_MUTATION,
  );

  const loading = queryLoading || mutationLoading;

  const user = userData?.getUser;
  const userDetails = user?.details;

  const {
    handleSubmit,
    register,
    formState: { errors },
  } = useForm<FormData>({
    values: {
      contactNumber: userDetails?.contactNumber || "",
      addressStreet: userDetails?.addressStreet || "",
      addressCity: userDetails?.addressCity || "",
      addressCounty: userDetails?.addressCounty || "",
      addressPostcode: userDetails?.addressPostcode || "",
    },
  });

  console.log(errors);
  console.log(loading);

  function onSubmit(data: FormData) {
    if (user) {
      saveUserDetailsMutation({
        variables: {
          id: user.id,
          detailsInput: {
            addressCity: data.addressCity,
            addressCounty: data.addressCounty,
            addressPostcode: data.addressPostcode,
            addressStreet: data.addressStreet,
            contactNumber: data.contactNumber,
          },
        },
      });
    }
  }

  return (
    <form onSubmit={handleSubmit(onSubmit)} className="max-w-3xl">
      <Divider />
      <div className="grid gap-x-8 gap-y-6 py-4 sm:grid-cols-2">
        <div className="space-y-4">
          <h2>Contact number</h2>
          <p className="pb-4 text-xs text-gray-500">
            Phone number so that we can contact you.
          </p>
        </div>
        <div className="space-y-4">
          <Input
            type="text"
            className="block max-h-8 w-full rounded-lg border p-1 shadow-lg data-[invalid]:border-red-500 data-[invalid]:data-[hover]:border-red-500 data-[invalid]:data-[focus]:ring-red-500 data-[invalid]:data-[focus]:ring-offset-red-500 dark:bg-slate-800"
            placeholder="07911 123456"
            {...register("contactNumber", {
              required: "Please enter your contact number",
            })}
            invalid={errors?.contactNumber ? true : false}
          />
          {errors.contactNumber && (
            <p className="text-xs text-red-500">
              {errors.contactNumber.message}
            </p>
          )}
        </div>
      </div>
      <Divider />
      <div className="grid gap-x-8 gap-y-6 py-4 sm:grid-cols-2">
        <div className="space-y-4">
          <h2>Address</h2>
          <p className="text-xs text-gray-500">
            Your address for billing purposes.
          </p>
        </div>
        <div className="space-y-4">
          <Input
            type="text"
            className="block max-h-8 w-full rounded-lg border p-1 shadow-lg data-[invalid]:border-red-500 data-[invalid]:data-[hover]:border-red-500 data-[invalid]:data-[focus]:ring-red-500 data-[invalid]:data-[focus]:ring-offset-red-500 dark:bg-slate-800"
            placeholder="123 Main St"
            {...register("addressStreet", { required: true })}
            invalid={errors?.addressStreet ? true : false}
          />
          <Input
            type="text"
            className="block max-h-8 w-full rounded-lg border p-1 shadow-lg data-[invalid]:border-red-500 data-[invalid]:data-[hover]:border-red-500 data-[invalid]:data-[focus]:ring-red-500 data-[invalid]:data-[focus]:ring-offset-red-500 dark:bg-slate-800"
            placeholder="City"
            {...register("addressCity", { required: true })}
            invalid={errors?.addressCity ? true : false}
          />
          <Input
            type="text"
            className="block max-h-8 w-full rounded-lg border p-1 shadow-lg data-[invalid]:border-red-500 data-[invalid]:data-[hover]:border-red-500 data-[invalid]:data-[focus]:ring-red-500 data-[invalid]:data-[focus]:ring-offset-red-500 dark:bg-slate-800"
            placeholder="County"
            {...register("addressCounty", { required: true })}
            invalid={errors?.addressCounty ? true : false}
          />
          <Input
            type="text"
            className="block max-h-8 w-full rounded-lg border p-1 shadow-lg data-[invalid]:border-red-500 data-[invalid]:data-[hover]:border-red-500 data-[invalid]:data-[focus]:ring-red-500 data-[invalid]:data-[focus]:ring-offset-red-500 dark:bg-slate-800"
            placeholder="Postcode"
            {...register("addressPostcode", { required: true })}
            invalid={errors?.addressPostcode ? true : false}
          />
        </div>
      </div>
      <Divider />
      <div className="flex flex-row items-end justify-end space-x-4 pt-4">
        <Button type="submit" className="max-w-32" disabled={loading}>
          {userDetails ? "Save" : "Create"}
        </Button>
      </div>
    </form>
  );
}
