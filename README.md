# springboot-oauth2-demo-2024
Demo of OAuth2 using Spring Boot and HTML

The goal of this exercise is to verify OAuth2/OIDC1 built-in capabilities in Spring Boot.
We are going to have some shortcuts: Authorization Server, microservice and Single Page Application would be in the same codebase.
This constraint forces us to have some hacks in configuration.

![client_authentication_methods: none](./imgs/cfg-001.png)

![client registration not in yaml but in a bean](./imgs/cfg-002.png)

![key to sign JWTs generated in runtime](./imgs/cfg-003.png)

![security filter chain combined for Authorization Server and microservice](./imgs/cfg-004.png)

![JWT decoder based on shared RSA key](./imgs/cfg-005.png)

Here is our simple API to be protected
![GET API returning some fruits and vegetables](./imgs/code-001.png)

Here's the demo to steal JWT and use in next API call
![Login via user/password](./imgs/demo-001.png)
![Login via OIDC form](./imgs/demo-002.png)
![Successfull call via stolen JWT](./imgs/demo-003.png)

Lesson learnt: use unique scope per microservice with fine grained access control for GET, POST, PUT, PATCH, DELETE
