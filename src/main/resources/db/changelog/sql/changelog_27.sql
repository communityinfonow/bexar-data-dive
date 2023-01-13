--liquibase formatted sql

--changeset herronrb:espanol_3

update tbl_indicators
set description_es = '<p><span class="font-weight-bold">Definición: </span>Porcentaje de adultos que declaran dormir habitualmente de forma insuficiente (&lt;7 horas para adultos &ge; de 18 años de edad, de media, durante un periodo de 24 horas)</p><p><span class="font-weight-bold">Notas sobre la Información: </span>Los porcentajes estimados que se presentan son los declarados (ver Fuentes de Información). Los márgenes de error asociados con cada estimación son reportados como límites altos o bajos. Las siguientes clasificaciones especiales son definidas y valuadas como:<ul><li>0.0 - la información está disponible y el porcentaje es menor a 0.1; valuado como 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Centros para el Control y la Prevención de Enfermedades (CDC, por sus siglas en inglés): PLACES</p>',
	name_es = 'Dormir menos de siete horas en adultos'
where id_ = 78; -- Sleeping less than seven hours among adults

update tbl_indicators
set description_es = '<p><span class="font-weight-bold">Definición: </span>Porcentaje de adultos que declaran haberse sometido a un control de colesterol en los últimos 5 años.</p><p><span class="font-weight-bold">Notas sobre la Información: </span>Los porcentajes estimados que se presentan son los declarados (ver Fuentes de Información). Los márgenes de error asociados con cada estimación son reportados como límites altos o bajos. Las siguientes clasificaciones especiales son definidas y valuadas como:<ul><li>0.0 - la información está disponible y el porcentaje es menor a 0.1; valuado como 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Centros para el Control y la Prevención de Enfermedades (CDC, por sus siglas en inglés): PLACES</p>',
	name_es = 'Detección de colesterol en adultos'
where id_ = 79; -- Cholesterol screening among adults

update tbl_indicators
set description_es = '<p><span class="font-weight-bold">Definición: </span>Porcentaje de la población de 5 años o más que está totalmente vacunada y tiene una sola dosis de refuerzo.</p><p><span class="font-weight-bold">Notas sobre la Información: </span>Los porcentajes estimados que se presentan son los declarados (ver Fuentes de Información). Los márgenes de error asociados con cada estimación son reportados como límites altos o bajos. Las siguientes clasificaciones especiales son definidas y valuadas como:<ul><li>0.0 - la información está disponible y el porcentaje es menor a 0.1; valuado como 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Distrito Metropolitano de Salud de la Ciudad de San Antonio (SAMHD, por sus siglas en inglés)</p>',
	name_es = 'Tasas de vacunación de COVID-19'
where id_ = 80; -- COVID-19 vaccination rates

update tbl_indicators
set description_es = '<p><span class="font-weight-bold">Definición: </span>Porcentaje de adultos que tienen un índice de masa corporal (IMC) ≥30.0 kg/m<sup>2</sup> calculado a partir del peso y la altura autodeclarados.</p><p><span class="font-weight-bold">Notas sobre la Información: </span>Los porcentajes estimados que se presentan son los declarados (ver Fuentes de Información). Los márgenes de error asociados con cada estimación son reportados como límites altos o bajos. Las siguientes clasificaciones especiales son definidas y valuadas como:<ul><li>0.0 - la información está disponible y el porcentaje es menor a 0.1; valuado como 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Centros para el Control y la Prevención de Enfermedades (CDC, por sus siglas en inglés): PLACES</p>',
	name_es = 'Obesidad en adultos'
where id_ = 81; -- Obesity among adults

update tbl_indicators
set description_es = '<p><span class="font-weight-bold">Definición: </span>Porcentaje de mujeres de 65 años o más que declaran haber recibido todo lo siguiente: una vacuna contra la gripe en el último año; una vacuna antineumocócica (PPV) alguna vez; una prueba de sangre oculta en heces (FOBT) en el último año, una sigmoidoscopia en los últimos 5 años y una FOBT en los últimos 3 años, o una colonoscopia en los últimos 10 años; y una mamografía en los últimos 2 años. En el caso de los hombres, es el porcentaje de personas de 65 años o más que declaran haber recibido todo lo siguiente: una vacuna contra la gripe en el último año; una PPV alguna vez; y una prueba de sangre oculta en heces (FOBT) en el último año, una sigmoidoscopia en los últimos 5 años y una FOBT en los últimos 3 años, o una colonoscopia en los últimos 10 años.</p><p><span class="font-weight-bold">Notas sobre la Información: </span>Los porcentajes estimados que se presentan son los declarados (ver Fuentes de Información). Los márgenes de error asociados con cada estimación son reportados como límites altos o bajos. Las siguientes clasificaciones especiales son definidas y valuadas como:<ul><li>0.0 - la información está disponible y el porcentaje es menor a 0.1; valuado como 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Centros para el Control y la Prevención de Enfermedades (CDC, por sus siglas en inglés): PLACES</p>',
	name_es = 'Adultos mayores de 65 años que están al día en un conjunto básico de servicios clínicos preventivos'
