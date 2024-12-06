describe("Navigate", () => {
  it("should navigate to the signin page", () => {
    cy.visit("http://localhost:3000/");
    cy.contains("Sign In").click();
  });
});
