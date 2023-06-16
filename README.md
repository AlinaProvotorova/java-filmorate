# java-filmorate

Приложение для общения и взаимодействия пользователей.

# База данных

![Базза данных для проекта](media/img.png)

## Возможности приложения

### ***Фильмы***

- Список всех фильмов
```
GET /films
```
- Получение фильма по id
```
GET /films/{id}
```
- Создание фильма
```
POST /films
```
- Изменение фильма
```
PUT /films
```
- Удаление фильма
```
DELETE /films/{id}
```
- Поставить лайк фильму
```
PUT /films/{id}/like/{userId}
```
- Поставить дислайк фильму
```
DELETE /{id}/like/{userId}
```
### ***Общие фильмы***
- Вывод общих фильмов пользователя и его друга с сортировкой по их популярности.
```
 GET /films/common?userId={userId}&friendId={friendId} 
 ```
### ***Поиск***
-  поиск по названию фильмов и по режиссёру.
Алгоритм умеет искать по подстроке. Например, вы вводите «крад», а в поиске возвращаются следующие фильмы: «Крадущийся тигр, затаившийся дракон», «Крадущийся в ночи» и другие.
```
GET /films/search?query=крад&by=director,title
```
### ***Популярный фильмы***
- Вывод топ-N фильмов по количеству лайков с возможностью указать жанр и год выхода фильма
```
GET /films/popular?count={limit}&genreId={genreId}&year={year}
 ```
### ***Фильмы по режиссёрам***
- Получение списка фильмов режиссера отсортированных по количеству лайков или году выпуска.
```
GET /films/director/{directorId}?sortBy=[year,likes]
```
- Список всех режиссёров
```
GET /directors
```
- Получение режиссёра по id
```
GET /directors/{id}
```
- Создание режиссёра
```
POST /directors
```
- Изменение режиссёра
```
PUT /directors
```
- Удаление режиссёра
```
DELETE /directors/{id}
```

### ***Отзывы на фильмы***

Добавленные отзывы имеют рейтинг и несколько дополнительных характеристик:

- При создании отзыва рейтинг равен нулю.
- Оценка — полезно/бесполезно.
- Тип отзыва — негативный/положительный.

Если пользователь оценил отзыв как полезный, это увеличивает его рейтинг на 1. Если как бесполезный, то уменьшает на 1.

Отзывы сортируются по рейтингу полезности.

- Добавление нового отзыва.
```
POST /reviews
```
- Получение отзыва по идентификатору.
```
GET /reviews/{id}
```
- Получение всех отзывов по идентификатору фильма, если фильм не указан то все. Если кол-во не указано то 10.
```
GET /reviews?filmId={filmId}&count={count}
```
- Редактирование уже имеющегося отзыва.
```
PUT /reviews
```
- Пользователь ставит лайк отзыву.
```
PUT /reviews/{id}/like/{userId}
```
- Пользователь ставит дизлайк отзыву.
```
PUT /reviews/{id}/dislike/{userId}
```
- Пользователь удаляет лайк отзыву.
```
DELETE /reviews/{id}/like/{userId}
```
- Пользователь удаляет дизлайк отзыву.
```
DELETE /reviews/{id}/dislike/{userId}
```
- Удаление уже имеющегося отзыва.
```
DELETE /reviews/{id}
```
### ***Пользователи***

- Получение всех пользователей.
```
GET /users
```
- Добавление нового пользователя.
```
POST /users
```
- Получение пользователя по идентификатору.
```
GET /users/{id}
```
- Редактирование уже имеющегося пользоватля.
```
PUT /users
```
- Удаление пользователя.
```
DELETE /users/{id}
```
### ***Лента событий пользователя***
- Просмотр последний событий пользователя на платформе - добавление/удаление друзей, лайков и отзывов.
```
 GET /users/{id}/feed 
 ```
### ***Рекомендации***
- Рекомендательную систему для фильмов.
  Находит пользователей с максимальным количеством пересечения по лайкам.
  Определяет фильмы, которые один пролайкал, а другой нет.
  Рекомендует фильмы, которым поставил лайк пользователь с похожими вкусами, а тот, для кого составляется рекомендация, ещё не поставил.

```
 GET /users/{id}/recommendations
 ```
### ***Сервис друзей***
- Получение всех друзей пользователя.
```
 GET /users/{id}/friends 
 ```
- Получение общих друзей с другим пользоватеелем.
```
 GET /users/{id}/friends/common/{otherId}
 ```
- Получение входящих заявок в друзья
```
 GET /users/{id}/friends/incoming
 ```
- Получение исходящих заявок в друзья
```
 GET /users/{id}/friends/outgoing
 ```
- Отправка заявки в друзья.
```
 PUT /users/{id}/friends/outgoing
 ```
- Принятие заявки в друзья.
```
 PUT /users/{id}/friends/reject/{friendId}
 ```
- Удаление друга.
```
 DELETE /users/{id}/friends/{friendId}
 ```
### ***Жанры***

- Получение всех жанров.
```
GET /genres
```
- Добавление нового жанра.
```
POST /genres
```
- Получение жанра по идентификатору.
```
GET /genres/{id}
```
- Редактирование уже имеющегося жанра.
```
PUT /genres
```
- Удаление жанра.
```
DELETE /genres/{id}
```
- Получение жанров по фильму.
```
DELETE /genres/film/{id}
```
### ***Рейтинг***

- Получение всех рейтингов.
```
GET /mpa
```
- Добавление нового рейтинга.
```
POST /mpa
```
- Получение рейтинга по идентификатору.
```
GET /mpa/{id}
```
- Редактирование уже имеющегося рейтинга.
```
PUT /mpa
```
- Удаление рейтинга.
```
DELETE /mpa/{id}
```







