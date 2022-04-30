INSERT INTO `pris_cinema`.`role` (`id`, `role`) VALUES ('1', 'ADMIN');
INSERT INTO `pris_cinema`.`role` (`id`, `role`) VALUES ('2', 'EMPLOYEE');
INSERT INTO `pris_cinema`.`role` (`id`, `role`) VALUES ('3', 'USER');

INSERT INTO `pris_cinema`.`genre` (`id`, `genre`) VALUES ('1', 'ACTION');
INSERT INTO `pris_cinema`.`genre` (`id`, `genre`) VALUES ('2', 'COMEDY');
INSERT INTO `pris_cinema`.`genre` (`id`, `genre`) VALUES ('3', 'HORROR');
INSERT INTO `pris_cinema`.`genre` (`id`, `genre`) VALUES ('4', 'DRAMA');
INSERT INTO `pris_cinema`.`genre` (`id`, `genre`) VALUES ('5', 'THRILLER');

INSERT INTO `pris_cinema`.`section` (`id`, `section`) VALUES ('1', 'GROUND_FLOOR');
INSERT INTO `pris_cinema`.`section` (`id`, `section`) VALUES ('2', 'GALLERY_LEFT');
INSERT INTO `pris_cinema`.`section` (`id`, `section`) VALUES ('3', 'GALLERY_RIGHT');

INSERT INTO `pris_cinema`.`movie` (`id`, `title`, `description`, `image`, `runtime`) VALUES
    ('1', 'Predator', 'Dutch and his team are out on a mission to rescue a group of hostages in Central America. There, they discover that they are being targeted by an extraterrestrial warrior.', 'https://upload.wikimedia.org/wikipedia/en/9/95/Predator_Movie.jpg', '107');
INSERT INTO `pris_cinema`.`movie` (`id`, `title`, `description`, `image`, `runtime`) VALUES
    ('2', 'Predator 2', 'Predator 2 is a 1990 American science fiction action film[3] written by brothers Jim and John Thomas, directed by Stephen Hopkins, and starring Danny Glover, Ruben Blades, Gary Busey, María Conchita Alonso, Bill Paxton, and Kevin Peter Hall.', 'https://upload.wikimedia.org/wikipedia/en/b/b5/Predator_two.jpg', '108');
INSERT INTO `pris_cinema`.`movie` (`id`, `title`, `description`, `image`, `runtime`) VALUES
    ('3', 'The Matrix', 'The Matrix is a 1999 science fiction action film[5][6] written and directed by the Wachowskis.', 'https://upload.wikimedia.org/wikipedia/en/c/c1/The_Matrix_Poster.jpg', '136');
INSERT INTO `pris_cinema`.`movie` (`id`, `title`, `description`, `image`, `runtime`) VALUES
    ('4', 'Dune', 'In the year 10191, a spice called melange is the most valuable substance known in the universe, and its only source is the desert planet Arrakis. ', 'https://upload.wikimedia.org/wikipedia/en/5/51/Dune_1984_Poster.jpg', '137');

INSERT INTO `pris_cinema`.`projection` (`id`, `date_time`, `fee`, `hall`, `movie`) VALUES ('1', '20220426', '100', '5', '1');
INSERT INTO `pris_cinema`.`projection` (`id`, `date_time`, `fee`, `hall`, `movie`) VALUES ('2', '20220430', '200', '5', '2');
INSERT INTO `pris_cinema`.`projection` (`id`, `date_time`, `fee`, `hall`, `movie`) VALUES ('3', '20220501', '300', '5', '3');
INSERT INTO `pris_cinema`.`projection` (`id`, `date_time`, `fee`, `hall`, `movie`) VALUES ('4', '20220505', '400', '5', '4');