where id_ = 82; -- Older adults aged 65 years and older who are up to date on a core set of clinical preventive services

update tbl_indicators
set description_es = '<p><span class="font-weight-bold">Definición: </span>Tasa de opioides prescritos por cada 100,000 personas.</p><p><span class="font-weight-bold">Notas sobre la Información: </span>Los porcentajes estimados que se presentan son los declarados (ver Fuentes de Información). Las siguientes clasificaciones especiales son definidas y valuadas como:<ul><li>0.0 - la información está disponible y el porcentaje es menor a 0.1; valuado como 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Departamento Estatal de Servicios de Salud de Texas</p>',
	name_es = 'Tasa de prescripción de opiáceos (cada 100,000)'
where id_ = 83; -- Opioid prescription rate (per 100K)

update tbl_indicators
set description_es = '<p><span class="font-weight-bold">Definición: </span>Porcentaje de mujeres de 21 a 65 años que no declaran haberse sometido a una histerectomía y que declaran haberse sometido a la prueba de detección del cáncer de cuello de útero recomendada. Para las encuestadas de 21 a 29 años, la prueba de detección recomendada es la prueba de Papanicolaou sola. Para las encuestadas de 30 a 65 años, hay tres pruebas de detección recomendadas con distintas frecuencias: (1) la prueba de Papanicolaou sola, (2) la prueba del virus del papiloma humano (VPH) sola, o (3) la prueba de Papanicolaou en combinación con la prueba del VPH.</p><p><span class="font-weight-bold">Notas sobre la Información: </span>Los porcentajes estimados que se presentan son los declarados (ver Fuentes de Información). Los márgenes de error asociados con cada estimación son reportados como límites altos o bajos. Las siguientes clasificaciones especiales son definidas y valuadas como:<ul><li>0.0 - la información está disponible y el porcentaje es menor a 0.1; valuado como 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Centros para el Control y la Prevención de Enfermedades (CDC, por sus siglas en inglés): PLACES</p>',
	name_es = 'Detección de cáncer de cuello uterino en mujeres adultas de 21 a 65 años'
where id_ = 84; -- Cervical cancer Pap screening among adult women aged 21-65 years

update tbl_indicators
set description_es = '<p><span class="font-weight-bold">Definición: </span>Porcentaje de adultos que declaran haber fumado 100 cigarrillos o más en su vida y que actualmente fuman todos los días o algunos días.</p><p><span class="font-weight-bold">Notas sobre la Información: </span>Los porcentajes estimados que se presentan son los declarados (ver Fuentes de Información). Los márgenes de error asociados con cada estimación son reportados como límites altos o bajos. Las siguientes clasificaciones especiales son definidas y valuadas como:<ul><li>0.0 - la información está disponible y el porcentaje es menor a 0.1; valuado como 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Centros para el Control y la Prevención de Enfermedades (CDC, por sus siglas en inglés): PLACES</p>',
	name_es = 'Tabaquismo actual en adultos'
where id_ = 85; -- Current smoking among adults

update tbl_indicators
set description_es = '<p><span class="font-weight-bold">Definición: </span>Porcentaje de mujeres que comenzaron a recibir atención prenatal en el primer o segundo trimestre.</p><p><span class="font-weight-bold">Notas sobre la Información: </span>Los porcentajes estimados que se presentan son los declarados (ver Fuentes de Información). Los márgenes de error asociados con cada estimación son reportados como límites altos o bajos. Las siguientes clasificaciones especiales son definidas y valuadas como:<ul><li>0.0 - la información está disponible y el porcentaje es menor a 0.1; valuado como 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Distrito Metropolitano de Salud de la Ciudad de San Antonio (SAMHD, por sus siglas en inglés)</p>',
	name_es = 'Atención Prenatal temprana'
