# Session Metadata
Day: Day 3
Session: Session 1
Topic: CSRF & Exception Handling
Difficulty: Intermediate
Duration: 45 minutes

## Learning Objectives
By the end of this session, the learner should be able to:
- Explain what a Cross-Site Request Forgery (CSRF) attack is at a high level.
- Understand that Spring Security enables CSRF protection by default for stateful applications.
- Identify when it is appropriate to disable CSRF protection (e.g., for stateless REST APIs).
- Recognize that a 403 Forbidden error is the default response for both CSRF failures and authorization failures.

## Key Concepts
- **CSRF (Cross-Site Request Forgery)**: An attack that tricks an authenticated user into submitting a malicious request to a web application they are currently logged into. It exploits the trust a site has in a user's browser.
- **CSRF Token**: To prevent this, servers can generate a unique, unpredictable token that must be included in state-changing requests (POST, PUT, DELETE). Spring Security handles this automatically for stateful apps.
- **Stateless vs. Stateful**: CSRF is primarily a concern for stateful, session-based applications where a browser automatically sends cookies. For stateless APIs where a token must be manually added to a header for every request, the risk is largely mitigated.
- **Exception Handling**: By default, Spring Security responds with a generic 403 Forbidden page for access denied errors. This can be customized, but for now, it's important to know what it means.

## Code Context
Relevant Files:
- `src/main/java/com/javatech/security/config/SecurityConfig.java`

Relevant Pattern:
The application being analyzed is a stateless REST API that uses JWTs. In this context, CSRF protection is not needed and is explicitly disabled. The configuration to do this is a key part of the security setup for a REST service.

```java
// In SecurityConfig.java
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
            .csrf(customizer -> customizer.disable()) // Disabling CSRF
            .authorizeHttpRequests(auth -> auth
                // ... rules
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Declaring the app is stateless
            // ... other configurations
            .build();
}
```

## Hands-On Tasks
1.  Review the `SecurityConfig.java` file in the project.
2.  Locate the `.csrf(customizer -> customizer.disable())` line.
3.  Discuss why this line is present. Relate it to the `.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))` configuration.
4.  Theorize what would happen if you were building a traditional web app (like one using Thymeleaf and sessions) and you made a POST request from a form without including the CSRF token. (The expected result is a 403 error).
5.  Explain that the 403 error seen in the previous session (Day 2, Session 2) for role-based access failure is the same error code you'd see for a CSRF failure.

## Common Errors

### Error: POST/PUT/DELETE requests fail with a 403 Forbidden error in a stateful application.
**Cause:**
- This is the most common symptom of a missing or incorrect CSRF token. CSRF protection is enabled by default, and Spring Security expects a valid `_csrf` token to be submitted with any state-changing request.
**Fix:**
- If using a form-based frontend like Thymeleaf, ensure the form includes the token (e.g., via a hidden input).
- If building a stateless API, disable CSRF as shown in the code context. **Do not disable it without understanding why.**

## Out of Scope
- Deep dive into how CSRF tokens are generated, stored, or validated.
- CORS (Cross-Origin Resource Sharing) configuration, which is a related but different security concept.
- Advanced exception handling using `@ControllerAdvice` or custom `AccessDeniedHandler` implementations.
- Implementing CSRF protection in a front-end framework like React or Angular.

## Reflection Prompts
- Why is CSRF protection less critical for a stateless API that uses a bearer token in the `Authorization` header?
- What two different security problems can lead to a 403 Forbidden error?
- If you see a 403 error, what are the first two things you should check?

```json
{
  "day": "Day 3",
  "session": "Session 1",
  "topic": "CSRF and Exception Handling",
  "tags": ["spring-security", "csrf", "stateless", "exception-handling", "403-forbidden"]
}
```