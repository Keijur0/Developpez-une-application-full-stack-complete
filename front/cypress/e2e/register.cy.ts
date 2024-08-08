/// <reference types="cypress" />

export default function RegisterSpec() {
    describe('Register spec', () => {
        beforeEach(() => {
            cy.visit('/auth/register');
        });
    
        it('should display the registration form with correct fields', () => {
            cy.get('mat-form-field.input-form').eq(0).should('be.visible');
            cy.get('input[formControlName="username"]').should('exist');
        
            cy.get('mat-form-field.input-form').eq(1).should('be.visible');
            cy.get('input[formControlName="email"]').should('exist');

            cy.get('mat-form-field.input-form').eq(2).should('be.visible');
            cy.get('input[formControlName="password"]').should('exist');
        });

        it('should go back to home page when back button is clicked', () => {
            cy.get('button.arrow-back').click();
            cy.url().should('equal', 'http://localhost:4200/');
        });
    
        it('should disable submit button when form is invalid', () => {
            cy.get('button.submit-button').should('be.disabled');
        });
    
        it('should enable submit button when form is valid', () => {
            cy.get('input[formControlName="username"]').type('test');
            cy.get('input[formControlName="email"]').type('test@test.com');
            cy.get('input[formControlName="password"]').type('Test!1234');
        
            cy.get('button.submit-button').should('not.be.disabled');
        });
    
        it('should show success message and redirect on successful registration', () => {
            cy.intercept('POST', '/api/auth/register', {
                statusCode: 200,
                body: {}
            }).as('registerRequest');
        
            cy.get('input[formControlName="username"]').type('testuser');
            cy.get('input[formControlName="email"]').type('testuser@example.com');
            cy.get('input[formControlName="password"]').type('ValidPass1!');
        
            cy.get('button.submit-button').click();
        
            cy.get('.mat-snack-bar-container')
                .should('be.visible')
                .and('contain.text', 'Inscription réussie');
        
            cy.url().should('include', '/auth/login');
        });
    
        it('should show error message on registration failure', () => {
            cy.intercept('POST', '/api/register', {
                statusCode: 400,
            }).as('registerRequest');
        
            cy.get('input[formControlName="username"]').type('test');
            cy.get('input[formControlName="email"]').type('test@test.com');
            cy.get('input[formControlName="password"]').type('Test!1234');
        
            cy.get('button.submit-button').click();
        
            cy.get('.mat-snack-bar-container')
                .should('be.visible')
                .and('contain.text', 'Le nom d\'utilisateur ou l\'adresse email existe déjà');
        });
    });
}