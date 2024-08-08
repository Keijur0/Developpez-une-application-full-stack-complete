/// <reference types="cypress" />

export default function LoginSpec() {
    describe('Login spec', () => {
        beforeEach(() => {
            cy.visit('/auth/login');
        });
    
        it('should display the login form with correct fields', () => {
            cy.get('mat-form-field.input-form').eq(0).should('be.visible');
            cy.get('input[formControlName="usernameOrEmail"]').should('exist');
        
            cy.get('mat-form-field.input-form').eq(1).should('be.visible');
            cy.get('input[formControlName="password"]').should('exist');
        });

        it('should go back to home page when back button is clicked', () => {
            cy.get('button.arrow-back').click();
            cy.url().should('equal', 'http://localhost:4200/');
        });
    
        it('should toggle password visibility when the icon is clicked', () => {
            cy.get('input[formControlName="password"]').should('have.attr', 'type', 'password');
        
            cy.get('button[role="passwordVisibility"]').click();
            cy.get('input[formControlName="password"]').should('have.attr', 'type', 'text');
        
            cy.get('button[role="passwordVisibility"]').click();
            cy.get('input[formControlName="password"]').should('have.attr', 'type', 'password');
        });
    
        it('should disable submit button if form is invalid', () => {
            cy.get('button[role="submit"]').should('be.disabled');
        });
    
        it('should enable submit button when form is valid', () => {
            cy.get('input[formControlName="usernameOrEmail"]').type('test');
            cy.get('input[formControlName="password"]').type('Test!1234');
    
            cy.get('button[role="submit"]').should('not.be.disabled');
        });

        it('should display error message for invalid credentials', () => {
            cy.get('input[formControlName="usernameOrEmail"]').type('invalid@example.com');
            cy.get('input[formControlName="password"]').type('wrongpassword');
        
            cy.get('button[role="submit"]').click();
        
            cy.get('.mat-snack-bar-container')
                .should('be.visible')
                .and('contain.text', 'Nom d\'utilisateur, email ou mot de passe incorrect');
        });
    
        it('should redirect to posts page on successful login', () => {
            cy.intercept('POST', '/api/auth/login', {
                statusCode: 200,
                body: {
                    token: 'token',
                    id: 1,
                    username: 'test',
                    email: 'test@test.com'
                }
            }).as('sessionInfo');
        
            cy.get('input[formControlName="usernameOrEmail"]').type('test');
            cy.get('input[formControlName="password"]').type('Test!1234');

            cy.get('button[role="submit"]').click();
        
            cy.url().should('include', '/posts');
        });
    });
}