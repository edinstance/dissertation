import { Button } from "@/components/ui/Button";

describe("<Button />", () => {
  it("renders", () => {
    cy.mount(<Button>Test Button</Button>);
  });
});
