create table groups
(
    id         serial,
    group_name varchar(25) not null,
    primary key (id)
);

create table students
(
    id         serial,
    first_name varchar(15) not null,
    last_name  varchar(20) not null,
    group_id   integer,
    primary key (id)
);

create table courses
(
    id                 serial,
    course_name        varchar(50)  not null,
    course_description varchar(100) not null,
    primary key (id)
);

create table student_course
(
    student_id bigint not null,
    course_id  bigint not null,
    constraint unique_course
        primary key (student_id, course_id),
    constraint fkowhsvsa3u070c23mmqdp5mqpy
        foreign key (student_id) references students,
    constraint fk7ofybdo2o0ngc4de3uvx4dxqv
        foreign key (course_id) references courses
);