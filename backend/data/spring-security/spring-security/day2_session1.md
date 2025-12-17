# Session Metadata
Day: Day 2
Session: Session 1
Topic: Users & Authentication
Difficulty: Intermediate
Duration: 60 minutes

## Learning Objectives
By the end of this session, the learner should be able to:
- Explain the roles of `UserDetailsService` and `UserDetails`.
- Define application users with `InMemoryUserDetailsManager`.
- Understand why storing plain-text passwords is a critical security vulnerability.
- Configure a `PasswordEncoder` bean (e.g., `BCryptPasswordEncoder`).
- Configure an `AuthenticationProvider` to connect the `UserDetailsService` and `PasswordEncoder`.

## Key Concepts
- **`UserDetailsService`**: A core interface in Spring Security for loading user-specific data. It acts as the bridge between your user data store (in-memory, database, LDAP) and the security framework.
- **`UserDetails`**: An interface that provides core user information (username, password, authorities, status) to the framework.
- **`PasswordEncoder`**: A service interface for encoding passwords using a one-way hashing algorithm. This is essential for securely storing user credentials. `BCrypt` is the modern standard.
- **`AuthenticationProvider`**: The component responsible for the actual authentication logic. `DaoAuthenticationProvider` is the standard implementation that uses a `UserDetailsService` to find a user and a `PasswordEncoder` to validate the submitted password.

## Code Context
Relevant Files:
- `src/main/java/com/javatech/security/config/SecurityConfig.java`

Relevant Pattern:
The pattern is to define beans for your user store (`UserDetailsService`), password hashing (`PasswordEncoder`), and the authentication logic (`AuthenticationProvider`). These beans are then wired together by Spring Security.

```java
// In SecurityConfig.java

// 1. Define a UserDetailsService bean
// @Bean
// public UserDetailsService userDetailsService() {
//     UserDetails user1 = User.withUsername("kiran")
//             .password("{bcrypt}$2a$12$...") // Password must be encoded
//             .roles("USER")
//             .build();
//     // ... more users
//     return new InMemoryUserDetailsManager(user1, user2);
// }

// 2. Define a PasswordEncoder bean
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(12);
}

// 3. Define the AuthenticationProvider
@Bean
public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userDetailsService()); // Wire the UserDetailsService
    provider.setPasswordEncoder(passwordEncoder()); // Wire the PasswordEncoder
    return provider;
}
```

*(Note: The `userDetailsService` is commented out as the lab code uses a custom implementation, but the concept of providing users is the same.)*

## Hands-On Tasks
1.  In `SecurityConfig.java`, create a `BCryptPasswordEncoder` bean.
2.  Create a custom `MyUserDetailsService` class that implements `UserDetailsService`.
3.  Inject a `UserRepo` to find users from the database.
4.  In `SecurityConfig.java`, define an `AuthenticationProvider` bean.
5.  Inside the provider bean, create a `DaoAuthenticationProvider`.
6.  Set your custom `UserDetailsService` and the `BCryptPasswordEncoder` bean on the provider.
7.  Run the application and try to log in with a user from your database.

## Common Errors

### Error: `java.lang.IllegalArgumentException: there is no PasswordEncoder mapped for the id "null"`
**Cause:**
- A `PasswordEncoder` bean has not been defined, or you are providing a plain-text password to the user builder without specifying an encoder.
**Fix:**
- Ensure a `PasswordEncoder` bean (e.g., `BCryptPasswordEncoder`) is defined in your `SecurityConfig`.
- When creating users, ensure their passwords are encoded, or use a `PasswordEncoder` to encode them before saving.

### Error: `UsernameNotFoundException`
**Cause:**
- The `UserDetailsService` implementation could not find a user with the provided username.
**Fix:**
- Verify that the user exists in your data store (in-memory or database).
- Check for typos in the username during login.

## Out of Scope
- Loading users from a relational database using `JdbcUserDetailsManager`. (The lab uses JPA, which is a more advanced concept covered implicitly).
- Method-level security (`@PreAuthorize`).
- JWT or token-based authentication.

## Reflection Prompts
- Why is `PasswordEncoder` a one-way process? What would be the risk if it were two-way?
- What is the exact responsibility of the `UserDetailsService`? What does it *not* do?
- How does the `DaoAuthenticationProvider` use both the `UserDetailsService` and `PasswordEncoder` to verify a user?

```json
{
  "day": "Day 2",
  "session": "Session 1",
  "topic": "Users and Authentication",
  "tags": ["spring-security", "userdetailsservice", "passwordencoder", "bcrypt", "authentication", "authenticationprovider"]
}
```