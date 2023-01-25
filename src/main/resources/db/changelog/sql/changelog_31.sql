--liquibase formatted sql

--changeset herronrb:more_translated_content
update tbl_location_types set name_es = 'Distritos de Ayuntamiento ' where id_ = 2;
update tbl_location_types set name_es = 'Código Postal' where id_ = 3;
update tbl_location_types set name_es = 'SSA' where id_ = 4;
update tbl_location_types set name_es = 'Áreas de Censo' where id_ = 5;

update tbl_indicators set name_es = 'Se mudó en el último año' where id_ = 113;
update tbl_indicators set name_es = 'Mala salud física por >=14 días entre adultos' where id_ = 105;
update tbl_indicators set name_es = 'Diversidad racial/étnica (Índice de Theil)' where id_ = 37;
update tbl_indicators set name_es = 'Computadora y suscripción de banda ancha' where id_ = 46;

update tbl_filter_types set name_es = 'Raza o Etnia' where id_ = 1;
update tbl_filter_types set name_es = 'Edad' where id_ = 2;
update tbl_filter_types set name_es = 'Sexo' where id_ = 3;
update tbl_filter_types set name_es = 'Nivel Educativo' where id_ = 4;
update tbl_filter_types set name_es = 'Nivel de Ingresos' where id_ = 5;

update tbl_sources set name_es = 'Encuesta sobre la Comunidad Estadounidense (ACS)' where id_ = 1;
update tbl_sources set name_es = 'Dtto. de Tasación del Condado de Bexar (BCAD)' where id_ = 2;
update tbl_sources set name_es = 'Elecciones del Condado de Bexar' where id_ = 3;
update tbl_sources set name_es = 'Condado de Bexar' where id_ = 4;
update tbl_sources set name_es = 'Centros para el Control y la Prevención de Enfermedades (CDC)' where id_ = 5;
update tbl_sources set name_es = 'Ciudad de San Antonio' where id_ = 6;
update tbl_sources set name_es = 'Dpto. de Servicios de Vivienda y Vecindario (COSA NHSD)' where id_ = 7;
update tbl_sources set name_es = 'Censo Decenal' where id_ = 8;
update tbl_sources set name_es = 'El Instrumento de Desarrollo Temprano (EDI)' where id_ = 9;
update tbl_sources set name_es = 'Agencia de Protección Ambiental de Estados Unidos (USEPA)' where id_ = 10;
update tbl_sources set name_es = 'Buró Federal de Investigaciones (FBI)' where id_ = 11;
update tbl_sources set name_es = 'C3 Intercambio de Información de Salud (C3HIE)' where id_ = 12;
update tbl_sources set name_es = 'Comisión de Salud y Servicios Humanos de Texas (THHS)' where id_ = 13;
update tbl_sources set name_es = 'Dpto. de Vivienda y Desarrollo Urbano (HUD)' where id_ = 14;
update tbl_sources set name_es = 'Dinámicas Longitudinales Empleador-Hogar (LEHD) del Censo' where id_ = 15;
update tbl_sources set name_es = 'Informe Nacional de Estadísticas Vitales' where id_ = 16;
update tbl_sources set name_es = 'Base de Datos Nacional de Conservación de la Vivienda (NHPD)' where id_ = 17;
update tbl_sources set name_es = 'CDC PLACES, BRFSS (nivel del condado)' where id_ = 18;
update tbl_sources set name_es = 'Dtto. Metropolitano de Salud de la Ciudad de San Antonio (SAMHD)' where id_ = 19;
update tbl_sources set name_es = 'Dpto. de Policía de la Ciudad de San Antonio' where id_ = 20;
update tbl_sources set name_es = 'Dpto. de Servicios para la Familia y de Protección de Texas (TDFPS)' where id_ = 21;
update tbl_sources set name_es = 'Dpto. de Seguridad Pública de Texas (TDPS)' where id_ = 22;
update tbl_sources set name_es = 'Dpto. Estatal de Servicios de Salud de Texas (TDSHS)' where id_ = 23;
update tbl_sources set name_es = 'PUDF de altas hospitalarias de THCIC' where id_ = 24;
update tbl_sources set name_es = 'United Way de San Antonio y el Condado de Bexar' where id_ = 25;
update tbl_sources set name_es = 'Oficina del Censo de Estados Unidos (USCB)' where id_ = 26;
update tbl_sources set name_es = 'Atlas de Alimentos del Dpto. de Agricultura de Estados Unidos (USDA)' where id_ = 27;