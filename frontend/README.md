# Frontend (Angular Application)

This is an Angular frontend application for managing categories (employee roles) and elements (employees). It communicates with the backend gateway to perform CRUD operations.

## Features

- **Categories (Employee Roles)**: List, create, edit, delete, and view details
- **Elements (Employees)**: List by category, create, edit, delete, and view details
- Full CRUD operations with form validation
- Routing with URL parameters for navigation

## Prerequisites

- Node.js (v18+)
- npm (v9+)
- Backend gateway running on port 8080 (default) with CORS enabled

## Installation

```bash
cd frontend
npm install
```

## Development Server

```bash
npm start
# or
ng serve --open
```

Navigate to `http://localhost:4200/`. The application will automatically reload on file changes.

## Configuration

### API Base URL

The API base URL is configured in `src/environments/environment.ts`:

```typescript
export const environment = {
  production: false,
  apiBaseUrl: 'http://localhost:8080'
};
```

To connect to a different gateway:
1. Edit `src/environments/environment.ts` for development
2. Edit `src/environments/environment.prod.ts` for production

### CORS Configuration

The gateway must have CORS enabled to allow requests from the Angular frontend. The gateway's `application.yml` includes CORS configuration for `http://localhost:4200`. If you're running the frontend on a different port or domain, update the gateway's CORS configuration accordingly.

## Build

```bash
npm run build
```

Build artifacts will be stored in `dist/frontend/`.

## Project Structure

```
src/app/
├── components/
│   ├── categories-list/     # List all categories
│   ├── category-form/       # Add/Edit category form
│   ├── category-details/    # Category details + elements list
│   ├── element-form/        # Add/Edit element form
│   └── element-details/     # Element details view
├── services/
│   ├── category.service.ts  # Category REST operations
│   └── element.service.ts   # Element REST operations
├── models/
│   ├── category.model.ts    # Category TypeScript interfaces
│   └── element.model.ts     # Element TypeScript interfaces
└── environments/
    ├── environment.ts       # Development config
    └── environment.prod.ts  # Production config
```

## Routes

| Path | Component | Description |
|------|-----------|-------------|
| `/categories` | CategoriesListComponent | List all categories |
| `/categories/add` | CategoryFormComponent | Add new category |
| `/categories/:id` | CategoryDetailsComponent | Category details + elements |
| `/categories/:id/edit` | CategoryFormComponent | Edit category |
| `/categories/:id/elements/add` | ElementFormComponent | Add new element |
| `/categories/:id/elements/:elementId` | ElementDetailsComponent | Element details |
| `/categories/:id/elements/:elementId/edit` | ElementFormComponent | Edit element |

## API Endpoints Used

- `GET /api/employee-roles` - List categories
- `POST /api/employee-roles` - Create category
- `GET /api/employee-roles/:id` - Get category
- `PUT /api/employee-roles/:id` - Update category
- `DELETE /api/employee-roles/:id` - Delete category
- `GET /api/employees/roles/:roleId` - List elements by category
- `GET /api/employees/:id` - Get element
- `POST /api/employees` - Create element
- `PUT /api/employees/:id` - Update element
- `DELETE /api/employees/:id` - Delete element

## This project was generated with

[Angular CLI](https://github.com/angular/angular-cli) version 18.2.21
