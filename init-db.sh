#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    -- Create schemas
    CREATE SCHEMA IF NOT EXISTS auth;
    CREATE SCHEMA IF NOT EXISTS enrollment;
    CREATE SCHEMA IF NOT EXISTS courses;
    CREATE SCHEMA IF NOT EXISTS grades;

    -- Set search path for auth schema
    SET search_path TO auth;

    -- Create tables in auth schema
    CREATE TABLE IF NOT EXISTS roles (
        id serial PRIMARY KEY,
        name VARCHAR(50) UNIQUE NOT NULL
    );

    CREATE TABLE IF NOT EXISTS users (
        id serial PRIMARY KEY,
        username VARCHAR(50) UNIQUE NOT NULL,
        email VARCHAR(100) UNIQUE NOT NULL,
        password VARCHAR(120) NOT NULL,
        first_name VARCHAR(50) NOT NULL,
        last_name VARCHAR(50) NOT NULL,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

    CREATE TABLE IF NOT EXISTS user_roles (
        user_id INTEGER REFERENCES users(id),
        role_id INTEGER REFERENCES roles(id),
        PRIMARY KEY (user_id, role_id)
    );

    -- Insert default roles
    INSERT INTO roles (name) VALUES
        ('STUDENT'),
        ('FACULTY'),
        ('ADMIN')
    ON CONFLICT (name) DO NOTHING;

    -- Create an admin user with password 'admin123'
    INSERT INTO users (username, email, password, first_name, last_name)
    VALUES (
        'admin',
        'admin@enrollment.com',
        '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi',
        'System',
        'Administrator'
    ) ON CONFLICT (username) DO NOTHING;

    -- Assign admin role to admin user
    INSERT INTO user_roles (user_id, role_id)
    SELECT u.id, r.id
    FROM users u, roles r
    WHERE u.username = 'admin' AND r.name = 'ADMIN'
    ON CONFLICT DO NOTHING;
EOSQL 