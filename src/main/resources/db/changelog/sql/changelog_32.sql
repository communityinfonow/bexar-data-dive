--liquibase formatted sql

--changeset herronrb:last_translated_content
select * from tbl_indicators where description_es = '' and description_en is not null;

update tbl_filter_options set name_es = 'Indígena Americano o Nativo de Alaska ' where id_ = 100;
update tbl_filter_options set name_es = 'Asiático' where id_ = 101;
update tbl_filter_options set name_es = 'Negro o Afroamericano ' where id_ = 102;
update tbl_filter_options set name_es = 'Hispano o Latino' where id_ = 103;
update tbl_filter_options set name_es = 'Nativo de Hawái y Otras Islas del Pacífico ' where id_ = 104;
update tbl_filter_options set name_es = 'Otra raza ' where id_ = 105;
update tbl_filter_options set name_es = 'Dos o Más Razas ' where id_ = 106;
update tbl_filter_options set name_es = 'Blanco (no hispano)' where id_ = 107;

update tbl_filter_options set name_es = 'Masculino' where id_ = 200;
update tbl_filter_options set name_es = 'Femenino' where id_ = 201;

update tbl_filter_options set name_es = 'Menores de 5 años' where id_ = 300;
update tbl_filter_options set name_es = '5 a 9 años' where id_ = 301;
update tbl_filter_options set name_es = '10 a 14 años' where id_ = 302;
update tbl_filter_options set name_es = '15 a 17 años' where id_ = 303;
update tbl_filter_options set name_es = '18 a 19 años' where id_ = 304;
update tbl_filter_options set name_es = '20 a 24 años' where id_ = 305;
update tbl_filter_options set name_es = '25 a 29 años' where id_ = 306;
update tbl_filter_options set name_es = '30 a 34 años' where id_ = 307;
update tbl_filter_options set name_es = '35 a 44 años' where id_ = 308;
update tbl_filter_options set name_es = '45 a 54 años' where id_ = 309;
update tbl_filter_options set name_es = '55 a 64 años' where id_ = 310;
update tbl_filter_options set name_es = '65 a 74 años' where id_ = 311;
update tbl_filter_options set name_es = '75 a 84 años' where id_ = 312;
update tbl_filter_options set name_es = '85 años y mayores' where id_ = 313;
update tbl_filter_options set name_es = '5 a 17 años' where id_ = 314;
update tbl_filter_options set name_es = '18 a 34 años' where id_ = 315;
update tbl_filter_options set name_es = '35 a 64 años' where id_ = 316;
update tbl_filter_options set name_es = '18 a 24 años' where id_ = 317;
update tbl_filter_options set name_es = '25 a 34 años' where id_ = 318;
update tbl_filter_options set name_es = '45 a 64 años' where id_ = 319;
update tbl_filter_options set name_es = '65 años y mayores' where id_ = 320;
update tbl_filter_options set name_es = 'Menores de 18 años' where id_ = 321;
update tbl_filter_options set name_es = '18 a 64 años' where id_ = 322;
update tbl_filter_options set name_es = 'Menores de 25 años' where id_ = 323;
update tbl_filter_options set name_es = '25 a 44 años' where id_ = 324;
update tbl_filter_options set name_es = '15 a 24 años' where id_ = 325;
update tbl_filter_options set name_es = '16 a 19 años' where id_ = 326;
update tbl_filter_options set name_es = 'Menores de 19 años' where id_ = 327;
update tbl_filter_options set name_es = '19 a 64 años' where id_ = 328;

update tbl_filter_options set name_es = 'Educación menor a un título secundario' where id_ = 400;
update tbl_filter_options set name_es = 'Graduado con título secundario (incluye equivalencia)' where id_ = 401;
update tbl_filter_options set name_es = 'Algunos estudios universitarios o un título de asociado' where id_ = 402;
update tbl_filter_options set name_es = 'Título de licenciatura universitaria' where id_ = 403;
update tbl_filter_options set name_es = 'Maestría o un título profesional' where id_ = 404;
update tbl_filter_options set name_es = 'Título de licenciatura universitaria o superior' where id_ = 405;

update tbl_filter_options set name_es = 'Debajo del umbral de pobreza' where id_ = 500;
update tbl_filter_options set name_es = 'Menos del 200 por ciento de pobreza' where id_ = 501;
update tbl_filter_options set name_es = 'Menos del 300 por ciento de pobreza' where id_ = 502;
update tbl_filter_options set name_es = 'En o por encima del umbral de pobreza' where id_ = 503;

update tbl_indicators set description_es = '<p><span class="font-weight-bold">Definición: </span>Porcentaje de la población de 1 año o más que se mudó en el último año.</p><p><span class="font-weight-bold">Notas sobre la Información: </span>Los porcentajes estimados que se presentan son los declarados (ver Fuentes de Información). Los márgenes de error asociados con cada estimación son reportados como límites altos o bajos. Las siguientes clasificaciones especiales son definidas y valuadas como:<ul><li>0.0 - la información está disponible y el porcentaje es menor a 0.1; valuado como 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Estimaciones de 5 años según la Encuesta sobre la Comunidad Estadounidense de la Oficina del Censo (USCB ACS, por sus siglas en inglés), B07004 A-I</p>' where id_ = 113;

update tbl_location_types set name_es = 'Condados' where id_ = 1;

update tbl_locations set name_es = 'Condado de Bexar' where id_ = '48029';
update tbl_locations set name_es = 'Distrito de Ayuntamiento ' || id_ where location_type_id = 2;
update tbl_locations set name_es = 'Código Postal ' || id_ where location_type_id = 3;
update tbl_locations set name_es = 'SSA ' || id_ where location_type_id = 4;
update tbl_locations set name_es = replace(name_en, 'Census Tract', 'Áreas de Censo') where location_type_id = 5;

