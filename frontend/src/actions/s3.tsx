"use server";

import { auth } from "@/server/auth";
import {
  DeleteObjectCommand,
  PutObjectCommand,
  S3Client,
} from "@aws-sdk/client-s3";
import { getSignedUrl } from "@aws-sdk/s3-request-presigner";

const S3_BUCKET_NAME = process.env.S3_BUCKET_NAME;
const S3_BUCKET_URL = process.env.S3_BUCKET_URL;

const s3Client = new S3Client({
  region: "eu-west-2",
  forcePathStyle: true,
  endpoint: S3_BUCKET_URL,
});

export async function getPresignedUrls(
  files: { name: string; type: string }[],
) {
  try {
    const session = await auth();
    if (!session) {
      return { success: false, error: "Not authenticated" };
    }
    const urls = await Promise.all(
      files.map(async (file) => {
        const uniqueFileName = `${session.user.id}/${file.name}`;

        const command = new PutObjectCommand({
          Bucket: S3_BUCKET_NAME,
          Key: uniqueFileName,
          ContentType: file.type,
        });

        const presignedUrl = await getSignedUrl(s3Client, command, {
          expiresIn: 3600,
        });

        return {
          presignedUrl,
          publicUrl: `${S3_BUCKET_URL}/${S3_BUCKET_NAME}/${uniqueFileName}`,
          key: uniqueFileName,
        };
      }),
    );

    return { success: true, urls };
  } catch (error) {
    console.error("Error generating presigned URLs:", error);
    return { success: false, error: "Failed to generate presigned URLs" };
  }
}

export async function deleteS3Object(key: string) {
  try {
    const command = new DeleteObjectCommand({
      Bucket: S3_BUCKET_NAME,
      Key: key,
    });

    await s3Client.send(command);
    return { success: true };
  } catch (error) {
    console.error("Error deleting S3 object:", error);
    return { success: false, error: "Failed to delete file" };
  }
}
