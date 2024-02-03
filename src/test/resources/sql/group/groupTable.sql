DROP TABLE IF EXISTS groups;

CREATE TABLE IF NOT EXISTS groups
(
    id serial PRIMARY KEY,
    group_name varchar(25) NOT NULL
    );