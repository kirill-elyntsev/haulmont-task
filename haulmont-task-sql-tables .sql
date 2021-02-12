create table genre
(id serial primary key,
 name varchar(30)
);

insert into genre
(name)
values
('comedy'),
('novella'),
('novel'),
('fantasy'),
('science fiction');


create table author (
id serial primary key,
name varchar (30),
surname varchar (30),
patronymic varchar(30)
);

insert into author
(name, surname, patronymic)
values
('Alexander', 'Griboyedov', 'Sergeyevich'),
('Ivan', 'Turgenev', 'Sergeyevich'),
('Mikhail', 'Bulgakov', 'Afanasyevich'),
('Lev', 'Tolstoy', 'Nikolayevich'),
('Arkady', 'Strugatsky', 'Natanovich'),
('Sergei', 'Lukyanenko', 'Vasilievich');

create table book
(id serial primary key,
 name varchar(100),
 author_id integer,
 genre_id integer,
 publisher varchar(50),
 year integer,
 city varchar(30),
 foreign key (author_id) references author(id),
 foreign key (genre_id) references genre(id)
);

insert into book
(name, author_id, genre_id, publisher, year, city)
values
(
'Woe from Wit',
1,
1,
'Moscow',
1985,
'Moscow'
),
(
'Crime and Punishment',
4,
3,
'Piter',
1984,
'Saint Petersburg'
),
(
'Demons',
4,
3,
'Piter',
1983,
'Saint Petersburg'
),
(
'The Brothers Karamazov',
4,
3,
'Moscow',
1988,
'Moscow'
),
(
'The Master and Margarita',
3,
4,
'Moscow',
1990,
'Moscow'
),
(
'Heart of a Dog',
3,
2,
'Moscow',
1993,
'Moscow'
),
(
'Fathers and Sons',
2,
3,
'Piter',
1993,
'Saint Petersburg'
),
(
'Hard to Be a God',
5,
5,
'Piter',
2001,
'Saint Petersburg'
),
(
'Prisoners of Power',
5,
5,
'Piter',
2002,
'Saint Petersburg'
),
(
'Night Watch',
6,
5,
'Moscow',
2002,
'Moscow'
);
