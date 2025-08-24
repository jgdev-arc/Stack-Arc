# <div align="center">🧱 **Stack Arc – Backend (Kotlin + Spring Boot)** 🧱</div>

A clean, production-ready REST API for inventory management. Built with **Kotlin + Spring Boot**, ships JWT auth, role-based access control, DTOs, and modular services. Deployed on **Azure App Service**, developed locally with **H2**, and ready for **MySQL** in prod.

---

<p align="center">
  <i>“Fast CRUD, sensible security, clean DTOs.”</i>
</p>

## :camera_flash: Features

<img width="1536" height="1024" alt="architecture" src="https://github.com/user-attachments/assets/d7ef6fce-0348-4fcb-b026-5437dea00090" />


- **JWT Auth + RBAC** – Login returns a JWT; routes gated by roles (`ADMIN`, `USER`).  
- **Domain Model** – Users, Products, Categories, Suppliers, Transactions (purchase/sell), Status, Types.
- **DTOs + Validation** – Request/Response DTOs with Bean Validation for clean inputs.
- **Manual Mapping** – Explicit mapping between entities and DTOs (no magic mappers).
- **Search & Paging** – Server-side filtering (e.g., `/transactions?q=...`) with consistent responses.
- **Azure-Ready** – Honors `PORT`/`WEBSITES_PORT` and plays nice with SWA frontend + CORS.

---


## :earth_americas: Try It

If you’re here to **use the app**, head to the **Azure Frontend** (React app):  
👉 **Open the UI:** https://calm-pond-0fe4c9410.2.azurestaticapps.net/

Use the UI to register, log in, and interact with the API—no local setup needed.

---

## :white_check_mark: Responses & Conventions

Success responses include a status and message, and where useful, DTO payloads.

Auth responses return token, role, and a user DTO.

Registration returns 201 Created; duplicate emails return 409 Conflict.

Errors use sensible messages (e.g., “Email Not Found”, “Password does not match”).

---

## :shield: Security Highlights

Never trust the client for roles – RegisterRequest does not accept role.

Admin-only role changes guarded by @PreAuthorize("hasRole('ADMIN')").

“Last admin” guard – Repo includes countByRole to prevent lockout.

No sensitive logging – User.toString() does not include password hash.

---

## :triangular_ruler: Project Structure 

# **Stack Arc — Backend Project Structure (Compact)**

```text
backend/
├─ .github/
│  └─ workflows/
├─ src/
│  ├─ main/
│  │  ├─ kotlin/
│  │  │  └─ com/
│  │  │     └─ tlz/
│  │  │        └─ stackarc/
│  │  │           ├─ controllers/
│  │  │           ├─ services/
│  │  │           │  └─ impl/
│  │  │           ├─ models/
│  │  │           ├─ dtos/
│  │  │           ├─ repositories/
│  │  │           ├─ enums/
│  │  │           ├─ security/
│  │  │           ├─ config/
│  │  │           └─ mappers/
│  │  └─ resources/
│  └─ test/
│     └─ kotlin/
│        └─ com/
│           └─ tlz/
│              └─ stackarc/
```

---

# :memo: Authors :memo:
- [@jgdev-arc](https://github.com/jgdev-arc)
