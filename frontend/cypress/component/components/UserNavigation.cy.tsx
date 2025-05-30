import TabNavigation from "@/components/ui/TabNavigation";
import { USER_NAVIGATION_LINKS } from "@/constants/UserNavigation";

describe("<UserNavigation />", () => {
  it("renders", () => {
    cy.mount(<TabNavigation links={USER_NAVIGATION_LINKS} />);
  });
});