where id_ = 86; -- Early Prenatal care

update tbl_indicators
set description_es = '<p><span class="font-weight-bold">Definición: </span>Porcentaje de adultos que declaran haber ido al dentista o a una clínica dental en el año anterior.</p><p><span class="font-weight-bold">Notas sobre la Información: </span>Los porcentajes estimados que se presentan son los declarados (ver Fuentes de Información). Los márgenes de error asociados con cada estimación son reportados como límites altos o bajos. Las siguientes clasificaciones especiales son definidas y valuadas como:<ul><li>0.0 - la información está disponible y el porcentaje es menor a 0.1; valuado como 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Centros para el Control y la Prevención de Enfermedades (CDC, por sus siglas en inglés): PLACES</p>',
	name_es = 'Visitas al dentista o a la clínica dental en adultos'
where id_ = 87; -- Visits to dentist or dental clinic among adults

update tbl_indicators
set description_es = '<p><span class="font-weight-bold">Definición: </span>Porcentaje de adultos que declaran haber acudido a un médico para una revisión rutinaria (por ejemplo, un examen físico general, no un examen por una lesión, enfermedad o afección específica) en el año anterior.</p><p><span class="font-weight-bold">Notas sobre la Información: </span>Los porcentajes estimados que se presentan son los declarados (ver Fuentes de Información). Los márgenes de error asociados con cada estimación son reportados como límites altos o bajos. Las siguientes clasificaciones especiales son definidas y valuadas como:<ul><li>0.0 - la información está disponible y el porcentaje es menor a 0.1; valuado como 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Centros para el Control y la Prevención de Enfermedades (CDC, por sus siglas en inglés): PLACES</p>',
	name_es = 'Visitas al médico para chequeo rutinario en el último año en adultos'
where id_ = 88; -- Visits to doctor for routine checkup within the past year among adults

update tbl_indicators
set description_es = '<p><span class="font-weight-bold">Definición: </span>Tasa de personas hospitalizadas con un diagnóstico primario de asma, por cada 10,000 admisiones.</p><p><span class="font-weight-bold">Notas sobre la Información: </span>Los porcentajes estimados que se presentan son los declarados (ver Fuentes de Información). Las siguientes clasificaciones especiales son definidas y valuadas como:<ul><li>0.0 - la información está disponible y el porcentaje es menor a 0.1; valuado como 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Departamento Estatal de Servicios de Salud de Texas - PUDF de Altas Hospitalarias de THCIC</p>',
	name_es = 'Tasa de hospitalización por asma - promedio de 3 años (cada 10,000)'
where id_ = 89; -- Asthma hospitalization (per 10K)

update tbl_indicators
set description_es = '<p><span class="font-weight-bold">Definición: </span>Tasa de nacimientos por cada 1,000 personas de 15 a 44 años.</p><p><span class="font-weight-bold">Notas sobre la Información: </span>Los porcentajes estimados que se presentan son los declarados (ver Fuentes de Información). Las siguientes clasificaciones especiales son definidas y valuadas como:<ul><li>0.0 - la información está disponible y el porcentaje es menor a 0.1; valuado como 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Distrito Metropolitano de Salud de la Ciudad de San Antonio (SAMHD, por sus siglas en inglés)</p>',
	name_es = 'Tasas de nacimiento (cada 1,000 edad 15-44)'
where id_ = 90; -- Birth rates (per 1K age 15-44)

update tbl_indicators
set description_es = '<p><span class="font-weight-bold">Definición: </span>Porcentaje de adultos que declaran haber sido informados alguna vez por un médico, una enfermera u otro profesional sanitario de que tenían angina de pecho o enfermedad coronaria.</p><p><span class="font-weight-bold">Notas sobre la Información: </span>Los porcentajes estimados que se presentan son los declarados (ver Fuentes de Información). Los márgenes de error asociados con cada estimación son reportados como límites altos o bajos. Las siguientes clasificaciones especiales son definidas y valuadas como:<ul><li>0.0 - la información está disponible y el porcentaje es menor a 0.1; valuado como 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Centros para el Control y la Prevención de Enfermedades (CDC, por sus siglas en inglés): PLACES</p>',
	name_es = 'Enfermedades coronarias en adultos'
where id_ = 91; -- Coronary heart disease among adults

