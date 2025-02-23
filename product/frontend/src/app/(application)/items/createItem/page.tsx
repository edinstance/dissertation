"use client";

import { deleteS3Object, getPresignedUrls } from "@/actions/s3";
import { Button } from "@/components/ui/Button";
import Divider from "@/components/ui/Divider";
import ImageUpload from "@/components/ui/ImageUpload";
import { Input } from "@/components/ui/Input";
import { Select } from "@/components/ui/Select";
import { TextArea } from "@/components/ui/TextArea";
import { GET_ITEM_BY_ID_QUERY, SAVE_ITEM_MUTATION } from "@/lib/graphql/items";
import { useMutation, useQuery } from "@apollo/client";
import { useSearchParams } from "next/navigation";
import { useState } from "react";
import { Controller, useForm } from "react-hook-form";

type FormData = {
  id: string | null;
  name: string;
  description: string;
  duration: string;
  price: number;
  stock: number;
  category: string;
  images: string[];
};

const durationOptions = [
  { value: 1, label: "1 Day" },
  { value: 3, label: "3 Days" },
  { value: 7, label: "7 Days" },
];

export default function CreateItem() {
  const {
    handleSubmit,
    register,
    formState: { errors },
    control,
    setValue,
  } = useForm<FormData>();

  const searchParams = useSearchParams();
  const itemId = searchParams.get("itemId");

  const [endingTime, setEndingTime] = useState<string | null>(null);
  const [uploadedImages, setUploadedImages] = useState<UploadedImage[]>([]);

  useQuery(GET_ITEM_BY_ID_QUERY, {
    skip: !itemId,
    variables: { id: itemId! },
    onCompleted: (data) => {
      const item = data.getItemById;
      if (item) {
        setValue("id", item.id || null);
        setValue("name", item.name || "");
        setValue("description", item.description || "");
        setValue("category", item.category || "");
        setValue("price", item.price || 0);
        setValue("stock", item.stock || 0);
        setValue(
          "images",
          item.images
            ? item.images.filter((img): img is string => img !== null)
            : [],
        );
        setEndingTime(item.endingTime || null);

        setUploadedImages(
          item.images
            ? item.images
                .filter((img): img is string => img !== null)
                .map((img) => ({
                  publicUrl: img,
                  key: img,
                }))
            : [],
        );
      }
    },
  });

  const [saveItemMutation] = useMutation(SAVE_ITEM_MUTATION);

  async function onSubmit(data: FormData) {
    if (!data.id) {
      const newEndingTime = new Date(
        Date.now() + Number(data.duration) * 24 * 60 * 60 * 1000,
      )
        .toLocaleDateString("en-UK", {
          year: "numeric",
          month: "2-digit",
          day: "2-digit",
          hour: "2-digit",
          minute: "2-digit",
          second: "2-digit",
          hour12: false,
        })
        .replace(/(\d+)\/(\d+)\/(\d+),\s/, "$3-$2-$1 ");

      await saveItemMutation({
        variables: {
          itemInput: {
            name: data.name,
            description: data.description,
            endingTime: newEndingTime,
            price: Number(data.price),
            stock: Number(data.stock),
            category: data.category,
            images: data.images,
          },
        },
      });
    } else {
      await saveItemMutation({
        variables: {
          itemInput: {
            id: data.id,
            name: data.name,
            description: data.description,
            endingTime: endingTime,
            price: Number(data.price),
            stock: Number(data.stock),
            category: data.category,
            images: data.images,
          },
        },
      });
    }
  }

  const handleImagesChange = async (files: File[]) => {
    const result = await getPresignedUrls(
      files.map((file) => ({ name: file.name, type: file.type })),
    );

    if (!result.success || !result.urls) {
      throw new Error("Failed to get presigned URLs");
    }

    const uploadedUrls = await Promise.all(
      files.map(async (file, index) => {
        const urlData = result.urls[index];
        if (!urlData) throw new Error(`No URL data for file ${file.name}`);

        const { presignedUrl, publicUrl, key } = urlData;

        const response = await fetch(presignedUrl, {
          method: "PUT",
          body: file,
          headers: {
            "Content-Type": file.type,
          },
        });

        if (!response.ok) {
          throw new Error(`Failed to upload ${file.name}`);
        }

        return {
          file,
          publicUrl,
          key,
        };
      }),
    );

    setUploadedImages((prev) => [...prev, ...uploadedUrls]);

    const allUrls = [...uploadedImages, ...uploadedUrls].map(
      (img) => img.publicUrl,
    );
    setValue("images", allUrls);
  };

  function removeFile(key: string) {
    const s3Key = key.includes(".s3.amazonaws.com/")
      ? key.split(".s3.amazonaws.com/")[1]
      : key;

    setUploadedImages((prev) =>
      prev.filter((img) => img.key !== key && img.key !== s3Key),
    );

    setValue(
      "images",
      uploadedImages
        .filter((img) => img.key !== key && img.key !== s3Key)
        .map((img) => img.publicUrl),
    );

    if (s3Key) {
      deleteS3Object(s3Key);
    }
  }

  return (
    <div className="pt-20">
      <form
        onSubmit={handleSubmit(onSubmit)}
        className="min-h-screen max-w-3xl py-20 pl-8"
      >
        <h1 className="pb-4 text-4xl">List an Item</h1>
        <Divider />
        <div className="grid gap-x-8 gap-y-6 py-4 sm:grid-cols-2">
          <div className="space-y-4">
            <h2>Basic Details</h2>
            <p className="text-xs text-gray-500">
              Enter the basic information about your item.
            </p>
          </div>
          <div className="space-y-4">
            <div className="min-h-12 space-y-1">
              <Input
                type="text"
                placeholder="Item Name"
                {...register("name", { required: "Name is required" })}
                invalid={errors?.name ? true : false}
              />
              {errors.name && (
                <p className="text-xs text-red-500">{errors.name.message}</p>
              )}
            </div>

            <div className="min-h-32 space-y-1">
              <TextArea
                placeholder="Description"
                rows={4}
                {...register("description", {
                  required: "Description is required",
                })}
                invalid={errors?.description ? true : false}
              />
              {errors.description && (
                <p className="text-xs text-red-500">
                  {errors.description.message}
                </p>
              )}
            </div>
            <div className="min-h-12 space-y-1">
              <Input
                type="text"
                placeholder="Category"
                {...register("category", { required: "Category is required" })}
                invalid={errors?.category ? true : false}
              />
              {errors.category && (
                <p className="text-xs text-red-500">
                  {errors.category.message}
                </p>
              )}
            </div>
          </div>
        </div>

        <Divider />
        <div className="grid gap-x-8 gap-y-6 py-4 sm:grid-cols-2">
          <div className="space-y-4">
            <h2>Pricing and Stock</h2>
            <p className="text-xs text-gray-500">
              Set the price and available stock for your item.
            </p>
          </div>
          <div className="space-y-4">
            <div className="min-h-12 space-y-1">
              <Input
                type="number"
                step="0.01"
                placeholder="Price"
                {...register("price", { required: "Price is required" })}
                invalid={errors?.price ? true : false}
              />
              {errors.price && (
                <p className="text-xs text-red-500">{errors.price.message}</p>
              )}
            </div>
            <div className="min-h-12 space-y-1">
              <Input
                type="number"
                placeholder="Stock"
                {...register("stock", { required: "Stock is required" })}
                invalid={errors?.stock ? true : false}
              />
              {errors.stock && (
                <p className="text-xs text-red-500">{errors.stock.message}</p>
              )}
            </div>
          </div>
        </div>

        <Divider />
        <div className="grid gap-x-8 gap-y-6 py-4 sm:grid-cols-2">
          <div className="space-y-4">
            <h2>Images</h2>
            <p className="text-xs text-gray-500">
              Upload images of your item. You can select multiple images.
            </p>
          </div>
          <div className="space-y-4">
            <div className="min-h-12 space-y-1">
              <ImageUpload
                onImagesChange={handleImagesChange}
                removeFile={removeFile}
                value={uploadedImages}
                maxFiles={5}
                maxSize={5 * 1024 * 1024}
              />
              {errors.images && (
                <p className="text-xs text-red-500">{errors.images.message}</p>
              )}
            </div>
          </div>
        </div>

        <Divider />

        {!itemId && (
          <div className="grid gap-x-8 gap-y-6 py-4 sm:grid-cols-2">
            <div className="space-y-4">
              <h2>Duration</h2>
              <p className="text-xs text-gray-500">
                Set how long you want this item to be available for.
              </p>
            </div>
            <div className="space-y-4">
              <div className="min-h-12 space-y-1">
                <Controller
                  name="duration"
                  control={control}
                  rules={{ required: "Duration is required" }}
                  render={({ field }) => (
                    <Select
                      options={durationOptions}
                      value={
                        durationOptions.find(
                          (option) => option.value === Number(field.value),
                        ) || null
                      }
                      onChange={(selected) => field.onChange(selected.value)}
                      placeholder="Select Duration"
                      invalid={!!errors.duration}
                    />
                  )}
                />
                {errors.duration && (
                  <p className="text-xs text-red-500">
                    {errors.duration.message}
                  </p>
                )}
              </div>
            </div>
          </div>
        )}

        <Divider />
        <div className="flex flex-row items-end justify-end space-x-4 pt-4">
          <Button type="submit" className="max-w-40">
            {!itemId && <span>Create Item</span>}
            {itemId && <span>Save Changes</span>}
          </Button>
        </div>
      </form>
    </div>
  );
}
