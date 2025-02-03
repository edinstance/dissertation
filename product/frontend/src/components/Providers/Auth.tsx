"use client";

import { SessionProvider } from "next-auth/react";

// This component is used to provide the session to the app
// It is created as an extra component so that it can be made a client side only component
export default SessionProvider;