update tbl_indicators
set description_es = '<p><span class="font-weight-bold">Definición: </span>Tasa de personas con COVID-19, por cada 100,000 personas.</p><p><span class="font-weight-bold">Notas sobre la Información: </span>Los porcentajes estimados que se presentan son los declarados (ver Fuentes de Información). Las siguientes clasificaciones especiales son definidas y valuadas como:<ul><li>0.0 - la información está disponible y el porcentaje es menor a 0.1; valuado como 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Distrito Metropolitano de Salud de la Ciudad de San Antonio (SAMHD, por sus siglas en inglés)</p>',
	name_es = 'Tasa de casos COVID-19 (cada 100,000)'
where id_ = 92; -- COVID-19 case rate (per 100K)

update tbl_indicators
set description_es = '<p><span class="font-weight-bold">Definición: </span>Tasa de personas que murieron como resultado de COVID-19, por cada 100,000 personas.</p><p><span class="font-weight-bold">Notas sobre la Información: </span>Los porcentajes estimados que se presentan son los declarados (ver Fuentes de Información). Las siguientes clasificaciones especiales son definidas y valuadas como:<ul><li>0.0 - la información está disponible y el porcentaje es menor a 0.1; valuado como 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Distrito Metropolitano de Salud de la Ciudad de San Antonio (SAMHD, por sus siglas en inglés)</p>',
	name_es = 'Tasa de mortalidad COVID-19 (cada 100,000)'
where id_ = 93; -- COVID-19 death rate (per 100K)

update tbl_indicators
set description_es = '<p><span class="font-weight-bold">Definición: </span>Tasa de muertes, por cada 100,000 personas.</p><p><span class="font-weight-bold">Notas sobre la Información: </span>Los porcentajes estimados que se presentan son los declarados (ver Fuentes de Información). Las siguientes clasificaciones especiales son definidas y valuadas como:<ul><li>0.0 - la información está disponible y el porcentaje es menor a 0.1; valuado como 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Departamento Estatal de Servicios de Salud de Texas</p>',
	name_es = 'Tasa de mortalidad (cada 100,000)'
where id_ = 94; -- Death rate (per 100K)

update tbl_indicators
set description_es = '<p><span class="font-weight-bold">Definición: </span>Tasa de personas hospitalizadas con un diagnóstico primario de diabetes, por cada 10,000 admisiones.</p><p><span class="font-weight-bold">Notas sobre la Información: </span>Los porcentajes estimados que se presentan son los declarados (ver Fuentes de Información). Las siguientes clasificaciones especiales son definidas y valuadas como:<ul><li>0.0 - la información está disponible y el porcentaje es menor a 0.1; valuado como 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Departamento Estatal de Servicios de Salud de Texas - PUDF de Altas Hospitalarias de THCIC</p>',
	name_es = 'Tasa de hospitalización por diabetes - promedio de 3 años (cada 10,000)'
where id_ = 95; -- Diabetes hospitalization rate (per 10K)

update tbl_indicators
set description_es = '<p><span class="font-weight-bold">Definición: </span>Porcentaje de adultos que declaran haber sido informados alguna vez por un médico, una enfermera u otro profesional sanitario de que tienen diabetes, distinta de la diabetes durante el embarazo.</p><p><span class="font-weight-bold">Notas sobre la Información: </span>Los porcentajes estimados que se presentan son los declarados (ver Fuentes de Información). Los márgenes de error asociados con cada estimación son reportados como límites altos o bajos. Las siguientes clasificaciones especiales son definidas y valuadas como:<ul><li>0.0 - la información está disponible y el porcentaje es menor a 0.1; valuado como 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Centros para el Control y la Prevención de Enfermedades (CDC, por sus siglas en inglés): PLACES</p>',
	name_es = 'Diabetes en adultos'
where id_ = 96; -- Diabetes among adults

