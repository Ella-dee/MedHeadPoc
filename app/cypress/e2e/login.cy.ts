describe('Patient Login', () => {
  it('It logs in patient and goes to profile page', () => {
    //OK
    cy.login('mandy.lloyd@test.com', 'password')    
    cy.url().should('include', '/profile')
    cy.contains('NHS Number')
    cy.contains('Find Hospital')
    cy.contains('LogOut').click()
    //KO
    cy.login('mandy.lloyd@test.com', 'wrongpassword')
    cy.contains('Login failed: Bad credentials')
    cy.contains(/^LogOut$/).should('not.exist')    
  })
})