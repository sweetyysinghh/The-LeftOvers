# Session Metadata
Day: Day 3
Session: Session 2
Topic: Mini Integration Lab - Securing REST APIs
Difficulty: Intermediate
Duration: 75 minutes

## Learning Objectives
By the end of this session, the learner should be able to:
- Understand the complete authentication flow for a stateless JWT-based API.
- Use a REST client like Postman to perform the login flow.
- Authenticate at a login endpoint to receive a JWT.
- Use the obtained JWT in an `Authorization: Bearer <token>` header to access protected resources.
- Diagnose 401 vs. 403 errors when interacting with a secured REST API.

## Key Concepts
- **Stateless Authentication Flow**: A client sends credentials once to a login endpoint. The server validates them and returns a short-lived token (JWT). The client includes this token in the `Authorization` header for all subsequent requests to protected endpoints. The server holds no session state.
- **`Authorization: Bearer <token>`**: The standard HTTP header for sending a security token.
- **`JwtFilter`**: A custom filter that runs once per request. Its job is to inspect the `Authorization` header, extract the token, validate it, and if valid, load the user's details and set up the `SecurityContext`.
- **The Big Picture**: `Request -> JwtFilter -> JWTService (validation) -> MyUserDetailsService -> SecurityContextHolder`. This chain ensures that by the time the request reaches the controller, Spring Security already knows who the user is.

## Code Context
Relevant Files:
- `src/main/java/com/javatech/security/config/SecurityConfig.java`
- `src/main/java/com/javatech/security/config/JwtFilter.java`
- `src/main/java/com/javatech/security/controller/UserController.java`
- `src/main/java/com/javatech/security/service/JWTService.java`
- `src/main/java/com/javatech/security/service/UserService.java`

Relevant Pattern:
The integration of all components in `SecurityConfig` is the key pattern. The `JwtFilter` is added *before* the standard `UsernamePasswordAuthenticationFilter`, and the `AuthenticationManager` is exposed as a bean to be used by the login service.

```java
// In SecurityConfig.java
.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

// In UserController.java
@PostMapping("/login")
public String login(@RequestBody Users user) {
    return userService.verify(user); // Returns a JWT string
}

// In JwtFilter.java
String authHeader = request.getHeader("Authorization");
if (authHeader != null && authHeader.startsWith("Bearer ")) {
    token = authHeader.substring(7);
    // ... validate token and set SecurityContext
}
```

## Hands-On Tasks
1.  Open a REST client like Postman.
2.  Create a `POST` request to the `/login` endpoint.
3.  In the request body, provide the raw JSON for a valid user (e.g., `{"username": "kiran", "password": "k@123"}`). Set the `Content-Type` header to `application/json`.
4.  Send the request. Copy the JWT string returned in the response body.
5.  Create a new `GET` request to a protected endpoint (e.g., `/hello`).
6.  Go to the "Authorization" tab, select "Bearer Token", and paste the JWT into the token field. This will create the `Authorization: Bearer <token>` header.
7.  Send the request and observe the successful response.
8.  Try sending the request again after removing the token. Observe the `403 Forbidden` error. (Note: It's 403 not 401 because the filter chain completes, but finds no authentication).

## Common Errors

### Error: Receiving a 403 Forbidden instead of a successful response.
**Cause:**
- **Missing "Bearer " prefix**: The `Authorization` header value must be `Bearer <token>`, not just `<token>`.
- **Expired Token**: JWTs have an expiration time. If you wait too long, the token becomes invalid.
- **Invalid Token Signature**: If the token was modified in any way, the signature will not match.
**Fix:**
- Double-check the `Authorization` header in Postman.
- Re-run the `/login` request to get a fresh token.

### Error: The `/login` endpoint itself returns a 403 error.
**Cause:**
- The `/login` endpoint was not correctly permitted in the `SecurityConfig`'s `authorizeHttpRequests` rules.
**Fix:**
- Ensure `requestMatchers("/login").permitAll()` is present in your `SecurityFilterChain`.

## Out of Scope
- JWT Refresh Tokens.
- Storing JWTs securely on the client-side (e.g., in `localStorage` vs. cookies).
- OAuth2 and OpenID Connect (OIDC) flows.
- Microservice security patterns (e.g., API Gateway token validation).

## Reflection Prompts
- Trace the path of a request to a protected endpoint, starting from the `JwtFilter`. What does each component do?
- Why is it important that the application is configured to be `STATELESS` when using this pattern?
- What is the role of the `AuthenticationManager` bean in the login process?

```json
{
  "day": "Day 3",
  "session": "Session 2",
  "topic": "Mini Integration Lab",
  "tags": ["spring-security", "jwt", "rest-api", "postman", "bearer-token", "stateless", "jwtfilter"]
}
```