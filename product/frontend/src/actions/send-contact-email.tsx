"use server";

import { SESClient, SendEmailCommand } from "@aws-sdk/client-ses";

const SES_SENDER_EMAIL = process.env.SES_SENDER_EMAIL;
const SES_RECIPIENT_EMAIL = process.env.SES_RECIPIENT_EMAIL;
const SES_PRODUCTION = process.env.SES_PRODUCTION === "true";

const sesClient = new SESClient();

interface EmailData {
  firstName: string;
  lastName: string;
  email: string;
  message: string;
}

export async function sendContactEmail(emailData: EmailData) {
  if (!SES_SENDER_EMAIL || !SES_RECIPIENT_EMAIL) {
    console.error("Missing required environment variables.");
    return { success: false, error: "An unknown error occurred" };
  }

  const { firstName, lastName, email, message } = emailData;

  const recipientEmailParams = {
    Destination: {
      ToAddresses: [SES_RECIPIENT_EMAIL],
    },
    Message: {
      Body: {
        Text: {
          Charset: "UTF-8",
          Data: `New message from: ${firstName} ${lastName} (${email})\n\nMessage:\n${message}`,
        },
      },
      Subject: {
        Charset: "UTF-8",
        Data: "New Contact Form Submission",
      },
    },
    Source: SES_SENDER_EMAIL,
  };

  try {
    const recipientResponse = await sesClient.send(
      new SendEmailCommand(recipientEmailParams),
    );
    console.log("Recipient email sent:", recipientResponse);

    if (SES_PRODUCTION && recipientResponse.$metadata.httpStatusCode === 200) {
      const confirmationEmailParams = {
        Destination: {
          ToAddresses: [email],
        },
        Message: {
          Body: {
            Text: {
              Charset: "UTF-8",
              Data: `Thank you for your message, ${firstName}! We have received it and will get back to you soon.`,
            },
          },
          Subject: {
            Charset: "UTF-8",
            Data: "Thank you for contacting SubShop!",
          },
        },
        Source: SES_SENDER_EMAIL,
      };

      const confirmationResponse = await sesClient.send(
        new SendEmailCommand(confirmationEmailParams),
      );
      console.log("Confirmation email sent:", confirmationResponse);
    }

    return { success: true };
  } catch (error: unknown) {
    console.error("Email sending error:", error);
    if (error instanceof Error) {
      return { success: false, error: error.message };
    }
    return { success: false, error: "An unknown error occurred" };
  }
}
