--liquibase formatted sql

--changeset herronrb:about_the_data_pt3

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>Percentage of adults who report usually getting insufficient sleep (&lt; 7 hours for those aged &ge; 18 years, on average, during a 24-hour period).</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Centers for Disease Control and Prevention: PLACES</p>',
	description_es = ''
where id_ = 78; -- Sleeping less than seven hours among adults

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>Percentage of adults who report having their cholesterol checked within the previous 5 years.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Centers for Disease Control and Prevention: PLACES</p>',
	description_es = ''
where id_ = 79; -- Cholesterol screening among adults

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>Percent of population age 5 years and older that are fully vaccinated and have a single booster dose.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>City of San Antonio Metropolitan Health District</p>',
	description_es = ''
where id_ = 80; -- COVID-19 vaccination rates

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>Percentage of adults who have a body mass index (BMI) &ge;30.0 kg/m<sup>2</sup> calculated from self-reported weight and height.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Centers for Disease Control and Prevention: PLACES</p>',
	description_es = ''
where id_ = 81; -- Obesity among adults

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>Percent of women aged 65 or older reporting having received all of the following: an influenza vaccination in the past year; a pneumococcal vaccination (PPV) ever; either a fecal occult blood test (FOBT) within the past year, a sigmoidoscopy within the past 5 years and a FOBT within the past 3 years, or a colonoscopy within the previous 10 years; and a mammogram in the past 2 years. For men it is the percent 65 or older reporting having received all of the following: an influenza vaccination in the past year; a PPV ever; and either a fecal occult blood test (FOBT) within the past year, a sigmoidoscopy within the past 5 years and a FOBT within the past 3 years, or a colonoscopy within the past 10 years.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Centers for Disease Control and Prevention: PLACES</p>',
	description_es = ''
where id_ = 82; -- Older adults aged 65 years and older who are up to date on a core set of clinical preventive services

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>Rate of opioids prescribed for every 100,000 people.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Texas Department of State Health Services</p>',
	description_es = ''
where id_ = 83; -- Opioid prescription rate (per 100K)

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>Percentage of women aged 21-65 years who do not report having had a hysterectomy and who report having had recommended cervical cancer screening test. For female respondents aged 21-29 years, the recommended screening test is Pap test alone. For female respondents aged 30-65 years, there are three recommended screening tests with varying frequencies: (1) Pap test alone, (2) human papillomavirus (HPV) test alone, or (3) Pap test in combination with HPV test.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Centers for Disease Control and Prevention: PLACES</p>',
	description_es = ''
where id_ = 84; -- Cervical cancer Pap screening among adult women aged 21-65 years

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>Percentage of adults who report having smoked 100 cigarettes or more in their lifetime and currently smoke every day or some days.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Centers for Disease Control and Prevention: PLACES</p>',
	description_es = ''
where id_ = 85; -- Current smoking among adults

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>Percentage of women who started receiving prenatal care in the 1<supst</sup> trimester.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>City of San Antonio Metropolitan Health District</p>',
	description_es = ''
where id_ = 86; -- Early Prenatal care

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>Percentage of adults who report having been to the dentist or dental clinic in the previous year.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Centers for Disease Control and Prevention: PLACES</p>',
	description_es = ''
where id_ = 87; -- Visits to dentist or dental clinic among adults

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>Percentage of adults who report having been to a doctor for a routine checkup (e.g., a general physical exam, not an exam for a specific injury, illness, condition) in the previous year.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Centers for Disease Control and Prevention: PLACES</p>',
	description_es = ''
where id_ = 88; -- Visits to doctor for routine checkup within the past year among adults

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>Rate of people hospitalized with a primary diagnosis of asthma, for every 10,000 intakes.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Texas Department of State Health Services</p>',
	description_es = ''
where id_ = 89; -- Asthma hospitalization (per 10K)

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>Rate of births for every 1,000 people ages 15 to 44 years.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>City of San Antonio Metropolitan Health District</p>',
	description_es = ''
where id_ = 90; -- Birth rates (per 1K age 15-44)

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>Percentage of adults who report ever having been told by a doctor, nurse, or other health professional that they had angina or coronary heart disease.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Centers for Disease Control and Prevention: PLACES</p>',
	description_es = ''
where id_ = 91; -- Coronary heart disease among adults

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>Rate of people with COVID-19, for every 100,000 people.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>City of San Antonio Metropolitan Health District</p>',
	description_es = ''
where id_ = 92; -- COVID-19 case rate (per 100K)

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>Rate of people who died as a result of COVID-19, for every 100,000 people.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>City of San Antonio Metropolitan Health District</p>',
	description_es = ''
where id_ = 93; -- COVID-19 death rate (per 100K)

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>Rate of deaths, for every 100,000 people.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Texas Department of State Health Services</p>',
	description_es = ''
where id_ = 94; -- Death rate (per 100K)

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>Rate of people hospitalized with a primary diagnosis of diabetes, for every 10,000 intakes.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Texas Department of State Health Services</p>',
	description_es = ''
where id_ = 95; -- Diabetes hospitalization rate (per 10K)

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>Percentage of adults who report ever been told by a doctor, nurse, or other health professional that they have diabetes other than diabetes during pregnancy.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Centers for Disease Control and Prevention: PLACES</p>',
	description_es = ''
where id_ = 96; -- Diabetes among adults

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>Percentage of people with a disability. The American Census Survey captures six aspects of disability: hearing, vision, cognitive, ambulatory, self-care, and independent living. For more information, visit <a target="_blank" href="https://www.census.gov/quickfacts/fact/note/US/DIS010220">https://www.census.gov/quickfacts/fact/note/US/DIS010220</a>.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>2020 Census Bureau''s American Community Survey, B18101 A-I</p>',
	description_es = ''
