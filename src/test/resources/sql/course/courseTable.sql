DROP TABLE IF EXISTS courses;

CREATE TABLE IF NOT EXISTS courses
(
    id serial PRIMARY KEY,
    course_name varchar(50) NOT NULL,
    course_description varchar(100) NOT NULL
    );