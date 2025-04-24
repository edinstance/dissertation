/** @type {import('next').NextConfig} */
const nextConfig = {
  output: "standalone",
  images: {
    remotePatterns: [
      {
        protocol: "https",
        hostname: `${process.env.S3_BUCKET_NAME}.s3.amazonaws.com`,
      },
      {
        protocol: "http",
        hostname: `localhost`,
      },
    ],
  },
};

export default nextConfig;
