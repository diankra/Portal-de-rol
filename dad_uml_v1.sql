CREATE TYPE "tipo_ficha" AS ENUM (
  'jugador',
  'mundo'
);

CREATE TABLE "ficha" (
  "ficha_id" SERIAL PRIMARY KEY,
  "descripcion" varchar,
  "tipo" tipo_ficha
);

CREATE TABLE "mensaje" (
  "mensaje_id" SERIAL PRIMARY KEY,
  "texto" varchar,
  "fecha" timestamp,
  "autor" int
);

CREATE TABLE "usuario" (
  "usuario_id" SERIAL PRIMARY KEY,
  "nombre" varchar,
  "admin" boolean
);

CREATE TABLE "foro" (
  "foro_id" SERIAL PRIMARY KEY,
  "partida" boolean DEFAULT true,
  "mensajes" int,
  "admin" int
);

CREATE TABLE "partida" (
  "partida_id" int PRIMARY KEY,
  "jugadores" int,
  "fichas" int
);

CREATE TABLE "ficha_jugador" (
  "ficha_id" int PRIMARY KEY,
  "propietario" int
);

CREATE TABLE "ficha_mundo" (
  "ficha_id" int PRIMARY KEY
);

ALTER TABLE "mensaje" ADD FOREIGN KEY ("mensaje_id") REFERENCES "foro" ("mensajes");

ALTER TABLE "foro" ADD FOREIGN KEY ("admin") REFERENCES "usuario" ("usuario_id");

ALTER TABLE "partida" ADD FOREIGN KEY ("partida_id") REFERENCES "foro" ("foro_id");

ALTER TABLE "ficha_jugador" ADD FOREIGN KEY ("ficha_id") REFERENCES "ficha" ("ficha_id");

ALTER TABLE "ficha_mundo" ADD FOREIGN KEY ("ficha_id") REFERENCES "ficha" ("ficha_id");

ALTER TABLE "mensaje" ADD FOREIGN KEY ("autor") REFERENCES "usuario" ("usuario_id") ON DELETE CASCADE ON UPDATE NO ACTION;

ALTER TABLE "ficha_jugador" ADD FOREIGN KEY ("propietario") REFERENCES "usuario" ("usuario_id") ON DELETE CASCADE ON UPDATE NO ACTION;

ALTER TABLE "ficha_mundo" ADD FOREIGN KEY ("ficha_id") REFERENCES "partida" ("fichas") ON DELETE SET NULL ON UPDATE NO ACTION;

ALTER TABLE "usuario" ADD FOREIGN KEY ("usuario_id") REFERENCES "partida" ("jugadores") ON DELETE SET NULL ON UPDATE NO ACTION;
