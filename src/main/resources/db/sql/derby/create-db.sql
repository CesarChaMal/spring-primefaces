CREATE TABLE TASK (
	id int PRIMARY KEY,
	title VARCHAR(40) not null,
	description VARCHAR(256) not null,
	due_date DATE not null
);