update tbl_indicators
set description_es = '<p><span class="font-weight-bold">Definición: </span>Porcentaje de personas con una discapacidad. La Encuesta del Censo Americano recoge seis aspectos de discapacidad: auditiva, visual, cognitiva, ambulatoria, de autocuidado y de vida independiente. Para más información, visite <a target="_blank" href="https://www.census.gov/quickfacts/fact/note/US/DIS010220">https://www.census.gov/quickfacts/fact/note/US/DIS010220</a>.</p><p><span class="font-weight-bold">Notas sobre la Información: </span>Los porcentajes estimados que se presentan son los declarados (ver Fuentes de Información). Los márgenes de error asociados con cada estimación son reportados como límites altos o bajos. Las siguientes clasificaciones especiales son definidas y valuadas como:<ul><li>0.0 - la información está disponible y el porcentaje es menor a 0.1; valuado como 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Estimaciones de 5 años según la Encuesta sobre la Comunidad Estadounidense de la Oficina del Censo (USCB ACS, por sus siglas en inglés), B18101 A-I</p>',
	name_es = 'Situación de discapacidad'
where id_ = 97; -- Disability status

update tbl_indicators
set description_es = '<p><span class="font-weight-bold">Definición: </span>Tasa de personas hospitalizadas con un diagnóstico primario de hipertensión, por cada 10,000 personas.</p><p><span class="font-weight-bold">Notas sobre la Información: </span>Los porcentajes estimados que se presentan son los declarados (ver Fuentes de Información). Las siguientes clasificaciones especiales son definidas y valuadas como:<ul><li>0.0 - la información está disponible y el porcentaje es menor a 0.1; valuado como 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Departamento Estatal de Servicios de Salud de Texas - PUDF de Altas Hospitalarias de THCIC</p>',
	name_es = 'Tasa de hospitalización por hipertensión - promedio de 3 años (cada 10,000)'
where id_ = 98; -- Hypertension hospitalization rate - 3 year average (per 10K)

update tbl_indicators
set description_es = '<p><span class="font-weight-bold">Definición: </span>Llamamos a esto tasa de hospitalización por lesión para abreviar, pero estos indicadores en realidad reflejan las altas hospitalarias por lesión, no las admisiones. Las tasas están determinadas por las hospitalizaciones por la enfermedad o lesión como diagnóstico principal, no por todas las altas hospitalarias con ese diagnóstico. Las tasas no son la prevalencia o la incidencia de la enfermedad o lesión. Estos recuentos de hospitalizaciones tampoco son visitas o personas únicas. Si la misma persona en 78204 va al hospital tres veces por el mismo diagnóstico en 2019, entonces se incluyen las tres visitas si el asma fue el diagnóstico principal del ingreso durante ese año.</p><p><span class="font-weight-bold">Notas sobre la Información: </span>Los porcentajes estimados que se presentan son los declarados (ver Fuentes de Información). Las siguientes clasificaciones especiales son definidas y valuadas como:<ul><li>0.0 - la información está disponible y el porcentaje es menor a 0.1; valuado como 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Departamento Estatal de Servicios de Salud de Texas - PUDF de Altas Hospitalarias de THCIC</p>',
	name_es = 'Tasa de hospitalización por lesiones - promedio de 3 años (cada 10,000)'
where id_ = 99; -- Injury hospitalization rate - 3 year average (per 10K)

update tbl_indicators
set description_es = '<p><span class="font-weight-bold">Definición: </span>Tasa de personas diagnosticadas con cáncer invasivo, por cada 10,000 personas.</p><p><span class="font-weight-bold">Notas sobre la Información: </span>Los porcentajes estimados que se presentan son los declarados (ver Fuentes de Información). Las siguientes clasificaciones especiales son definidas y valuadas como:<ul><li>0.0 - la información está disponible y el porcentaje es menor a 0.1; valuado como 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Centros para el Control y la Prevención de Enfermedades (CDC, por sus siglas en inglés)</p>',
	name_es = 'Tasa de incidencia de cáncer invasivo (cada 10,000)'
where id_ = 100; -- Invasive cancer incidence rate (per 10K)

update tbl_indicators
set description_es = '<p><span class="font-weight-bold">Definición: </span>Tasa de personas diagnosticadas con envenenamiento por plomo o plomo elevado en la sangre, por cada 10,000 personas.</p><p><span class="font-weight-bold">Notas sobre la Información: </span>Los porcentajes estimados que se presentan son los declarados (ver Fuentes de Información). Las siguientes clasificaciones especiales son definidas y valuadas como:<ul><li>0.0 - la información está disponible y el porcentaje es menor a 0.1; valuado como 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Departamento Estatal de Servicios de Salud de Texas (Texas DSHS, por sus siglas en inglés)</p>',
	name_es = 'Intoxicación por plomo/plomo elevado en sangre (cada 10,000)'
where id_ = 101; -- Lead poisoning/elevated blood lead (per 10K)

