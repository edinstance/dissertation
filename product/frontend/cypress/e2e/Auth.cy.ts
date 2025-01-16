describe("Auth", () => {
  const timestamp = Date.now();

  it("should sign up", () => {
    cy.visit("http://localhost:3000/");
    cy.contains("Sign up").click();
    cy.get('input[name="email"]').type(`test-${timestamp}@test.com`);
    cy.get('input[name="name"]').type("testName");
    cy.get('input[name="password"]').type("Testing12");
    cy.get('input[name="confirmPassword"]').type("Testing12");
    cy.get('button[type="submit"]').click();
  });

  Cypress.Commands.add(
    "signUp",
    (email: string, name: string, password: string) => {
      cy.visit("http://localhost:3000/");
      cy.contains("Sign up").click();
      cy.get('input[name="email"]').type(email);
      cy.get('input[name="name"]').type(name);
      cy.get('input[name="password"]').type(password);
      cy.get('input[name="confirmPassword"]').type(password);
      cy.get('button[type="submit"]').click();
    },
  );

  it("should sign in", () => {
    cy.visit("http://localhost:3000/");
    cy.contains("Sign In").click();
    cy.get('input[name="email"]').type(`test-${timestamp}@test.com`);
    cy.get('input[name="password"]').type("Testing12");
    cy.get('button[type="submit"]').click();
  });

  Cypress.Commands.add("signIn", (email: string, password: string) => {
    cy.visit("http://localhost:3000/");
    cy.contains("Sign In").click();
    cy.get('input[name="email"]').type(email);
    cy.get('input[name="password"]').type(password);
    cy.get('button[type="submit"]').click();
  });

  it("should sign in and out", () => {
    cy.signIn(`test-${timestamp}@test.com`, "Testing12");
    cy.wait(2000);
    cy.contains("testName").click();
    cy.contains("Sign Out").click();
  });

  it("should delete user", () => {
    cy.signIn(`test-${timestamp}@test.com`, "Testing12");
    cy.wait(2000);
    cy.contains("Delete User").click();
    cy.contains("Confirm").click();
  });
});
