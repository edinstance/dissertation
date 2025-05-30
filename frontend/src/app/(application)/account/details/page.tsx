import UserDetailsForm from "@/components/Users/UserDetailsForm";

/**
 * Details component for displaying and managing user details.
 *
 * This component renders the UserDetailsForm, allowing users to view and
 * update their personal information.
 *
 * @returns The rendered Details component.
 */
export default function Details() {
  return (
    <div className="pt-8 text-black dark:text-white">
      <UserDetailsForm />
    </div>
  );
}
