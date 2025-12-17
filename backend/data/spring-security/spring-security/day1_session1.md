# Session Metadata
Day: Day 1
Session: Session 1
Topic: Spring Security Basics
Difficulty: Beginner
Duration: 45 minutes

## Learning Objectives
By the end of this session, the learner should be able to:
- Explain the need for web application security.
- Add the Spring Security dependency to a project.
- Describe the "secure by default" behavior after adding the dependency.
- Observe the auto-generated login page and basic logout functionality.
- Understand the high-level concept of a servlet filter chain.

## Key Concepts
- **Default "Deny-All" Policy**: When Spring Security is on the classpath, it automatically secures all application endpoints. No request is permitted without authentication.
- **Auto-Configuration**: Spring Boot automatically configures a basic but complete security setup. This includes a login page, a default user, and session management.
- **Servlet Filter Chain**: Spring Security is a series of servlet filters. Each incoming request passes through this chain, where security rules (like checking for authentication) are applied.

## Code Context
Relevant Files:
- `pom.xml`

Relevant Pattern:
The only change required is adding the `spring-boot-starter-security` dependency to the `pom.xml`. This single dependency triggers all of Spring Security's default behaviors.

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

## Hands-On Tasks
1.  Add the `spring-boot-starter-security` dependency to your `pom.xml`.
2.  Start the application. Note the auto-generated security password printed in the console logs.
3.  Open a web browser and navigate to any endpoint in your application (e.g., `http://localhost:8080/hello`).
4.  Observe that you are redirected to an auto-generated login page.
5.  Log in using the username `user` and the password from the console.
6.  After logging in, confirm you can now access the endpoint.
7.  Navigate to `http://localhost:8080/logout` to invalidate the session.

## Common Errors

### Error: Cannot find password in console
**Cause:**
- The console buffer is too small, or logs are scrolling too fast.
**Fix:**
- Stop the application. In your `application.properties` file, add `spring.security.user.name=myuser` and `spring.security.user.password=mypassword` to set a predictable username and password. Restart the application.

### Error: All my pages show a login screen
**Cause:**
- This is the expected default behavior. Spring Security protects everything until you explicitly configure it otherwise.
**Fix:**
- This is not an error for this session. In the next session, you will learn how to selectively expose certain endpoints.

## Out of Scope
- Customizing the login page.
- Creating custom Java configuration (`SecurityConfig`).
- Defining your own users or roles.
- JWT, OAuth2, or any other authentication mechanism besides the default.
- Disabling security for any endpoint.

## Reflection Prompts
- What is the first thing you notice after adding the security starter and running the app?
- Where did the login page and the password come from?
- What problem does a "secure by default" approach solve?

```json
{
  "day": "Day 1",
  "session": "Session 1",
  "topic": "Spring Security Basics",
  "tags": ["spring-security", "starters", "auto-configuration", "filter-chain"]
}
```