/// <reference types="cypress" />

import { Post } from "src/app/features/posts/interfaces/post.interface";
import { Topic } from "src/app/interfaces/topic.interface";
import { User } from "src/app/interfaces/user.interface";


export default function PostListSpec() {
    describe('Post list spec', () => {

        const post1: Post = {
            id: 1,
            topicId: 1,
            title: 'Post 1',
            authorId: 1,
            content: 'Post 1 content',
            createdAt: new Date('2024-08-07')
        };

        const post2: Post = {
            id: 2,
            topicId: 2,
            title: 'Post 2',
            authorId: 2,
            content: 'Post 2 content',
            createdAt: new Date('2024-08-06')
        }
        const posts: Post[] = [ post1, post2 ];

        const user1: User = {
            id: 1,
            username: 'User 1',
            email: 'user1@test.com'
        };

        const user2: User = {
            id: 2,
            username: 'User 2',
            email: 'user2@test.com'
        };

        const topic1: Topic = {
            id: 1,
            name: 'Topic 1',
            description: 'Topic 1 description'
        };

        const topic2: Topic = {
            id: 2,
            name: 'Topic 2',
            description: 'Topic 2 description'
        };

        const topics: Topic[] = [ topic1, topic2 ]

        
        beforeEach(() => {
            cy.intercept('GET', '/api/auth/me', {
                statusCode: 404,
                body: {
                    token: 'token',
                    id: 1,
                    username: 'test',
                    email: 'test@test.com'
                }
            }).as('me');

            cy.intercept('POST', '/api/auth/login', {
                statusCode: 200,
                body: {
                    token: 'token',
                    id: 1,
                    username: 'test',
                    email: 'test@test.com'
                }
            }).as('sessionInfo');

            cy.intercept('GET', '/api/post/subscribed/1', {
                statusCode: 200,
                body: posts
            }).as('posts');

            cy.intercept('GET', '/api/user/1', {
                statusCode: 200,
                body: user1
            }).as('user1');

            cy.intercept('GET', '/api/user/2', {
                statusCode: 200,
                body: user2
            }).as('user2');

            cy.intercept('GET', 'api/topic/1', {
                statusCode: 200,
                body: topic1
            }).as('topic1');

            cy.intercept('GET', 'api/topic/2', {
                statusCode: 200,
                body: topic2
            }).as('topic2');

            cy.window().then((win) => {
                win.localStorage.removeItem('token');
            });

            cy.visit('/auth/login');
            cy.get('input[formControlName="usernameOrEmail"]').type('test');
            cy.get('input[formControlName="password"]').type('Test!1234');

            cy.get('button[role="submit"]').click();

            cy.url().should('include', '/posts');
        });
        
        it('should display the create button and sort button', () => {
            cy.get('button.create-button')
                .should('be.visible')
                .and('contain.text', 'Créer un article');
        
            cy.get('button.sort-button')
                .should('be.visible')
                .and('contain.text', 'Trier par');
        });
    
        it('should display posts with correct information', () => {
            cy.get('.link.box').should('have.length', 2);
            cy.get('.box-title').eq(0).should('contain.text', 'Post 1');
            cy.get('.box-content').eq(0).should('contain.text', 'Post 1 content');
            cy.get('.box-title').eq(1).should('contain.text', 'Post 2');
            cy.get('.box-content').eq(1).should('contain.text', 'Post 2 content');
        });
    
        it('should toggle sort direction and update posts', () => {
            cy.get('.box-title').eq(0).should('contain.text', 'Post 1');
        
            cy.get('button.sort-button').click();
        
            cy.get('.box-title').eq(0).should('contain.text', 'Post 2');
        });
    
        it('should navigate to create post page when create button is clicked', () => {
            cy.intercept('GET', 'api/topic', {
                statusCode: 200,
                body: topics
            }).as('topics');
            cy.get('button.create-button').click();
        
            cy.url().should('include', '/posts/create');
        });

        it('should navigate to post detail page when a post is clicked', () => {
            cy.intercept('GET', '/api/post/1', {
                statusCode: 200,
                body: post1
            }).as('post1');

            cy.intercept('GET', '/api/comment/1', {
                statusCode: 200,
                body: []
            }).as('comments');
            

            cy.get('.box-title').eq(0).click();
            cy.url().should('include', '/posts/detail/1');
        });
    });
}