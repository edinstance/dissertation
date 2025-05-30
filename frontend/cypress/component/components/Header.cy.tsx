import Header from "@/components/Header";
import SessionProvider from "@/components/Providers/Auth";

describe("<Header />", () => {
  it("renders", () => {
    cy.mount(
      <SessionProvider session={null}>
        <Header launched={true} />
      </SessionProvider>,
    );
  });
});
