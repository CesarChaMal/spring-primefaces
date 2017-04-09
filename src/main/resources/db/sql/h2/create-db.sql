DROP TABLE TASK IF EXISTS;

CREATE TABLE TASK (
	id bigint PRIMARY KEY auto_increment,
  	title VARCHAR(40) not null,
  	description VARCHAR(256) not null,
  	due_date DATE not null
);
