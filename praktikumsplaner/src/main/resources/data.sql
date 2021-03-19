insert into ubungswoche_config (anmeldestart, anmeldeschluss, name, modus, min_personen, max_personen, repos_erstellt)
values ('2000-03-14 07:05', '2020-03-20 15:50', 'Pue 8', 0, 3, 5, false);
-- insert into ubungswoche_config (anmeldestart, anmeldeschluss, name, modus, min_personen, max_personen, repos_erstellt)
-- values ('2021-03-14 07:05', '2020-03-17 15:50', 'Pue 8', 0, 3, 5, false);
--
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
values ('Gruppe Test3', 'Max', 1);
insert into gruppe (gruppen_name, tutoren_name, zeitslot)
values ('Gruppe 10', 'Dieter', 2);
insert into gruppe (gruppen_name, tutoren_name, zeitslot)
values ('Gruppe 9', 'Peter', 2);
insert into gruppe (gruppen_name, tutoren_name, zeitslot)
values ('Gruppe Test2', 'Peter2', 1);

insert into angemeldeter_student (github_handle, zeitslot)
values ('mawar108', 1);
insert into angemeldeter_student (github_handle, zeitslot)
values ('Nina181', 1);
insert into angemeldeter_student (github_handle, zeitslot)
values ('Couraxe', 1);
insert into angemeldeter_student (github_handle, zeitslot)
values ('Christopher-Schmitz', 1);

