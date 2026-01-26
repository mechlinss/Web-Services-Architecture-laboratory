CREATE TABLE IF NOT EXISTS employees (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    salary DOUBLE PRECISION NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    employee_role_id UUID,
    employee_role_name VARCHAR(255),
    employee_role_department VARCHAR(255)
);