update tbl_indicators
set description_es = '<p><span class="font-weight-bold">Definición: </span>El número medio de años que una persona puede esperar vivir. Para más información, visite <a target="_blank" href="https://www.cdc.gov/nchs/nvss/life-expectancy.htm">https://www.cdc.gov/nchs/nvss/life-expectancy.htm</a>.</p><p><span class="font-weight-bold">Notas sobre la Información: </span>Los porcentajes estimados que se presentan son los declarados (ver Fuentes de Información). Los márgenes de error asociados con cada estimación son reportados como límites altos o bajos. Las siguientes clasificaciones especiales son definidas y valuadas como:<ul><li>0.0 - la información está disponible y el porcentaje es menor a 0.1; valuado como 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Centros para el Control y la Prevención de Enfermedades (CDC, por sus siglas en inglés)</p>',
	name_es = 'Expectativa de vida (promedio)'
where id_ = 102; -- Life expectancy (average)

update tbl_indicators
set description_es = '<p><span class="font-weight-bold">Definición: </span>Tasa de personas hospitalizadas con un diagnóstico primario de una condición de salud mental, por cada 10,000 personas.</p><p><span class="font-weight-bold">Notas sobre la Información: </span>Los porcentajes estimados que se presentan son los declarados (ver Fuentes de Información). Las siguientes clasificaciones especiales son definidas y valuadas como:<ul><li>0.0 - la información está disponible y el porcentaje es menor a 0.1; valuado como 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Departamento Estatal de Servicios de Salud de Texas - PUDF de Altas Hospitalarias de THCIC</p>',
	name_es = 'Tasa de hospitalización por salud mental - promedio de 3 años (cada 10,000)'
where id_ = 103; -- Mental health hospitalization rate - 3 year average (per 10K)

update tbl_indicators
set description_es = '<p><span class="font-weight-bold">Definición: </span>Porcentaje de adultos que declaran 14 o más días durante los últimos 30 días en los que su salud mental no fue buena.</p><p><span class="font-weight-bold">Notas sobre la Información: </span>Los porcentajes estimados que se presentan son los declarados (ver Fuentes de Información). Los márgenes de error asociados con cada estimación son reportados como límites altos o bajos. Las siguientes clasificaciones especiales son definidas y valuadas como:<ul><li>0.0 - la información está disponible y el porcentaje es menor a 0.1; valuado como 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Centros para el Control y la Prevención de Enfermedades (CDC, por sus siglas en inglés): PLACES</p>',
	name_es = 'Salud mental deteriorada durante >=14 días en adultos'
where id_ = 104; -- Mental health not good for >=14 days among adults

update tbl_indicators
set description_es = '<p><span class="font-weight-bold">Definición: </span>Porcentaje de adultos que declaran haber sido informados alguna vez por un médico, una enfermera u otro profesional sanitario de que han sufrido una apoplejía.</p><p><span class="font-weight-bold">Notas sobre la Información: </span>Los porcentajes estimados que se presentan son los declarados (ver Fuentes de Información). Los márgenes de error asociados con cada estimación son reportados como límites altos o bajos. Las siguientes clasificaciones especiales son definidas y valuadas como:<ul><li>0.0 - la información está disponible y el porcentaje es menor a 0.1; valuado como 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Centros para el Control y la Prevención de Enfermedades (CDC, por sus siglas en inglés): PLACES</p>',
	name_es = 'Apoplejía en adultos'
where id_ = 106; -- Stroke among adults

update tbl_indicators
set description_es = '<p><span class="font-weight-bold">Definición: </span>El porcentaje de cambio en las unidades de vivienda entre el Censo Decenal de 2010 y 2020.</p><p><span class="font-weight-bold">Notas sobre la Información: </span>Los porcentajes estimados que se presentan son los declarados (ver Fuentes de Información). Los márgenes de error asociados con cada estimación son reportados como límites altos o bajos. Las siguientes clasificaciones especiales son definidas y valuadas como:<ul><li>0.0 - la información está disponible y el porcentaje es menor a 0.1; valuado como 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Nación, Estado y Condado - Oficina del Censo de Estados Unidos (USCB, por sus siglas en inglés), 2020</p>',
	name_es = 'Cambio en el total de unidades de vivienda (cambio en 10 años)'
where id_ = 107; -- Change in total housing units (10-year change)

