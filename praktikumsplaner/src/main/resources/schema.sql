DROP TABLE IF EXISTS tutor_termin;
DROP TABLE IF EXISTS ubungswoche_config;

DROP TABLE IF EXISTS student;
DROP TABLE IF EXISTS gruppe;
DROP TABLE IF EXISTS zeitslot;
DROP TABLE IF EXISTS wochenbelegung;
DROP TABLE IF EXISTS orga_member;

DROP TABLE IF EXISTS roles;

CREATE TABLE ubungswoche_config
(
    id             integer   NOT NULL PRIMARY KEY AUTO_INCREMENT,
    anmeldestart   TIMESTAMP NOT NULL,
    anmeldeschluss TIMESTAMP NOT NULL,
    name           varchar   NOT NULL,
    modus           integer   NOT NULL,
    min_personen    integer   NOT NULL,
    max_personen    integer   NOT NUll
);

CREATE TABLE tutor_termin
(
    id                            integer   NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name                          varchar   NOT NULL,
    zeit                          TIMESTAMP NOT NULL,
    ubungswoche_config integer references ubungswoche_config (id)
);

CREATE TABLE roles
(
    role varchar NOT NULL PRIMARY KEY
);

CREATE TABLE wochenbelegung
(
    id integer NOT NULL PRIMARY KEY AUTO_INCREMENT
);

CREATE TABLE zeitslot
(
    id             integer   NOT NULL PRIMARY KEY AUTO_INCREMENT,
    ubungs_anfang  TIMESTAMP NOT NULL,
    min_personen   integer   NOT NULL,
    max_personen   integer   NOT NULL,
    wochenbelegung integer references wochenbelegung (id)
);

CREATE TABLE gruppe
(
    id           integer NOT NULL PRIMARY KEY AUTO_INCREMENT,
    gruppen_name varchar,
    tutoren_name varchar NOT NULL,
    zeitslot     integer references zeitslot (id)
);

CREATE TABLE student
(
    github_handle varchar NOT NULL PRIMARY KEY,
    gruppe        integer references gruppe (id)
);

CREATE TABLE orga_member
(
    github_handle varchar NOT NULL PRIMARY KEY,
    role          varchar references roles (role)
);
