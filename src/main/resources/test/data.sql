delete from friendship;
delete from film_genre;
delete from likes_film;
delete from film;
alter table film alter COLUMN id RESTART with 1;
delete from users;
alter table users alter COLUMN id RESTART with 1;

delete from reviews;
alter table reviews alter COLUMN id RESTART with 1;

insert into users (login, email, name, birthday) values ('Alina', 'alina@y.ru', 'Алина', '1997-01-14');
insert into users (login, email, name, birthday) values ('Stas', 'stas@y.ru', 'Стас', '1997-03-15');
insert into users (login, email, name, birthday) values ('Ylia', 'ylia@y.ru', 'Юля', '1997-03-16');
insert into users (login, email, name, birthday) values ('Ivan', 'ivan@y.ru', 'Иван', '1997-03-17');
insert into users (login, email, name, birthday) values ('Andrey', 'andrey@y.ru', 'Андрей', '1997-03-18');

--Делаем друзьями 1 и 2 пользователя
insert into FRIENDSHIP (USER_ID, FRIEND_ID, IS_ACCEPTED) values (1, 2, TRUE);
INSERT INTO FRIENDSHIP (USER_ID, FRIEND_ID, IS_ACCEPTED) VALUES (2, 1, TRUE);
-- Делаем друзьями 1 и 5 пользователя
insert into FRIENDSHIP (USER_ID, FRIEND_ID, IS_ACCEPTED) values (1, 5, TRUE);
INSERT INTO FRIENDSHIP (USER_ID, FRIEND_ID, IS_ACCEPTED) VALUES (5, 1, TRUE);
-- Делаем друзьями 2 и 5 пользователя
insert into FRIENDSHIP (USER_ID, FRIEND_ID, IS_ACCEPTED) values (2, 5, TRUE);
INSERT INTO FRIENDSHIP (USER_ID, FRIEND_ID, IS_ACCEPTED) VALUES (5, 2, TRUE);
-- Отправляем заявки в друзья 3 и 4 пользователю
insert into FRIENDSHIP (USER_ID, FRIEND_ID) values (1, 3);
insert into FRIENDSHIP (USER_ID, FRIEND_ID) values (1, 4);
insert into FRIENDSHIP (USER_ID, FRIEND_ID) values (2, 4);

insert into film (name, release_date, description, duration, rating_id)
values ('Зеленая миля','1999-01-01','Зеленая миля','189',1);
insert into film (name, release_date, description, duration, rating_id)
values ('Побег из Шоушенка','1994-01-01','Побег из Шоушенка','142',2);
insert into film (name, release_date, description, duration, rating_id)
values ('Форрест Гамп','1994-01-01','Форрест Гамп','141',3);
insert into film (name, release_date, description, duration, rating_id)
values ('Тайна Коко','2017-01-01','Тайна Коко','105',4);
insert into film (name, release_date, description, duration, rating_id)
values ('Властелин колец: Возвращение короля','2003-01-01','Властелин колец: Возвращение короля','201',5);

-- Содаем лайки фильмам
insert into likes_film (film_id, user_id) values (1, 1);
insert into likes_film (film_id, user_id) values (1, 2);
insert into likes_film (film_id, user_id) values (1, 3);
insert into likes_film (film_id, user_id) values (1, 4);
insert into likes_film (film_id, user_id) values (1, 5);

insert into likes_film (film_id, user_id) values (5, 1);
insert into likes_film (film_id, user_id) values (5, 2);
insert into likes_film (film_id, user_id) values (5, 3);
insert into likes_film (film_id, user_id) values (5, 4);

insert into likes_film (film_id, user_id) values (3, 1);
insert into likes_film (film_id, user_id) values (3, 2);
insert into likes_film (film_id, user_id) values (3, 3);

insert into likes_film (film_id, user_id) values (2, 1);
insert into likes_film (film_id, user_id) values (2, 2);

insert into likes_film (film_id, user_id) values (4, 2);


insert into film_genre (film_id, genre_id) values (1, 2);
insert into film_genre (film_id, genre_id) values (1, 4);
insert into film_genre (film_id, genre_id) values (2, 3);
insert into film_genre (film_id, genre_id) values (3, 1);
insert into film_genre (film_id, genre_id) values (3, 6);
insert into film_genre (film_id, genre_id) values (4, 5);
insert into film_genre (film_id, genre_id) values (4, 2);
insert into film_genre (film_id, genre_id) values (5, 2);
insert into film_genre (film_id, genre_id) values (5, 1);
insert into film_genre (film_id, genre_id) values (5, 6);

