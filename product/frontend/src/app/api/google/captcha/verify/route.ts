import type { NextRequest } from "next/server";

export async function POST(request: NextRequest) {
  const secret = process.env.RECAPTCHA_SECRET_KEY;

  const { captcha } = await request.json();

  if (!captcha) {
    return Response.json({ success: false, message: "Missing captcha token" });
  }

  const response = await fetch(
    `https://www.google.com/recaptcha/api/siteverify?secret=${secret}&response=${captcha}`,
    {
      method: "POST",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded",
      },
    },
  );
  const data = await response.json();

  return Response.json(data);
}
