
import Header from "@/components/Header";
import SessionProvider from "@/components/Providers/Auth";
import Divivder from "@/components/ui/Divider";

describe("<Header />", () => {
  it("renders", () => {
    cy.mount(
      <SessionProvider session={null}>
        <Header />
      </SessionProvider>,
    );
  });
});