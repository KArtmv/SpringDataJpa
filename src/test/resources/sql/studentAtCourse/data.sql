INSERT INTO groups (group_name) values ('YS-27');
INSERT INTO groups (group_name) values ('HV-14');
INSERT INTO groups (group_name) values ('QM-09');

INSERT INTO students (first_name, last_name, group_id) values ('Liam', 'Jones', 2);
INSERT INTO students (first_name, last_name, group_id) values ('Harper', 'Robinson', 3);
INSERT INTO students (first_name, last_name, group_id) values ('Lucas', 'Thompson', 1);
INSERT INTO students (first_name, last_name, group_id) values ('Carter', 'Clark', 2);
INSERT INTO students (first_name, last_name, group_id) values ('Amelia', 'Martinez', 1);

INSERT INTO courses (course_name, course_description) values ('Principles of Economics', 'Learn about the fundamentals of economics');
INSERT INTO courses (course_name, course_description) values ('World History: Ancient Civilizations', 'Explore the history of ancient civilizations');
INSERT INTO courses (course_name, course_description) values ('Creative Writing Workshop', 'Develop your writing skills in a creative environment');

INSERT INTO studenttocourse (student_id, course_id) values (1, 1);
INSERT INTO studenttocourse (student_id, course_id) values (1, 2);
INSERT INTO studenttocourse (student_id, course_id) values (1, 3);

INSERT INTO studenttocourse (student_id, course_id) values (2, 3);

INSERT INTO studenttocourse (student_id, course_id) values (3, 1);
INSERT INTO studenttocourse (student_id, course_id) values (3, 2);

INSERT INTO studenttocourse (student_id, course_id) values (4, 1);
INSERT INTO studenttocourse (student_id, course_id) values (4, 2);
INSERT INTO studenttocourse (student_id, course_id) values (4, 3);

INSERT INTO studenttocourse (student_id, course_id) values (5, 2);

