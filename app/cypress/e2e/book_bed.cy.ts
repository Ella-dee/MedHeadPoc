describe('Hospital search', () => {
  it('Patient is logged in and searches for hospital', () => {
    cy.login('mandy.lloyd@test.com', 'password')
    cy.hospitalSearch()    
    cy.contains('Schoen Hospital London')
    cy.get('#name1144').contains('Schoen Hospital London')
    cy.get('#beds1144').then(($span) => {
      // capture what num is right now
      const availableBeds = parseFloat($span.text())
    
      cy.get('#book1144')
        .click()
        .then(() => {
          //verify popup
          cy.contains('Your bed has been booked!')
          cy.get('#popupClose').click()
          cy.hospitalSearch()
          // check beds
          if(availableBeds>1){
            cy.get('#beds1144').should('be.visible').then(($span)=>{
              const bedsAfterBooking = parseFloat($span.text())  
              expect(bedsAfterBooking).to.eq(availableBeds - 1)
          })
          }else{
            cy.get('#beds1144').should('not.exist')
          }
        })
    })
  })
})