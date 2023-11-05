-- Inserir autores
INSERT INTO `projeto`.`autor` (`name`) VALUES ('Jennifer Snicker');
INSERT INTO `projeto`.`autor` (`name`) VALUES ('August Cezar');
INSERT INTO `projeto`.`autor` (`name`) VALUES ('Andrils Belmont');

-- Inserir editoras
INSERT INTO `projeto`.`editora` (`name_fantasy`) VALUES ('Saraiva');
INSERT INTO `projeto`.`editora` (`name_fantasy`) VALUES ('Cisco');
INSERT INTO `projeto`.`editora` (`name_fantasy`) VALUES ('Gobb');

-- Inserir livros
INSERT INTO `projeto`.`book` (`title`, `year_publication`, `id_autor`, `id_editora`) VALUES ('Coraline', '2022-01-01', 1, 1);
INSERT INTO `projeto`.`book` (`title`, `year_publication`, `id_autor`, `id_editora`) VALUES ('Eu Robô', '2022-02-01', 2, 2);
INSERT INTO `projeto`.`book` (`title`, `year_publication`, `id_autor`, `id_editora`) VALUES ('Leviatãs', '2022-03-01', 3, 3);
