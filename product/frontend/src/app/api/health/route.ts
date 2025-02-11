/**
 * Handles GET requests and returns a simple response.
 *
 * This function responds with a status of 200 and a message indicating that the service is up.
 *
 * @returns A promise that resolves to a Response object with a status of 200 and a message "UP".
 */
export async function GET() {
  return new Response("UP", { status: 200 });
}
