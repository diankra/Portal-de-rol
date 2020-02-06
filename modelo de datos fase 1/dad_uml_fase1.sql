CREATE TABLE `ficha` (
  `ficha_id` int PRIMARY KEY AUTO_INCREMENT,
  `descripcion` varchar(255),
  `tipo` ENUM ('jugador', 'mundo')
);

CREATE TABLE `mensaje` (
  `mensaje_id` int PRIMARY KEY AUTO_INCREMENT,
  `texto` varchar(255),
  `fecha` timestamp,
  `autor` int
);

CREATE TABLE `foro` (
  `foro_id` int PRIMARY KEY AUTO_INCREMENT,
  `hilos` int,
  `admin` int
);

CREATE TABLE `usuario` (
  `usuario_id` int PRIMARY KEY AUTO_INCREMENT,
  `nombre` varchar(255),
  `admin` boolean,
  `fichas` int
);

CREATE TABLE `admin` (
  `admin_id` int PRIMARY KEY
);

CREATE TABLE `hilo` (
  `hilo_id` int PRIMARY KEY AUTO_INCREMENT,
  `mensajes` int
);

CREATE TABLE `partida` (
  `partida_id` int PRIMARY KEY,
  `master` int,
  `jugadores` int,
  `fichas` int
);

CREATE TABLE `ficha_jugador` (
  `ficha_id` int PRIMARY KEY
);

CREATE TABLE `ficha_mundo` (
  `ficha_id` int PRIMARY KEY
);

ALTER TABLE `admin` ADD FOREIGN KEY (`admin_id`) REFERENCES `usuario` (`usuario_id`);

ALTER TABLE `mensaje` ADD FOREIGN KEY (`mensaje_id`) REFERENCES `hilo` (`mensajes`);

ALTER TABLE `partida` ADD FOREIGN KEY (`partida_id`) REFERENCES `hilo` (`hilo_id`);

ALTER TABLE `ficha_jugador` ADD FOREIGN KEY (`ficha_id`) REFERENCES `ficha` (`ficha_id`);

ALTER TABLE `ficha_mundo` ADD FOREIGN KEY (`ficha_id`) REFERENCES `ficha` (`ficha_id`);

ALTER TABLE `mensaje` ADD FOREIGN KEY (`autor`) REFERENCES `usuario` (`usuario_id`) ON DELETE CASCADE ON UPDATE NO ACTION;

ALTER TABLE `ficha_mundo` ADD FOREIGN KEY (`ficha_id`) REFERENCES `partida` (`fichas`) ON DELETE SET NULL ON UPDATE NO ACTION;

ALTER TABLE `usuario` ADD FOREIGN KEY (`usuario_id`) REFERENCES `partida` (`jugadores`) ON DELETE SET NULL ON UPDATE NO ACTION;

ALTER TABLE `partida` ADD FOREIGN KEY (`master`) REFERENCES `usuario` (`usuario_id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `hilo` ADD FOREIGN KEY (`hilo_id`) REFERENCES `foro` (`hilos`) ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE `ficha_jugador` ADD FOREIGN KEY (`ficha_id`) REFERENCES `usuario` (`fichas`) ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE `admin` ADD FOREIGN KEY (`admin_id`) REFERENCES `foro` (`admin`);
