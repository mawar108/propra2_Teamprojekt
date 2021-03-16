insert into ubungswoche_config (anmeldestart, anmeldeschluss, name, modus, min_personen, max_personen)
values ('2021-03-14 07:05', '2021-03-20 07:05', 'Pue 8', 1, 3, 5);
insert into tutor_termin (name, zeit, ubungswoche_config)
values ('Max', '2021-03-20 07:05', 1);
insert into tutor_termin (name, zeit, ubungswoche_config)
values ('Dieter', '2021-03-20 09:05', 1);
insert into tutor_termin (name, zeit, ubungswoche_config)
values ('Peter', '2021-03-20 09:05', 1);

insert into zeitslot (ubungs_anfang, min_personen, max_personen, ubungswoche_config)
values ('2021-03-20 07:05', '3', '5', 1);
insert into zeitslot (ubungs_anfang, min_personen, max_personen, ubungswoche_config)
values ('2021-03-20 09:05', '3', '5', 1);

insert into gruppe (gruppen_name, tutoren_name, zeitslot)
values ('Gruppe 1', 'Max', 1);
insert into student (github_handle, gruppe)
values ('abc', 1);

insert into gruppe (gruppen_name, tutoren_name, zeitslot)
values ('Gruppe 2', 'Dieter', 2);
insert into gruppe (gruppen_name, tutoren_name, zeitslot)
values ('Gruppe 3', 'Peter', 2);