where id_ = 97; -- Disability status

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>Rate of people hospitalized with a primary diagnosis of hypertension, for every 10,000 people.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Texas Department of State Health Services</p>',
	description_es = ''
where id_ = 98; -- Hypertension hospitalization rate – 3 year average (per 10K)

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>We call this injury hospitalization rate for short, but these indicators actually reflect hospital discharges for injury, not admissions. The rates are determined by hospitalizations for the disease or injury as the primary diagnosis, not all hospital discharges with that diagnosis. The rates are not prevalence or incidence of the disease or injury. These hospitalization counts are also not unique visits or people. If the same person in 78204 goes to the hospital three times for the same diagnosis in 2019 then all three visits are included if asthma was the primary diagnosis for the admission during that year.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Texas Department of State Health Services</p>',
	description_es = ''
where id_ = 99; -- Injury hospitalization rate – 3 year average (per 10K)

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>Rate of people diagnosed with invasive cancer, for every 10,000 people.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Centers for Disease Control and Prevention</p>',
	description_es = ''
where id_ = 100; -- Invasive cancer incidence rate (per 10K)

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>Rate of people diagnosed with lead poisoning or elevated lead in their blood, for every 10,000 people.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Texas Department of State Health Services</p>',
	description_es = ''
where id_ = 101; -- Lead poisoning/elevated blood lead (per 10K)

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>The average number of years a person can expect to live. For more information, visit <a target="_blank" href="https://www.cdc.gov/nchs/nvss/life-expectancy.htm">https://www.cdc.gov/nchs/nvss/life-expectancy.htm</a>.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Centers for Disease Control and Prevention</p>',
	description_es = ''
where id_ = 102; -- Life expectancy (average)

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>Rate of people hospitalized with a primary diagnosis of a mental health condition, for every 10,000 people.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Texas Department of State Health Services</p>',
	description_es = ''
where id_ = 103; -- Mental health hospitalization rate – 3 year average (per 10K)

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>Percentage of adults who report 14 or more days during the past 30 days during which their mental health was not good.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Centers for Disease Control and Prevention: PLACES</p>',
	description_es = ''
where id_ = 104; -- Mental health not good for >=14 days among adults

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>Percentage of adults who report ever having been told by a doctor, nurse, or other health professional that they have had a stroke.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Centers for Disease Control and Prevention: PLACES</p>',
	description_es = ''
where id_ = 106; -- Stroke among adults

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>The percentage of change in housing units between the 2010 and 2020 Decennial Census.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>2020 Census Bureau''s Decennial Census</p>',
	description_es = ''
where id_ = 107; -- Change in total housing units (10-year change)

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>Percentage of housing units with 2 or more units in the structure.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>2020 Census Bureau''s American Community Survey, Table B25024</p>',
	description_es = ''
where id_ = 108; -- Multi-family housing units

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>Percentage of housing units paid for by federal, state, or local agencies.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>National Housing Preservation Database</p>',
	description_es = ''
where id_ = 109; -- Housing units subsidized

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>Coming soon</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>Bexar County Appraisal District</p>',
	description_es = ''
where id_ = 110; -- Residential buildings in fair or poor condition

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>Percentage of population 1 year and over in renter-occupied housing units who moved in the past year.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>2020 Census Bureau''s American Community Survey, Table B07013</p>',
	description_es = ''
where id_ = 111; -- Renter housing stability (moved in past year)

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>Percentage of housing units which are vacant. Dormitories, bunkhouses, barracks, transient hotels (and the like), quarters in institutions, general hospitals, and military installations are excluded from the count of vacant housing. For more information, visit <a target="_blank" href="https://www.census.gov/housing/hvs/definitions.pdf">https://www.census.gov/housing/hvs/definitions.pdf</a>.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>2020 Census Bureau''s American Community Survey, Table B25004</p>',
	description_es = ''
where id_ = 112; -- Vacant housing

update tbl_indicators
set description_en = '<p><span class="font-weight-bold">Definition: </span>Percentage of population 1 year and over who moved in the past year.</p><p><span class="font-weight-bold">Data Notes: </span>The estimated percentages presented are as reported (see Data Sources). Margins of error associated with each estimate are reported as high and low limits. The following special classifications are defined and valued as:<ul><li>0.0 - data are available and percentage is less than 0.1; valued as 0.0</li></ul></p><p><span class="font-weight-bold">Data Source: </span>2020 Census Bureau''s American Community Survey, Table B07001, B07004 A-I</p>',
	description_es = ''
where id_ = 113; -- Moved in the past year

update tbl_indicators 
set name_en = 'Early prenatal care' 
where id_ = 86;

update tbl_indicators 
set name_en = 'Asthma hospitalization rate - 3 year average (per 10K)',
	source_id = 24
where id_ = 89;

update tbl_indicators 
set name_en = 'Diabetes hospitalization rate - 3 year average  (per 10K)',
	source_id = 24
where id_ = 95;

update tbl_indicators 
set name_en = 'Hypertension hospitalization rate - 3 year average  (per 10K)' 
where id_ = 98;

update tbl_indicators 
set name_en = 'Injury hospitalization rate - 3 year average  (per 10K)' 
where id_ = 99;

update tbl_indicators 
set name_en = 'Mental health hospitalization rate - 3 year average  (per 10K)' 
where id_ = 103;

update tbl_indicators
set description_en = replace(description_en, 'Texas Department of State Health Services', 'Texas Department of State Health Services - THCIC Hospital Discharge PUDF')
where id_ in (89, 95, 98, 99, 103);