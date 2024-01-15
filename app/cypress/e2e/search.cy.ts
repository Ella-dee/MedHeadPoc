describe('Hospital search', () => {
  it('Patient is logged in and searches for hospital', () => {
    cy.login('mandy.lloyd@test.com', 'password')
    cy.hospitalSearch()
  })
})