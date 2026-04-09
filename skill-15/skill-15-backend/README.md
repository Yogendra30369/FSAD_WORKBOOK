# Skill 15 - JWT Authentication and Role Authorization

This backend implements JWT-based authentication and RBAC for:
- ADMIN endpoints: `/admin/add`, `/admin/delete`
- EMPLOYEE endpoint: `/employee/profile`

## Implemented Tasks

1. User entity with username, password, role
2. `/login` endpoint that generates JWT token
3. Spring Security configuration with JWT filter
4. Secured endpoints:
   - `POST /admin/add`
   - `DELETE /admin/delete`
   - `GET /employee/profile`
5. Authentication and authorization tests for valid/invalid/missing JWT
6. Postman collection included in `postman/exp15-jwt-rbac.postman_collection.json`

## Default Users

- ADMIN
  - username: `admin`
  - password: `admin123`
- EMPLOYEE
  - username: `employee`
  - password: `emp123`

Users are seeded at application startup.

## Run Backend

```bash
./mvnw spring-boot:run
```

Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

## Run Tests

```powershell
.\mvnw.cmd test
```

## Manual API Testing (without Postman)

### Login
`POST /login`

```json
{
  "username": "admin",
  "password": "admin123"
}
```

### Access Employee Profile
`GET /employee/profile`
Header: `Authorization: Bearer <token>`

### Add Employee (ADMIN only)
`POST /admin/add?employeeName=john`
Header: `Authorization: Bearer <admin-token>`

### Delete Employee (ADMIN only)
`DELETE /admin/delete?employeeName=john`
Header: `Authorization: Bearer <admin-token>`

## Expected Authorization Behavior

- No token: secured endpoints are denied
- Invalid token: secured endpoints are denied
- EMPLOYEE token:
  - `/employee/profile`: allowed
  - `/admin/add`, `/admin/delete`: denied
- ADMIN token:
  - all above secured endpoints: allowed

## Viva Questions (Quick Answers)

1. What is JWT?
   - JWT (JSON Web Token) is a compact signed token format that carries identity claims (like username and role). Server verifies signature to trust the claims.

2. How does Spring validate tokens?
   - A JWT filter extracts bearer token, verifies signature and expiration, loads user details, and sets authentication in the security context.

3. Authentication vs Authorization?
   - Authentication checks who the user is (login). Authorization checks what the user can access (roles/permissions).

4. Why are roles needed?
   - Roles enforce least-privilege access control, so users can only access endpoints allowed for their responsibilities.

5. What happens when a token expires?
   - Token validation fails; request is rejected; user must login again to receive a new token.
