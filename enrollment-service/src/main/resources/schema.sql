CREATE TABLE IF NOT EXISTS enrollments (
    id SERIAL PRIMARY KEY,
    student_id INTEGER NOT NULL,
    course_id INTEGER NOT NULL,
    course_code VARCHAR(10) NOT NULL,
    course_name VARCHAR(100) NOT NULL,
    schedule_days VARCHAR(100),
    professor_name VARCHAR(100),
    status VARCHAR(20) NOT NULL,
    enrolled_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
); 