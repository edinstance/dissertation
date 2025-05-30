/// <reference types="cypress" />
// ***********************************************
// This example commands.ts shows you how to
// create various custom commands and overwrite
// existing commands.
//
// For more comprehensive examples of custom
// commands please read more here:
// https://on.cypress.io/custom-commands
// ***********************************************
//
//
// -- This is a parent command --
// Cypress.Commands.add('login', (email, password) => { ... })
//
//
// -- This is a child command --
// Cypress.Commands.add('drag', { prevSubject: 'element'}, (subject, options) => { ... })
//
//
// -- This is a dual command --
// Cypress.Commands.add('dismiss', { prevSubject: 'optional'}, (subject, options) => { ... })
//
//
// -- This will overwrite an existing command --
// Cypress.Commands.overwrite('visit', (originalFn, url, options) => { ... })
//
// declare global {
//   namespace Cypress {
//     interface Chainable {
//       login(email: string, password: string): Chainable<void>
//       drag(subject: string, options?: Partial<TypeOptions>): Chainable<Element>
//       dismiss(subject: string, options?: Partial<TypeOptions>): Chainable<Element>
//       visit(originalFn: CommandOriginalFn, url: string, options: Partial<VisitOptions>): Chainable<Element>
//     }
//   }
// }

declare global {
  namespace Cypress {
    interface Chainable {
      signIn(email: string, password: string): Chainable<void>;
      signUp(email: string, name: string, password: string): Chainable<void>;
      deleteUser(email: string, password: string): Chainable<void>;
    }
  }
}

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

Cypress.Commands.add("signIn", (email: string, password: string) => {
  cy.visit("http://localhost:3000/");
  cy.contains("Sign In").click();
  cy.get('input[name="email"]').type(email);
  cy.get('input[name="password"]').type(password);
  cy.get('button[type="submit"]').click();
});

Cypress.Commands.add("deleteUser", (email: string, password: string) => {
  cy.signIn(email, password);
  cy.wait(2000);
  cy.contains("Delete User").click();
  cy.contains("Confirm").click();
});
