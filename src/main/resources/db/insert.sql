SET FOREIGN_KEY_CHECKS = 0;

truncate table learning_party;
truncate table instructor;
truncate table authority;
truncate table course;
truncate table student;

--
-- insert into student (id, first_name, last_name,gender)
-- values(110,'Damilare', 'Jolayemi', 'MALE'),
--       (111,'John', 'Doe', 'MALE'),
--       (112,'Jane', 'Doe', 'FEMALE'),
--       (113,'Felicia', 'Parker', 'FEMALE');
--
--
-- insert into instructor (id, first_name, last_name, specialization, bio)
-- values(114,'Damilare', 'Jolayemi', 'Software Engineering', 'Lorem Ipsum'),
--       (115,'John', 'Doe', 'Teaching', 'Lorem Ipsum'),
--       (116,'Jane', 'Doe', 'Diving', 'Lorem ipsum'),
--       (117,'Felicia', 'Parker', 'Content creation', 'lorem ipsum');
--
insert into learning_party (id, email, password,enabled)
values(118,'dummy@gmail.com', 'password123', true),
      (119,'dummy1@gmail.com', 'password123', false);

-- insert into course (id, title, is_published,duration, description, language)
-- values(120,'DSA', true, '3 months', 'lorem ipsum', 'French'),
--       (121,'Introduction to programming', false, '3 weeks', 'lorem ipsum', 'English');
--

SET FOREIGN_KEY_CHECKS = 1;