update tbl_indicators
set description_es = '<p><span class="font-weight-bold">Definición: </span>Porcentaje de viviendas con 2 o más unidades familiares en la estructura.</p><p><span class="font-weight-bold">Notas sobre la Información: </span>Los porcentajes estimados que se presentan son los declarados (ver Fuentes de Información). Los márgenes de error asociados con cada estimación son reportados como límites altos o bajos. Las siguientes clasificaciones especiales son definidas y valuadas como:<ul><li>0.0 - la información está disponible y el porcentaje es menor a 0.1; valuado como 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Estimaciones de 5 años según la Encuesta sobre la Comunidad Estadounidense de la Oficina del Censo (USCB ACS, por sus siglas en inglés), B25024</p>',
	name_es = 'Viviendas multifamiliares'
where id_ = 108; -- Multi-family housing units

update tbl_indicators
set description_es = '<p><span class="font-weight-bold">Definición: </span>Porcentaje de viviendas pagadas por organismos federales, estatales o locales.</p><p><span class="font-weight-bold">Notas sobre la Información: </span>Los porcentajes estimados que se presentan son los declarados (ver Fuentes de Información). Los márgenes de error asociados con cada estimación son reportados como límites altos o bajos. Las siguientes clasificaciones especiales son definidas y valuadas como:<ul><li>0.0 - la información está disponible y el porcentaje es menor a 0.1; valuado como 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Base de Datos Nacional de Conservación de la Vivienda (NHPD, por sus siglas en inglés)</p>',
	name_es = 'Unidades de vivienda subvencionadas'
where id_ = 109; -- Housing units subsidized

update tbl_indicators
set description_es = '<p><span class="font-weight-bold">Definición: </span>Próximamente</p><p><span class="font-weight-bold">Notas sobre la Información: </span>Los porcentajes estimados que se presentan son los declarados (ver Fuentes de Información). Los márgenes de error asociados con cada estimación son reportados como límites altos o bajos. Las siguientes clasificaciones especiales son definidas y valuadas como:<ul><li>0.0 - la información está disponible y el porcentaje es menor a 0.1; valuado como 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Distrito de Tasación del Condado de Bexar (BCAD, por sus siglas en inglés)</p>',
	name_es = 'Edificios residenciales en estado regular o malo'
where id_ = 110; -- Residential buildings in fair or poor condition

update tbl_indicators
set description_es = '<p><span class="font-weight-bold">Definición: </span>Porcentaje de la población de 1 año o más en viviendas ocupadas por inquilinos que se mudó en el último año.</p><p><span class="font-weight-bold">Notas sobre la Información: </span>Los porcentajes estimados que se presentan son los declarados (ver Fuentes de Información). Los márgenes de error asociados con cada estimación son reportados como límites altos o bajos. Las siguientes clasificaciones especiales son definidas y valuadas como:<ul><li>0.0 - la información está disponible y el porcentaje es menor a 0.1; valuado como 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Estimaciones de 5 años según la Encuesta sobre la Comunidad Estadounidense de la Oficina del Censo (USCB ACS, por sus siglas en inglés), B07013</p>',
	name_es = 'Estabilidad de la vivienda del inquilino (se ha mudado en el último año)'
where id_ = 111; -- Renter housing stability (moved in past year)

update tbl_indicators
set description_es = '<p><span class="font-weight-bold">Definición: </span>Porcentaje de viviendas desocupadas. Quedan excluidos del recuento de viviendas vacías los dormitorios, las literas, los barracones, los hoteles de paso (y similares), los alojamientos en instituciones, los hospitales generales y las instalaciones militares. Para más información, visite <a target="_blank" href="https://www.census.gov/housing/hvs/definitions.pdf">https://www.census.gov/housing/hvs/definitions.pdf</a>.</p><p><span class="font-weight-bold">Notas sobre la Información: </span>Los porcentajes estimados que se presentan son los declarados (ver Fuentes de Información). Los márgenes de error asociados con cada estimación son reportados como límites altos o bajos. Las siguientes clasificaciones especiales son definidas y valuadas como:<ul><li>0.0 - la información está disponible y el porcentaje es menor a 0.1; valuado como 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Estimaciones de 5 años según la Encuesta sobre la Comunidad Estadounidense de la Oficina del Censo (USCB ACS, por sus siglas en inglés), B25004</p>',
	name_es = 'Unidades de vivienda vacías'
where id_ = 112; -- Vacant housing
