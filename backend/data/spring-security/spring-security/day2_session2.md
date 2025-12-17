# Session Metadata
Day: Day 2
Session: Session 2
Topic: Authorization Rules
Difficulty: Intermediate
Duration: 45 minutes

## Learning Objectives
By the end of this session, the learner should be able to:
- Clearly distinguish between Authentication and Authorization.
- Secure specific URL patterns based on user roles.
- Correctly use `hasRole()` and `hasAuthority()`.
- Understand the automatic `ROLE_` prefix convention for `hasRole()`.
- Configure multiple, ordered authorization rules within `authorizeHttpRequests`.

## Key Concepts
- **Authentication vs. Authorization**: Authentication is "who are you?" (verifying identity). Authorization is "what are you allowed to do?" (enforcing access policies).
- **Role-Based Access Control (RBAC)**: A common authorization strategy where permissions are associated with roles, and users are assigned to roles.
- **`hasRole('ADMIN')`**: Checks if the authenticated user has the authority `ROLE_ADMIN`. Spring Security automatically adds the `ROLE_` prefix.
- **`hasAuthority('WRITE_PRIVILEGE')`**: Checks if the authenticated user has an authority with the exact name `WRITE_PRIVILEGE`. No prefix is added.
- **Rule Specificity**: In `HttpSecurity`, authorization rules must be ordered from most specific to most general.

## Code Context
Relevant Files:
- `src/main/java/com/javatech/security/config/SecurityConfig.java`
- `src/main/java/com/javatech/security/controller/Controller.java` (or similar)

Relevant Pattern:
The pattern involves expanding the `authorizeHttpRequests` lambda to include role-based checks for different URL patterns.

```java
// In SecurityConfig.java
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http.csrf(c -> c.disable()) // Discussed later
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/admin/**").hasRole("ADMIN") // Most specific
                    .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                    .requestMatchers("/login", "/register").permitAll() // Public endpoints
                    .anyRequest().authenticated() // Most general
            )
            // ... other configurations
            .build();
}
```

## Hands-On Tasks
1.  Create a controller with several endpoints, such as `/`, `/user/profile`, and `/admin/dashboard`.
2.  In `SecurityConfig.java`, modify the `authorizeHttpRequests` configuration.
3.  Add a rule to restrict access to `/admin/**` to users with the `ADMIN` role.
4.  Add a rule to restrict access to `/user/**` to users with either `USER` or `ADMIN` roles.
5.  Ensure your public endpoints (`/login`, `/register`) are still permitted for all.
6.  Log in with a `USER` role. Try to access `/user/profile` (should work) and `/admin/dashboard` (should fail with a 403 Forbidden error).
7.  Log in with an `ADMIN` role and verify you can access all endpoints.

## Common Errors

### Error: Getting a 403 Forbidden error for a user who should have access.
**Cause:**
- **`hasRole()` vs. `hasAuthority()` confusion**: Using `hasRole("ROLE_ADMIN")` is incorrect; it should be `hasRole("ADMIN")`. The authority stored in the `UserDetails` object should be `ROLE_ADMIN`.
- **Incorrect rule order**: Placing `anyRequest().authenticated()` before a more specific rule like `requestMatchers("/admin/**").hasRole("ADMIN")` will cause the specific rule to be ignored.
**Fix:**
- Use `hasRole("ADMIN")` and ensure your `UserDetails` provides the authority `"ROLE_ADMIN"`.
- Always order your matchers from most specific to most general.

## Out of Scope
- Method-level security (e.g., `@PreAuthorize("hasRole('ADMIN')")`).
- Creating custom authorization logic beyond roles.
- OAuth2 scopes for authorization.
- Securing non-web-request resources.

## Reflection Prompts
- What is the practical difference between a 401 Unauthorized and a 403 Forbidden response?
- When would you choose to use `hasAuthority()` over `hasRole()`?
- Sketch out the order of rules you would need to secure an e-commerce site with `/products` (public), `/cart` (user), and `/orders/management` (admin) endpoints.

```json
{
  "day": "Day 2",
  "session": "Session 2",
  "topic": "Authorization Rules",
  "tags": ["spring-security", "authorization", "rbac", "hasrole", "hasauthority", "httpsecurity"]
}
```