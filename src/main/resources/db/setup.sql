create databases e_learning;

create user 'learner'@'localhost' identified by 'Password123';
grant all privileges e_learning.* to 'learner'@'localhost';
flush privileges