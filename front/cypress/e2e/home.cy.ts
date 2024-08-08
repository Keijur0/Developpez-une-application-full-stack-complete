/// <reference types="cypress" />

export default function HomeSpec() {
    describe('Home spec', () => {
        beforeEach(() => {
            cy.visit('/');
        });
        
        it('should display the logo image', () => {
            cy.get('img.logo')
                .should('be.visible')
                .and('have.attr', 'src', '/assets/logo_p6.png')
                .and('have.attr', 'alt', 'logo');
        });
        
        it('should have a "Se connecter" button', () => {
            cy.get('button.home-button')
                .contains('Se connecter')
                .should('be.visible');
        });
        
        it('should have an "S\'inscrire" button', () => {
            cy.get('button.home-button')
                .contains("S'inscrire")
                .should('be.visible');
        });
        
        it('should navigate to the login page when "Se connecter" is clicked', () => {
            cy.get('button.home-button').contains('Se connecter').click();
            cy.url().should('include', '/auth/login');
        });
        
        it('should navigate to the registration page when "S\'inscrire" is clicked', () => {
            cy.get('button.home-button').contains("S'inscrire").click();
            cy.url().should('include', '/auth/register');
        });
    });
}