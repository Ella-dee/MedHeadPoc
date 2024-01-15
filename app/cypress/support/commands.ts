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
Cypress.Commands.add('login', (username, password) => {
      cy.visit('/')
      cy.contains('Login').click()
      cy.url().should('include', '/login')
      cy.get('[name="username"]').type(username)
      cy.get('[name="username"]').should('have.value', username)    
      cy.get('[name="password"]').type(password)
      cy.get('[name="password"]').should('have.value', password)
      cy.contains('Log In').click()
  })

  Cypress.Commands.add('hospitalSearch', () => {
    cy.contains('Find Hospital').click()
    cy.contains('Your Address')
    cy.contains(/^Specialty Group$/)
    cy.get('[name="inputSpecialty"').should('not.be.visible');
    cy.contains(/^Specialty$/).should('not.be.visible')
    cy.get('[name="inputSpecialtyGroup"]').select('Anesthésie')
    cy.get('[name="inputSpecialty"').should('be.visible');
    cy.contains(/^Specialty$/).should('be.visible')
    cy.get('[name="form"]').submit()
    cy.contains('Distance in Km')
    cy.contains('Distance in Time')
    cy.contains('Available beds')
    cy.contains('Book bed')
})