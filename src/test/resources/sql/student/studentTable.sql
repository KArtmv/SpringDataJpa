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