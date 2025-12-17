# Session Metadata
Day: Day 1
Session: Session 2
Topic: Configuration Basics
Difficulty: Beginner
Duration: 60 minutes

## Learning Objectives
By the end of this session, the learner should be able to:
- Create a `SecurityConfig` class with `@EnableWebSecurity`.
- Define a `SecurityFilterChain` bean to customize security rules.
- Use `HttpSecurity` to configure which endpoints are public and which are protected.
- Understand and use `requestMatchers` to specify URL patterns.
- Enable form-based login using `formLogin()`.

## Key Concepts
- **`@EnableWebSecurity`**: This annotation, placed on a `@Configuration` class, enables Spring's web security support and provides the integration with Spring MVC.
- **`SecurityFilterChain`**: A bean that defines the entire security configuration for HTTP requests. You can define multiple chains, but we will start with one.
- **`HttpSecurity`**: The primary configuration object for building a `SecurityFilterChain`. It provides a fluent API (or "DSL") to set up rules.
- **`authorizeHttpRequests`**: The method used to define URL-based authorization rules.
- **`formLogin`**: Re-enables a username/password login form, which we will need since we are overriding the default configuration.

## Code Context
Relevant Files:
- `src/main/java/com/javatech/security/config/SecurityConfig.java`

Relevant Pattern:
The core pattern is defining a `@Bean` of type `SecurityFilterChain` inside a `@Configuration` class. This bean uses the `HttpSecurity` object to build a chain of rules. The lambda-based DSL is the modern, recommended approach.

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/register").permitAll() // Allow public access
                        .anyRequest().authenticated() // Secure all other requests
                )
                .formLogin(Customizer.withDefaults()) // Enable default form login
                .build();
    }
}
```

*(Note: The snippet is illustrative. The actual lab code might use different endpoints.)*

## Hands-On Tasks
1.  Create a new Java class named `SecurityConfig` in the `config` package.
2.  Annotate the class with `@Configuration` and `@EnableWebSecurity`.
3.  Define a public method that returns a `SecurityFilterChain` and annotate it with `@Bean`.
4.  Use the `HttpSecurity` parameter to configure rules.
5.  Permit access to `/login` and `/register` for everyone.
6.  Specify that any other request must be authenticated.
7.  Enable form login using `formLogin(Customizer.withDefaults())`.
8.  Run the application and test that you can access the public endpoints but are prompted to log in for protected ones.

## Common Errors

### Error: Application fails to start, or security rules are not applied.
**Cause:**
- Missing `@Configuration` or `@EnableWebSecurity` on the `SecurityConfig` class.
- The `securityFilterChain` method is not declared as a public `@Bean`.
**Fix:**
- Ensure all three annotations (`@Configuration`, `@EnableWebSecurity`, `@Bean`) are present and correctly placed.

### Error: All pages are still public, or all are still private.
**Cause:**
- The order of `requestMatchers` is incorrect. Rules are matched in the order they are declared. A general rule like `anyRequest()` placed before a specific one will override it.
**Fix:**
- Always place more specific rules (`requestMatchers("/some/path")`) before more general rules (`anyRequest()`).

## Out of Scope
- Creating users (`UserDetailsService`). The application will still use the default `user` for now.
- Password encoding (`PasswordEncoder`).
- Role-based authorization (`hasRole`, `hasAuthority`).
- CSRF, CORS, or session management details.
- JWT-based authentication.

## Reflection Prompts
- What is the single most important bean for configuring web security?
- How does `authorizeHttpRequests` change the default "deny-all" behavior?
- Why do we need to add `formLogin()` when we are creating our own configuration?

```json
{
  "day": "Day 1",
  "session": "Session 2",
  "topic": "Configuration Basics",
  "tags": ["spring-security", "httpsecurity", "securityfilterchain", "formlogin", "authorizehttprequests"]
}
```