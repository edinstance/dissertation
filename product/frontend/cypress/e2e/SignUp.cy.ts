describe("Navigate", () => {
  it("should navigate to the signup page", () => {
    cy.visit("http://localhost:3000/");
    cy.contains("Sign up").click();
  });
});
