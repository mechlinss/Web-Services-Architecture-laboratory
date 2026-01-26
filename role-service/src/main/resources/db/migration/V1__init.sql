CREATE TABLE IF NOT EXISTS employee_roles (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(1000),
    department VARCHAR(255)
);
