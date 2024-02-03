DROP TABLE IF EXISTS studenttocourse;
DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS courses;
DROP TABLE IF EXISTS groups;

CREATE TABLE IF NOT EXISTS groups
(
    id serial PRIMARY KEY,
    group_name varchar(25) NOT NULL
);

CREATE TABLE IF NOT EXISTS students
(
    id serial PRIMARY KEY,
    first_name varchar(15) NOT NULL,
    last_name varchar(20) NOT NULL,
    group_id integer,
    CONSTRAINT fk_student_group_id FOREIGN KEY (group_id)
        REFERENCES groups (id)
);

CREATE TABLE IF NOT EXISTS courses
(
    id serial PRIMARY KEY,
    course_name varchar(50) NOT NULL,
    course_description varchar(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS studenttocourse
(
    id SERIAL PRIMARY KEY,
    student_id INTEGER,
    course_id INTEGER,
    CONSTRAINT unique_course UNIQUE (student_id, course_id),
    CONSTRAINT fk_studenttocourse_course_id FOREIGN KEY (course_id)
        REFERENCES courses (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_studenttocourse_student_id FOREIGN KEY (student_id)
        REFERENCES students (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);