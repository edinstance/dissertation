import { cn } from "@/lib/utils";
import { XMarkIcon } from "@heroicons/react/24/outline";
import { useCallback } from "react";
import { FileRejection, useDropzone } from "react-dropzone";

function getFileName({ key }: { key: string }) {
  const parts = key.split("/");
  return parts[parts.length - 1];
}

interface ImageUploadProps {
  onImagesChange: (files: File[]) => void;
  removeFile: (key: string) => void;
  maxSize?: number;
  accept?: Record<string, string[]>;
  maxFiles?: number;
  value?: UploadedImage[];
}

function ImageUpload({
  onImagesChange,
  removeFile,
  maxSize = 5 * 1024 * 1024,
  accept = {
    "image/jpeg": [".jpeg", ".jpg"],
    "image/png": [".png"],
  },
  maxFiles = 5,
  value = [],
}: ImageUploadProps) {
  const onDrop = useCallback(
    (acceptedFiles: File[], rejectedFiles: FileRejection[]) => {
      rejectedFiles.forEach((rejection) => {
        console.error(
          `${rejection.file.name}: ${rejection.errors[0]?.message}`,
        );
      });

      onImagesChange(acceptedFiles);
    },
    [onImagesChange],
  );

  const { getRootProps, getInputProps, isDragActive } = useDropzone({
    onDrop,
    accept,
    maxSize,
    maxFiles: maxFiles - value.length,
    multiple: true,
  });

  const formatFileSize = (bytes: number) => {
    if (bytes === 0) return "0 Bytes";
    const k = 1024;
    const sizes = ["Bytes", "KB", "MB", "GB"];
    const i = Math.floor(Math.log(bytes) / Math.log(k));
    return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + " " + sizes[i];
  };

  return (
    <div className="space-y-4">
      {value.length < maxFiles && (
        <div
          {...getRootProps()}
          className={cn(
            "min-h-[100px] rounded-lg border-2 border-dashed p-4 transition-colors",
            isDragActive
              ? "border-blue-500 bg-blue-50"
              : "border-gray-300 hover:border-gray-400",
            "cursor-pointer",
          )}
        >
          <input {...getInputProps()} />
          <div className="flex h-full flex-col items-center justify-center space-y-2">
            <p className="text-center text-sm text-gray-500">
              {isDragActive ? (
                "Drop the files here..."
              ) : (
                <>
                  Drag & drop files here, or click to select files
                  <br />
                  <span className="text-xs">
                    (Max {maxFiles} files, up to{" "}
                    {(maxSize / 1024 / 1024).toFixed(0)}
                    MB each)
                  </span>
                </>
              )}
            </p>
          </div>
        </div>
      )}
      {value.length > 0 && (
        <div className="space-y-4">
          <div className="overflow-hidden rounded-lg border border-gray-200 bg-white shadow-sm dark:border-gray-800 dark:bg-gray-900">
            {value.map((image, index) => (
              <div
                key={index}
                className="flex items-center justify-between border-b border-gray-200 p-1 last:border-0 hover:bg-gray-50 dark:border-gray-800 dark:hover:bg-gray-800"
              >
                <div className="min-w-0 flex-1">
                  <div className="flex flex-row items-center gap-2 truncate text-sm">
                    <span className="w-1/2 truncate font-medium text-gray-900 dark:text-gray-100">
                      {getFileName({ key: image.key })}
                    </span>
                    {image.file?.size && (
                      <span className="text-xs text-gray-500 dark:text-gray-400">
                        ({formatFileSize(image.file.size)})
                      </span>
                    )}
                  </div>
                </div>
                <button
                  type="button"
                  onClick={(e) => {
                    e.preventDefault();
                    removeFile(image.key);
                  }}
                >
                  <XMarkIcon className="h-5 w-5 text-gray-400 hover:text-red-500" />
                </button>
              </div>
            ))}
          </div>
          <p className="text-sm text-gray-500 dark:text-gray-400">
            {value.length} file{value.length === 1 ? "" : "s"} selected
            {maxFiles && ` (${maxFiles - value.length} remaining)`}
          </p>
        </div>
      )}
    </div>
  );
}

export default ImageUpload;
