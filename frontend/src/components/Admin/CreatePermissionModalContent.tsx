"use client";

import { Actions, Resources } from "@/gql/graphql";
import {
  CREATE_PERMISSION,
  GET_ALL_PERMISSIONS,
} from "@/lib/graphql/admin-permissions";
import { GET_ACTIONS_VALUES, GET_RESOURCES_VALUES } from "@/lib/graphql/enums";
import { useMutation, useQuery } from "@apollo/client";
import { Dispatch, SetStateAction, useMemo } from "react";
import { Controller, SubmitHandler, useForm } from "react-hook-form";
import { Button } from "../ui/Button";
import Divivder from "../ui/Divider";
import { Input } from "../ui/Input";
import LoadingSpinner from "../ui/LoadingSpinner";
import type { SelectOption } from "../ui/Select";
import { Select } from "../ui/Select";

type EnumValue = { name: string };
type EnumTypeResponse = { name: string; enumValues: EnumValue[] };
type ResourceQueryResponse = { Resources?: EnumTypeResponse | null };
type ActionQueryResponse = { Actions?: EnumTypeResponse | null };

const mapEnumToOptions = (
  enumTypeData: EnumTypeResponse | null | undefined,
): SelectOption[] => {
  if (!enumTypeData?.enumValues) {
    return [];
  }
  return enumTypeData.enumValues.map((enumValue) => ({
    value: enumValue.name,
    label: enumValue.name,
  }));
};

interface FormData {
  permissionDescription: string;
  action: SelectOption | null;
  actionDescription: string;
  resource: SelectOption | null;
  resourceDescription: string;
}

function CreatePermissionModalContent({
  setModalOpen,
}: {
  setModalOpen: Dispatch<SetStateAction<boolean>>;
}) {
  const {
    data: resourceData,
    loading: resourceLoading,
    error: resourceError,
  } = useQuery<ResourceQueryResponse>(GET_RESOURCES_VALUES);
  const {
    data: actionData,
    loading: actionLoading,
    error: actionError,
  } = useQuery<ActionQueryResponse>(GET_ACTIONS_VALUES);

  const [createPermission] = useMutation(CREATE_PERMISSION, {
    refetchQueries: [GET_ALL_PERMISSIONS, "getAllPermissions"],
    onCompleted: () => {
      setTimeout(() => {
        setModalOpen(false);
      }, 2000);
    },
  });

  const {
    handleSubmit,
    control,
    register,
    formState: { errors, isSubmitting, isValid },
  } = useForm<FormData>({
    mode: "onChange",
    defaultValues: {
      action: null,
      resource: null,
    },
  });

  const resourceOptions = useMemo(
    () => mapEnumToOptions(resourceData?.Resources),
    [resourceData],
  );
  const actionsOptions = useMemo(
    () => mapEnumToOptions(actionData?.Actions),
    [actionData],
  );

  const isLoading = resourceLoading || actionLoading;
  const queryError = resourceError || actionError;

  const onSubmit: SubmitHandler<FormData> = (formData) => {
    createPermission({
      variables: {
        input: {
          permissionDescription: formData.permissionDescription,
          action: formData.action?.value as Actions,
          actionDescription: formData.actionDescription,
          resource: formData.resource?.value as Resources,
          resourceDescription: formData.resourceDescription,
        },
      },
    });
  };

  if (isLoading) {
    return <LoadingSpinner />;
  }

  if (queryError) {
    return (
      <p className="text-red-500">
        Error loading options: {queryError.message}
      </p>
    );
  }

  return (
    <div className="text-black dark:text-white">
      <div className="flex flex-col space-y-4 pb-4">
        <h1 className="text-xl">Create Permission</h1>
        <h2 className="text-lg">
          Create a new permission by providing a description, action, and
          resource.
        </h2>
        <p className="text-sm">
          Please ensure that the action and resource are selected from the
          provided options. If their descriptions are are already set they will
          not change and if a permission with the same combination of action and
          resource already exists, it will not be created.
        </p>
      </div>

      <Divivder className="py-4" />

      <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
        <div>
          <label className="pb-0.5 text-sm">Permission Description</label>
          <Input
            type="text"
            placeholder="Permission description"
            {...register("permissionDescription", {
              required: "Please enter a description",
            })}
            invalid={errors?.permissionDescription ? true : false}
          />
          {errors.permissionDescription && (
            <p className="text-xs text-red-500">
              {errors.permissionDescription.message}
            </p>
          )}
        </div>
        <Controller
          name="action"
          control={control}
          rules={{ required: "Action is required" }}
          render={({ field }) => (
            <Select
              label="Action"
              options={actionsOptions}
              value={field.value}
              onChange={field.onChange}
              placeholder="Choose an action..."
              invalid={!!errors.action}
              ref={field.ref}
            />
          )}
        />

        {errors.action && (
          <p className="text-sm text-red-500">{errors.action.message}</p>
        )}

        <div>
          <label className="pb-0.5 text-sm">Action Description</label>
          <Input
            type="text"
            placeholder="Action description"
            {...register("actionDescription", {
              required: "Please enter a description",
            })}
            invalid={errors?.actionDescription ? true : false}
          />
          {errors.actionDescription && (
            <p className="text-xs text-red-500">
              {errors.actionDescription.message}
            </p>
          )}
        </div>

        <Controller
          name="resource"
          control={control}
          rules={{ required: "Resource is required" }}
          render={({ field }) => (
            <Select
              label="Resource"
              options={resourceOptions}
              value={field.value}
              onChange={field.onChange}
              placeholder="Choose a resource..."
              invalid={!!errors.resource}
              ref={field.ref}
            />
          )}
        />

        {errors.resource && (
          <p className="text-sm text-red-500">{errors.resource.message}</p>
        )}

        <div>
          <label className="pb-0.5 text-sm">Resource Description</label>
          <Input
            type="text"
            placeholder="Resource description"
            {...register("resourceDescription", {
              required: "Please enter a description",
            })}
            invalid={errors?.resourceDescription ? true : false}
          />
          {errors.resourceDescription && (
            <p className="text-xs text-red-500">
              {errors.resourceDescription.message}
            </p>
          )}
        </div>

        <div className="flex flex-row items-center justify-end space-x-4">
          <Button type="submit" disabled={!isValid || isSubmitting}>
            {isSubmitting ? "Creating..." : "Create Permission"}
          </Button>
        </div>
      </form>
    </div>
  );
}

export default CreatePermissionModalContent;
