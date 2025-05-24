# ✅ TODOs – Stack-Arc Project

This document tracks improvements and deferred features to be addressed after the core functionality is complete.

---

## 🖼️ Product Image Upload

- [ ] Add support for uploading product images using `MultipartFile`
- [ ] Store images in a dedicated `/product-images/` directory or external storage (e.g., Azure Blob Storage)
- [ ] Update `ProductDto`, `ProductService`, and entity accordingly

---

## 🔐 Role-Based Authorization Routing

- [ ] Refactor route structure:
    - Move role-protected endpoints to paths like `/api/admin/**`, `/api/manager/**`, etc.
- [ ] Replace `@PreAuthorize` annotations where appropriate with route-based restrictions

---

## 📦 Response Refactor (Generic DTO)

- [ ] Refactor `Response.kt` into a flexible `ApiResponse<T>` format
- [ ] Remove hardcoded fields like `user`, `product`, etc.
- [ ] Use `data` or `payload` to encapsulate response body cleanly

---

## 🪵 Exception Handling Enhancements

- [ ] Log stack traces in `GlobalExceptionHandler`
- [ ] Add support for:
    - `MethodArgumentNotValidException`
    - `ConstraintViolationException`
- [ ] Include field-level error feedback in response

---

## 🧪 Database Migration to MySQL

- [ ] When ready to deploy:
    - Switch from H2 to MySQL (Azure Database for MySQL recommended)
    - Update `application.properties` with connection string
    - Configure `spring.jpa.hibernate.ddl-auto` as needed
- [ ] Migrate data (if needed) from H2 to MySQL using tools like Flyway or manual export

---

## 🔐 H2 Console (Dev Only)

- [ ] Enable H2 console access in development mode
- [ ] Update Spring Security config to allow `/h2-console/**`
- [ ] Disable or restrict H2 in production environments

---

> ✨ Revisit this file after MVP completion to systematically apply enhancements.
