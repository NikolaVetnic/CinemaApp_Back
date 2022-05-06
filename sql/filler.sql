SELECT * FROM role;
INSERT INTO `pris_cinema`.`role` (`id`, `role`) VALUES ('1', 'ADMIN');
INSERT INTO `pris_cinema`.`role` (`id`, `role`) VALUES ('2', 'EMPLOYEE');
INSERT INTO `pris_cinema`.`role` (`id`, `role`) VALUES ('3', 'USER');

SELECT * FROM genre;
INSERT INTO `pris_cinema`.`genre` (`id`, `genre`) VALUES ('1', 'ACTION');
INSERT INTO `pris_cinema`.`genre` (`id`, `genre`) VALUES ('2', 'COMEDY');
INSERT INTO `pris_cinema`.`genre` (`id`, `genre`) VALUES ('3', 'HORROR');
INSERT INTO `pris_cinema`.`genre` (`id`, `genre`) VALUES ('4', 'DRAMA');
INSERT INTO `pris_cinema`.`genre` (`id`, `genre`) VALUES ('5', 'THRILLER');

SELECT * FROM section;
INSERT INTO `pris_cinema`.`section` (`id`, `section`) VALUES ('1', 'GROUND_FLOOR');
INSERT INTO `pris_cinema`.`section` (`id`, `section`) VALUES ('2', 'GALLERY_LEFT');
INSERT INTO `pris_cinema`.`section` (`id`, `section`) VALUES ('3', 'GALLERY_RIGHT');

SELECT * FROM movie;
INSERT INTO `pris_cinema`.`movie` (`id`, `title`, `description`, `image`, `runtime`) VALUES
('1', 'Predator', 'Dutch and his team are out on a mission to rescue a group of hostages in Central America. There, they discover that they are being targeted by an extraterrestrial warrior.', 'https://upload.wikimedia.org/wikipedia/en/9/95/Predator_Movie.jpg', '107');
INSERT INTO `pris_cinema`.`movie` (`id`, `title`, `description`, `image`, `runtime`) VALUES
('2', 'Predator 2', 'Predator 2 is a 1990 American science fiction action film[3] written by brothers Jim and John Thomas, directed by Stephen Hopkins, and starring Danny Glover, Ruben Blades, Gary Busey, María Conchita Alonso, Bill Paxton, and Kevin Peter Hall.', 'https://upload.wikimedia.org/wikipedia/en/b/b5/Predator_two.jpg', '108');
INSERT INTO `pris_cinema`.`movie` (`id`, `title`, `description`, `image`, `runtime`) VALUES
('3', 'The Matrix', 'The Matrix is a 1999 science fiction action film[5][6] written and directed by the Wachowskis.', 'https://upload.wikimedia.org/wikipedia/en/c/c1/The_Matrix_Poster.jpg', '136');
INSERT INTO `pris_cinema`.`movie` (`id`, `title`, `description`, `image`, `runtime`) VALUES
('4', 'Dune', 'In the year 10191, a spice called melange is the most valuable substance known in the universe, and its only source is the desert planet Arrakis. ', 'https://upload.wikimedia.org/wikipedia/en/5/51/Dune_1984_Poster.jpg', '137');

SELECT * FROM movie_genre;
INSERT INTO `pris_cinema`.`movie_genre` (`movie_id`, `genre_id`) VALUES ('1', '1');
INSERT INTO `pris_cinema`.`movie_genre` (`movie_id`, `genre_id`) VALUES ('1', '3');
INSERT INTO `pris_cinema`.`movie_genre` (`movie_id`, `genre_id`) VALUES ('2', '1');
INSERT INTO `pris_cinema`.`movie_genre` (`movie_id`, `genre_id`) VALUES ('2', '3');
INSERT INTO `pris_cinema`.`movie_genre` (`movie_id`, `genre_id`) VALUES ('3', '1');
INSERT INTO `pris_cinema`.`movie_genre` (`movie_id`, `genre_id`) VALUES ('3', '3');
INSERT INTO `pris_cinema`.`movie_genre` (`movie_id`, `genre_id`) VALUES ('4', '1');
INSERT INTO `pris_cinema`.`movie_genre` (`movie_id`, `genre_id`) VALUES ('4', '3');

SELECT * FROM hall;
INSERT INTO `pris_cinema`.`hall` (`id`, `name`) VALUES ('1', 'Velika sala');
INSERT INTO `pris_cinema`.`hall` (`id`, `name`) VALUES ('2', 'Mala sala');

SELECT * FROM projection;
INSERT INTO `pris_cinema`.`projection` (`id`, `date_time`, `fee`, `hall_id`, `movie_id`) VALUES ('1', '20220426', '100', '1', '1');
INSERT INTO `pris_cinema`.`projection` (`id`, `date_time`, `fee`, `hall_id`, `movie_id`) VALUES ('2', '20220430', '200', '1', '2');
INSERT INTO `pris_cinema`.`projection` (`id`, `date_time`, `fee`, `hall_id`, `movie_id`) VALUES ('3', '20220501', '300', '1', '3');
INSERT INTO `pris_cinema`.`projection` (`id`, `date_time`, `fee`, `hall_id`, `movie_id`) VALUES ('4', '20220505', '400', '1', '4');
INSERT INTO `pris_cinema`.`projection` (`id`, `date_time`, `fee`, `hall_id`, `movie_id`) VALUES ('5', '20220505', '400', '1', '4');
INSERT INTO `pris_cinema`.`projection` (`id`, `date_time`, `fee`, `hall_id`, `movie_id`) VALUES ('6', '20220505', '100', '1', '1');

SELECT * FROM comment;
INSERT INTO `pris_cinema`.`comment` (`id`, `content`, `date_time`, `movie_id`, `user_id`) VALUES ('1', 'Do jaja film!', '2022-05-04T18:00', '1', '1');
INSERT INTO `pris_cinema`.`comment` (`id`, `content`, `date_time`, `movie_id`, `user_id`) VALUES ('2', 'Slabiji nego prvi ali dobar...', '2022-05-04T18:45', '2', '1');
INSERT INTO `pris_cinema`.`comment` (`id`, `content`, `date_time`, `movie_id`, `user_id`) VALUES ('3', 'Klasika!', '2022-05-04T19:00', '3', '1');
INSERT INTO `pris_cinema`.`comment` (`id`, `content`, `date_time`, `movie_id`, `user_id`) VALUES ('4', 'I shall not fear, fear is the mind killer...', '2022-05-04T20:30', '4', '1');