DROP TABLE IF EXISTS tutor_termin;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS praktische_ubungswoche_config;


CREATE TABLE praktische_ubungswoche_config
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
    id                          integer   NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name                        varchar   NOT NULL,
    zeit                        TIMESTAMP NOT NULL,
    praktische_ubungswoche_config integer references praktische_ubungswoche_config (id)
);

CREATE TABLE roles
(
    id   integer NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name varchar NOT NULL,
    role varchar NOT NULL
);
