import UserNavigation from "@/components/Users/UserNavigation";

describe("<UserNavigation />", () => {
  it("renders", () => {
    cy.mount(<UserNavigation />);
  });
});
