CREATE SCHEMA IF NOT EXISTS enrollment;

CREATE TABLE enrollment.enrollments (
    id SERIAL PRIMARY KEY,
    student_id INTEGER NOT NULL,
    course_id INTEGER NOT NULL,
    course_code VARCHAR(10) NOT NULL,
    course_name VARCHAR(100) NOT NULL,
    schedule_days VARCHAR(100) NOT NULL,
    professor_name VARCHAR(100) NOT NULL,
    status VARCHAR(20) NOT NULL,
    enrolled_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    UNIQUE(student_id, course_id)
); 