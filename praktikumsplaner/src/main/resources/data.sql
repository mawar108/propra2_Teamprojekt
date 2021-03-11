insert into praktische_ubungswoche_config (anmeldestart, anmeldeschluss, name, modus, min_personen, max_personen)
values ('2020-01-01 07:05', '2020-01-09 07:05', 'Pue 8', 1, 3, 5);
insert into tutor_termin (name, zeit, praktische_ubungswoche_config)
values ('Max', '2020-01-09 07:05', 1);

insert into wochenbelegung default values;

insert into zeitslot (ubungs_anfang, min_personen, max_personen, wochenbelegung)
values ('2020-01-01 07:05', '10', '20', 1);
insert into zeitslot (ubungs_anfang, min_personen, max_personen, wochenbelegung)
values ('2020-05-01 10:05', '10', '20', 1);

insert into gruppe (gruppen_name, tutoren_name, zeitslot)
values ('Gruppe 1','peter', 1);
insert into student (github_handle, gruppe)
values ('abc', 1);

insert into gruppe (gruppen_name, tutoren_name, zeitslot)
values ('Gruppe 2','hans', 1);
insert into gruppe (gruppen_name, tutoren_name, zeitslot)
values ('Gruppe 3','dieter', 2);
