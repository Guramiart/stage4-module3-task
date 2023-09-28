# Task

This module is an extension of REST API Advanced module and covers following topics:

1. Spring Security framework
2. Oauth2 and OpenId Connect
3. JWT token

Spring Security is a powerful and highly customizable authentication and access-control framework. It is the de-facto standard for securing Spring-based applications. OAuth 2.0 is a security standard where you give one application permission to access your data in another application. The steps to grant permission, or consent, are often referred to as authorization or even delegated authorization. You authorize one application to access your data, or use features in another application on your behalf, without giving them your password. OpenID Connect (OIDC) is a thin layer that sits on top of OAuth 2.0 that adds login and profile information about the person who is logged in. JSON Web Tokens are JSON objects used to send information between parties in a compact and secure manner.

## Application Requirements

1. Spring Security should be used as a security framework.
2. Application should support only stateless user authentication and verify integrity of JWT token.
3. Users should be stored in a database with some basic information and a password.
4. Get acquainted with the concepts OAuth2 and OpenId Connect
5. (Optional task) Use OAuth2 as an authorization protocol. (OAuth2 scopes should be used to restrict data. Implicit grant and Resource owner credentials grant should be implemented.)
6. (Optional task) It's allowed to use Spring Data. Requirement for this task - all repository (and existing ones) should be migrated to Spring Data.
User Permissions:
```
- Guest:
    * Read operations for main entity.
    * Signup.
    * Login.
- User:
    * All operations for guest.
    * Addition of news, comments.
- Administrator (can be added only via database call):
    * All operations for users.
    * Addition and modification of entities.
```

# Demo

1. Generate for demo at least 1000 authors, 1000 tags, 1000 comments, 10 000 news (should be linked with tags, authors, comments). All values should look like more -or-less meaningful: random words, but not random letters
2. Demonstrate API using Postman tool (prepare for demo Postman collection with APIs)
3. (Optional) Build & run application using command line